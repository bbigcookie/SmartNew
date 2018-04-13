package com.dcits.commodity.base.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.CallableStatementCallback;

import com.dcits.commodity.base.dialect.Dialect;
import com.dcits.commodity.exception.DaoException;

public interface IDao {
    public SqlSessionTemplate getSqlSessionTemplate();

    public Dialect getDialect();

    /**
     * 通过sql键值执行insert操作
     * @param sqlID Mybatis的sql鍵值，组成格式：命名空间.id
     * @param param 参数
     * @return 影响记录数（不一定准确，取决于底层）
     * @throws DaoException
     */
    public int insertBySqlId(String sqlID, Object param) throws DaoException;

    /**
     * 通过sql键值执行update操作
     * @param sqlID Mybatis的sql鍵值，组成格式：命名空间.id
     * @param param 参数
     * @return 影响记录数（不一定准确，取决于底层）
     * @throws DaoException
     */
    public int updateBySqlId(String sqlID, Object param) throws DaoException;

    /**
     * 通过sql键值执行delete操作
     * @param sqlID Mybatis的sql鍵值，组成格式：命名空间.id
     * @param param 参数
     * @return 影响记录数（不一定准确，取决于底层）
     * @throws DaoException
     */
    public int deleteBySqlId(String sqlID, Object param) throws DaoException;


    /**
     * 通过sql键值执行查询单条记录操作
     * @param sqlID Mybatis的sql鍵值，组成格式：命名空间.id
     * @param param 参数
     * @param resultType 返回值类型
     * @return 单条查询记录（如果存在多条结果，只取第一个）
     * @throws DaoException
     */
    public <T> T findBySqlId(String sqlID, Object param, Class<T> resultType) throws DaoException;

    /**
     * 通过sql键值执行查询多条记录操作
     * @param sqlID Mybatis的sql鍵值，组成格式：命名空间.id
     * @param param 参数
     * @param resultType 返回值类型
     * @return 多条查询记录
     * @throws DaoException
     */
    public <T> List<T> findListBySqlId(String sqlID, Object param, Class<T> resultType) throws DaoException;

    /**
     * 通过sql键值执行分页查询操作
     * @param sqlID Mybatis的sql鍵值，组成格式：命名空间.id
     * @param param 参数 （欲获得总记录数，参见 {@link com.digitalchina.microservice.base.model.RecordCount RecordCount}）
     * @param pageNo 分页序号
     * @param pageSize 分页大小
     * @param resultType 返回值类型
     * @return 分页查询记录
     * @throws DaoException
     */
    public <T> List<T> findListBySqlId(String sqlID, Object param, int pageNo, int pageSize, Class<T> resultType) throws DaoException;

    /**
     * 通过sql语句执行insert、update或者delete操作
     * @param sql sql语句
     * @param params sql语句中，与参数占位符"?"对应的参数值
     * @return 影响记录数（不一定准确，取决于底层）
     * @throws DaoException
     */
    public int executeBySql(String sql, Object... params) throws DaoException;

    /**
     * 通过sql语句执行查询单条记录操作
     * @param sql sql语句
     * @param params sql语句中，与参数占位符"?"对应的参数值
     * @return 单条查询记录（如果存在多条结果，只取第一个）
     * @throws DaoException
     */
    public Map<String, Object> findBySql(String sql, Object... params) throws DaoException;

    /**
     * 通过sql语句执行查询多条记录操作
     * @param sql sql语句
     * @param params sql语句中，与参数占位符"?"对应的参数值
     * @return 多条查询记录
     * @throws DaoException
     */
    public List<Map<String, Object>> findListBySql(String sql, Object... params) throws DaoException;

    /**
     * 通过sql语句执行分页查询操作
     * @param sql sql语句
     * @param pageNo 分页序号
     * @param pageSize 分页大小
     * @param params sql语句中，与参数占位符"?"对应的参数值
     * @return 分页查询记录
     * @throws DaoException
     */
    public List<Map<String, Object>> findListBySql(String sql, int pageNo, int pageSize, Object... params) throws DaoException;

    /**
     * 通过数据库存储过程、函数进行查询操作
     * @param sql sql语句
     * @param callback 回调对象
     * @return 查询结果
     * @throws DaoException
     */
    public <T> T findByProc(String sql, CallableStatementCallback<T> callback) throws DaoException;

    /**
     * 通过数据库存储过程、函数进行DML操作
     * @param sql sql语句
     * @param callback 回调对象
     * @return 执行结果
     * @throws DaoException
     */
    public <T> T executeByProc(String sql, CallableStatementCallback<T> callback) throws DaoException;

    /**
     * 通过sql键值执行分页查询操作
     * @param sqlID Mybatis的sql鍵值，组成格式：命名空间.id
     * @param param 参数 （欲获得总记录数，参见 {@link com.digitalchina.microservice.base.model.RecordCount RecordCount}）
     * @param pageNo 分页序号
     * @param pageSize 分页大小
     * @return 分页查询记录（格式为 {total : .. , rows : []}）
     * @throws DaoException
     */
    public Map<String, Object> findRecordsWithTotalBySqlId(String sqlID, Object param, int pageNo, int pageSize) throws DaoException;

    /**
     * 通过sql语句执行分页查询操作
     * @param sql sql语句
     * @param pageNo 分页序号
     * @param pageSize 分页大小
     * @param params sql语句中，与参数占位符"?"对应的参数值
     * @return 分页查询记录（格式为 {total : .. , rows : []}）
     * @throws DaoException
     */
    public Map<String, Object> findRecordsWithTotalBySql(String sql, int pageNo, int pageSize, Object... params) throws DaoException;
    /**
     * 通过sql语句执行批量插入操作
     * @param sql sql语句
     * @param list 数据集合
     * @throws DaoException
     */
    public void batchInsert(List list, String sql) throws DaoException;
}
