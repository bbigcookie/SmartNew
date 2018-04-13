package com.dcits.user.dao.impl;

import com.dcits.user.base.dao.impl.BaseDao;
import com.dcits.user.exception.DaoException;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends BaseDao {
    @Override
    public int insertBySqlId(String sqlID, Object param) throws DaoException {
        return super.insertBySqlId(sqlID, param);
    }

    @Override
    public int updateBySqlId(String sqlID, Object param) throws DaoException {
        return super.updateBySqlId(sqlID, param);
    }

    @Override
    public int deleteBySqlId(String sqlID, Object param) throws DaoException {
        return super.deleteBySqlId(sqlID, param);
    }

    @Override
    public <T> T findBySqlId(String sqlID, Object param, Class<T> resultType) throws DaoException {
        return super.findBySqlId(sqlID, param, resultType);
    }
}