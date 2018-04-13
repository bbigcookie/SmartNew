package com.dcits.order.base.dao.impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dcits.order.base.dao.IDao;
import com.dcits.order.base.dialect.Dialect;
import com.dcits.order.base.dialect.impl.MysqlDialect;
import com.dcits.order.base.dialect.impl.OracleDialect;
import com.dcits.order.base.model.RecordCount;
import com.dcits.order.constant.LogConstant;
import com.dcits.order.exception.DaoException;

public class BaseDao implements IDao,InitializingBean {
    private Logger logger = LoggerFactory.getLogger(BaseDao.class);

    private Dialect dialect = null;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private JdbcTemplate jdbcTemplate;


    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public Dialect getDialect() {
        return dialect;
    }
    public SqlSession getBatchSqlSession() {
        return sqlSessionTemplate.getSqlSessionFactory().openSession(
                ExecutorType.BATCH, false);
    }
    public void afterPropertiesSet() throws Exception {

        String type = sqlSessionTemplate.getConfiguration().getVariables()
                .getProperty("dialect");

        if(type==null)
        {
            throw new DaoException("dao_config_not_found");
        }

        try
        {
            String upperType = type.toUpperCase();
            Dialect.Type databaseType = Dialect.Type.valueOf(upperType);

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
        }
        catch(Exception e)
        {
            logger.error(LogConstant.BASEDAO_AFTERPROPERTIESSET_ERROR, e);
            throw new DaoException(LogConstant.DAO_CONFIG_ERROR);
        }

    }

    public int insertBySqlId(String sqlID, Object param) throws DaoException {

        if (sqlID == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLA);

        int res = sqlSessionTemplate.insert(sqlID, param);

        return res;
    }

    public int updateBySqlId(String sqlID, Object param) throws DaoException {

        if (sqlID == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLA);

        int res = sqlSessionTemplate.update(sqlID, param);

        return res;
    }

    public int deleteBySqlId(String sqlID, Object param) throws DaoException {

        if (sqlID == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLA);

        int res = sqlSessionTemplate.delete(sqlID, param);

        return res;
    }

    public int executeBySql(String sql, Object... params) throws DaoException {

        if (sql == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLB);

        return jdbcTemplate.update(sql, params);

    }

