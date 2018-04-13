package com.dcits.user.service.interfaces;

import com.dcits.user.dao.bean.User;
import com.dcits.user.exception.DaoException;

/**
 * Created by gaojunc on 2017/12/24 18:51.
 * Created Reason: 用户通用接口
 */
public interface IUser {

    /*
        查找用户
     */
    User findUser(Object params) throws DaoException;

}
