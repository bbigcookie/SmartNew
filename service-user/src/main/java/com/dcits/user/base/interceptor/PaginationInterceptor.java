package com.dcits.user.base.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.SimpleStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.dcits.user.base.dialect.Dialect;
import com.dcits.user.base.dialect.impl.MysqlDialect;
import com.dcits.user.base.dialect.impl.OracleDialect;
import com.dcits.user.base.model.RecordCount;


/**
 * 分页查询拦截器
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }),
		@Signature(type = StatementHandler.class, method = "parameterize", args = { Statement.class }) })
public class PaginationInterceptor implements Interceptor {
	private static final Log log = LogFactory.getLog(PaginationInterceptor.class);

	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private Dialect dialect = null;

	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();
		Dialect.Type databaseType = null;
		MetaObject metaStatementHandler = MetaObject.forObject(
				statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY);
		Configuration configuration = (Configuration) metaStatementHandler
				.getValue("delegate.configuration");
		try {
			databaseType = Dialect.Type.valueOf(configuration.getVariables()
					.getProperty("dialect").toUpperCase());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		if (databaseType == null) {
			throw new Exception(
					"the value of the dialect property in configuration.xml is not defined : "
							+ configuration.getVariables().getProperty(
							"dialect"));
		}
		if (dialect == null) {
			switch (databaseType) {
				case ORACLE:
					dialect = new OracleDialect();
					break;
				case MYSQL:
					dialect = new MysqlDialect();
					break;
			}
		}
		Method m = invocation.getMethod();
		if ("prepare".equals(m.getName())) {
			return prepare(invocation);
		} else if ("parameterize".equals(m.getName())) {
			return parameterize(invocation);
		}
		return invocation.proceed();
	}

	/**
	 * 拦截prepare修改分页 <功能详细描述>
	 *
	 * @param invocation
	 * @return
	 * @throws Throwable
	 *             [参数说明]
	 *
	 * @return Object [返回类型说明]
	 * @exception throws [异常类型] [异常说明]
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	private Object prepare(Invocation invocation) throws Throwable {
		if (!(invocation.getTarget() instanceof RoutingStatementHandler)) {
			return invocation.proceed();
		}

		// 提取statement
		RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
				.getTarget();

		MetaObject metaStatementHandler = MetaObject.forObject(
				statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY);

		StatementHandler statement = (StatementHandler) metaStatementHandler
				.getValue("delegate");
		// 如果不为两种statement则不继续进行处理
		if (!(statement instanceof SimpleStatementHandler)
				&& !(statement instanceof PreparedStatementHandler)) {
			return invocation.proceed();
		}

		RowBounds rowBounds = (RowBounds) metaStatementHandler
				.getValue("delegate.rowBounds");
		// 根据rowBounds判断是否需要进行物理分页
		if (rowBounds == null
				|| rowBounds.equals(RowBounds.DEFAULT)
				|| (rowBounds.getOffset() <= RowBounds.NO_ROW_OFFSET && rowBounds
				.getLimit() == RowBounds.NO_ROW_LIMIT)) {
			return invocation.proceed();
		}

		// 进行处理
		BoundSql boundSql = statementHandler.getBoundSql();
		String sql = boundSql.getSql();

		Object parameterObject = boundSql.getParameterObject();
		if (parameterObject != null)
		{
			boolean needCount = false;
			Field countField = null;
			if(parameterObject instanceof Map)
			{
				Map<String, Object> map = (Map<String, Object>)parameterObject;
				if(map.containsKey(RecordCount.RECORD_COUNT_KEY))
				{
					needCount = true;
				}
			}
			else
			{
				Field[] fields = parameterObject.getClass().getDeclaredFields();
				for(Field field : fields)
				{
					if(field.getType().isAssignableFrom(RecordCount.class))
					{
						needCount = true;
						countField = field;
						break;
					}
				}
			}

			if(needCount)
			{	// 计算总记录数
				String countSql = dialect.getCountString(sql);
				Connection connection = (Connection) invocation.getArgs()[0];
				MappedStatement mappedStatement =
						(MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
				countTotalRecords(connection, countSql, mappedStatement, boundSql, countField);
			}
		}

		String limitSql = dialect.getLimitString(sql, rowBounds.getOffset(),
				rowBounds.getLimit());

		if (statement instanceof SimpleStatementHandler) {
			limitSql.replaceAll("rn <= ?", "rn <= " + rowBounds.getLimit());
			limitSql.replaceAll("rn > ?", "rn > " + rowBounds.getOffset());
		}

		// 如果为PreparedStatementHandler则无需替换即可
		metaStatementHandler.setValue("delegate.boundSql.sql", limitSql);

		if (log.isDebugEnabled()) {
			log.debug(boundSql.getSql());
		}

		return invocation.proceed();
	}

	/**
	 * 计算记录总数 <功能详细描述>
	 * @param
	 * @param countField
	 *
	 * @param
	 * @return
	 * @throws Throwable
	 *             [参数说明]
	 *
	 * @return Object [返回类型说明]
	 * @throws Exception
	 * @exception throws [异常类型] [异常说明]
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	private void countTotalRecords(Connection connection, String countSql,
                                   MappedStatement mappedStatement, BoundSql boundSql, Field countField) throws Exception
	{
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try
		{
			countStmt = connection.prepareStatement(countSql);
			Object parameterObject = boundSql.getParameterObject();
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
					boundSql.getParameterMappings(), parameterObject);
			ParameterHandler parameterHandler =
					new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
			parameterHandler.setParameters(countStmt);
			rs = countStmt.executeQuery();
			int totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}

			if(countField == null)
			{
				Map<String, Object> map = (Map<String, Object>)parameterObject;
				RecordCount rc = new RecordCount();
				rc.setTotal(totalCount);
				map.put(RecordCount.RECORD_COUNT_KEY, rc);
			}
			else
			{
				countField.setAccessible(true);
				RecordCount rc = new RecordCount();
				rc.setTotal(totalCount);
				countField.set(parameterObject, rc);
			}
		}
		finally {
			rs.close();
			countStmt.close();
		}
	}

	/**
	 * 设置分页参数 <功能详细描述>
	 *
	 * @param invocation
	 * @return
	 * @throws Throwable
	 *             [参数说明]
	 *
	 * @return Object [返回类型说明]
	 * @exception throws [异常类型] [异常说明]
	 * @see [类、类#方法、类#成员]
	 */
	private Object parameterize(Invocation invocation) throws Throwable {
		// 先执行系统默认的参数设置
		Object returnObj = invocation.proceed();

		// 提取statement
		RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation
				.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(
				routingStatementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY);

		StatementHandler statementHandler = (StatementHandler) metaStatementHandler
				.getValue("delegate");
		// 如果不为两种statement则不继续进行处理
		if (!(statementHandler instanceof PreparedStatementHandler)) {
			return returnObj;
		}

		RowBounds rowBounds = (RowBounds) metaStatementHandler
				.getValue("delegate.rowBounds");
		// 根据rowBounds判断是否需要进行物理分页
		if (rowBounds == null
				|| rowBounds.equals(RowBounds.DEFAULT)
				|| (rowBounds.getOffset() <= RowBounds.NO_ROW_OFFSET && rowBounds
				.getLimit() == RowBounds.NO_ROW_LIMIT)) {
			return returnObj;
		}
		// 提取参数设置statement
		Statement statement = (Statement) invocation.getArgs()[0];
		if (!(statement instanceof PreparedStatement)) {
			// 如果对应statement不为PreparedStatement则直接返回
			return returnObj;
		}

		// 设置分页的参数
		PreparedStatement ps = (PreparedStatement) statement;
		int parameterSize = statementHandler.getBoundSql()
				.getParameterMappings().size();
		setParameters(ps, parameterSize, rowBounds);

		metaStatementHandler.setValue("delegate.rowBounds.offset",
				RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit",
				RowBounds.NO_ROW_LIMIT);
		return returnObj;
	}

	private void setParameters(PreparedStatement ps, int parameterSize,
							   RowBounds rowBounds) throws SQLException
	{
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();

		if(dialect instanceof OracleDialect)
		{
			ps.setInt(parameterSize + 1, offset + limit);
			ps.setInt(parameterSize + 2, offset );
		}
		else if(dialect instanceof MysqlDialect)
		{
			ps.setInt(parameterSize + 1, offset );
			ps.setInt(parameterSize + 2, limit );
		}
	}


	/**
	 * @return 返回 dialect
	 */
	public Dialect getDialect() {
		return dialect;
	}

	/**
	 * @param
	 */
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}

}