    public <T> T findBySqlId(String sqlID, Object param, Class<T> resultType) throws DaoException {

        if (sqlID == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLA);

        return sqlSessionTemplate.selectOne(sqlID, param);

    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findListBySqlId(String sqlID, Object param,
                                       Class<T> resultType) throws DaoException {

        if (sqlID == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLA);

        List<Object> list = sqlSessionTemplate.selectList(sqlID, param);

        return (List<T>) list;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findListBySqlId(String sqlID, Object param, int pageNo,
                                       int pageSize, Class<T> resultType) throws DaoException {

        if (sqlID == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLA);

        int offset = (pageNo - 1) * pageSize;
        List<Object> list = sqlSessionTemplate.selectList(sqlID, param,
                new RowBounds(offset, pageSize));

        return (List<T>) list;
    }

    @SuppressWarnings("rawtypes")
    public Map<String, Object> findRecordsWithTotalBySqlId(String sqlID,
                                                           Object param, int pageNo, int pageSize) throws DaoException {

        if (sqlID == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLA);

        int offset = (pageNo - 1) * pageSize;
        List<Object> list = sqlSessionTemplate.selectList(sqlID, param,
                new RowBounds(offset, pageSize));

        RecordCount rc = null;
        if (param instanceof Map) {
            rc = (RecordCount) ((Map) param).get(RecordCount.RECORD_COUNT_KEY);
        } else {
            Field[] fields = param.getClass().getDeclaredFields();
            Field countField = null;
            for (Field field : fields) {
                if (field.getType().isAssignableFrom(RecordCount.class)) {
                    countField = field;
                    break;
                }
            }
            if (countField != null) {
                countField.setAccessible(true);
                try {
                    rc = (RecordCount) countField.get(param);
                } catch (IllegalArgumentException e) {
                    logger.error(LogConstant.BASEDAO_FINDRECORDSWITHTOTALBYSQLID_ERRORA, e);
                } catch (IllegalAccessException e) {
                    logger.error(LogConstant.BASEDAO_FINDRECORDSWITHTOTALBYSQLID_ERRORB, e);
                }
            }
        }

        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("total", rc != null ? rc.getTotal() : list.size());
        resMap.put("rows", list);

        return resMap;
    }

    public Map<String, Object> findBySql(String sql, Object... params) throws DaoException {

        if (sql == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLB);

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        Map<String, Object> map = null;
        if (list.size() > 0)
            map = list.get(0);

        return map;
    }

    public List<Map<String, Object>> findListBySql(String sql, Object... params) throws DaoException {

        if (sql == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLB);

        return jdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> findListBySql(String sql, int pageNo,
                                                   int pageSize, Object... params) throws DaoException {

        if (sql == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLB);

        int offset = (pageNo - 1) * pageSize;
        String newSql = dialect.getLimitString(sql, offset, pageSize);
        Object[] newParams = setParameters(params, pageNo, pageSize);

        return jdbcTemplate.queryForList(newSql, newParams);
    }

    public Map<String, Object> findRecordsWithTotalBySql(String sql,
                                                         int pageNo, int pageSize, Object... params) throws DaoException {

        if (sql == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLB);

        String countSql = dialect.getCountString(sql);
        Map<String, Object> map = jdbcTemplate.queryForMap(countSql, params);
        Object num = map.get("n$$");

        int offset = (pageNo - 1) * pageSize;
        String limitSql = dialect.getLimitString(sql, offset, pageSize);
        Object[] newParams = setParameters(params, pageNo, pageSize);

        List<Map<String, Object>> list = jdbcTemplate.queryForList(limitSql,
                newParams);

        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("total", num);
        resMap.put("rows", list);

        return resMap;
    }

    private Object[] setParameters(Object[] params, int pageNo, int pageSize) {

        if (params == null)
            params = new Object[0];
        int length = params.length;
        Object[] newParams = Arrays.copyOf(params, length + 2);

        if (dialect instanceof OracleDialect) {
            int offset = (pageNo - 1) * pageSize;
            newParams[length] = offset + pageSize;
            newParams[length + 1] = offset;
        } else if (dialect instanceof MysqlDialect) {
            int offset = (pageNo - 1) * pageSize;
            newParams[length] = offset;
            newParams[length + 1] = pageSize;
        }

        return newParams;
    }

    public <T> T findByProc(String sql, CallableStatementCallback<T> callback) throws DaoException {
        if (sql == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLB);

        if (callback == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLC);

        return jdbcTemplate.execute(sql, callback);

    }

    public <T> T executeByProc(String sql, CallableStatementCallback<T> callback) throws DaoException {
        if (sql == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLB);

        if (callback == null)
            throw new DaoException(LogConstant.DAO_ARGUMENT_NOT_NULLC);

        return jdbcTemplate.execute(sql, callback);

    }
    public void batchInsert(List list, String sql) throws DaoException {
        SqlSession sqlSession = getBatchSqlSession();
        try {
            int batchCount = 200; //Integer.parseInt((String)FrameCacheManager.getValueFromCache(FrameConstant.CacheNames.ConfigCache.name(), FrameConstant.BATCHCOUNT));// 每批commit的个数
            for (int index = 0; index < list.size();) {
                if (batchCount > list.size()) {
                    batchCount = list.size();
                    sqlSession.insert(sql, list.subList(index, batchCount));
                    sqlSession.commit();
                    //清理缓存，防止溢出
                    sqlSession.clearCache();
                    break;// 数据插入完毕,退出循环
                } else {
                    sqlSession.insert(sql, list.subList(index, batchCount));
                    sqlSession.commit();
                    //清理缓存，防止溢出
                    sqlSession.clearCache();
                    index = batchCount;
                    batchCount = index + batchCount;
                }
            }
        } catch (Exception e) {
            logger.error("业务发生异常", e);
            sqlSession.rollback();
            throw new DaoException(e);
        }finally{
            sqlSession.close();
        }
    }
}
