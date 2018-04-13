package com.dcits.user.service.imlp;

import com.dcits.user.constant.ResultStatus;
import com.dcits.user.dao.bean.IUserInfo;
import com.dcits.user.dao.bean.User;
import com.dcits.user.dao.bean.UserMainInfo;
import com.dcits.user.dao.bean.UserPersonalInfo;
import com.dcits.user.dao.impl.UserMapper;
import com.dcits.user.exception.DaoException;
import com.dcits.user.log.ILogger;
import com.dcits.user.log.LoggerFactory;
import com.dcits.user.service.interfaces.IUserUpdate;
import com.dcits.user.util.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaojunc on 2018/3/14 10:26.
 * Created Reason: 用户信息修改实现类
 */
@Service
public class UserUpdateImpl implements IUserUpdate {
    private static ILogger logger = LoggerFactory.getLogger(UserUpdateImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private User userObj;
    @Autowired
    private ResultStatus resultStatus;

    public static final String NAMESPACE_PREFIX = "com.dcits.user.dao.impl.UserMapper.";

    /**
     * 根据不同登录类型查询（支持使用账号、昵称或者手机号登录）
     */
    @Override
    public User findUser(Object param) throws DaoException {
        Map<String, String> params = new HashMap<>();
        IUserInfo user = null;
        if (param instanceof UserPersonalInfo) {
            user = (UserPersonalInfo) param;
        } else if (param instanceof User) {
            user = (User) param;
        } else return null;
        return _findUser(params, user);
    }

    private User _findUser(Map<String, String> params, IUserInfo user) throws DaoException {
        if (user.getUsername() != null) {
            params.put("userid", "smart_" + user.getUsername());
            return userMapper.findBySqlId(NAMESPACE_PREFIX + "selectByPrimaryKey", params, User.class);
        } else if (user.getNickname() != null) {
            //TODO 根据昵称查询
            params.put("nickname", user.getNickname());
            return userMapper.findBySqlId("", params, User.class);
        } else if (user.getPhoneNum() != null) {
            //TODO 根据手机号查询
            params.put("phone_num", user.getPhoneNum());
            return userMapper.findBySqlId("", params, User.class);
        } else {
            return null;
        }
    }

    @Override
    public ResultStatus modifyPassword(UserMainInfo userInfo) {

        try {
            //根据账号名更新
            if (userInfo.getUsername().length() > 0) {
                return _updateWithUsername(userInfo);
            } else if (userInfo.getPhoneNum().length() > 0) {
                //TODO 根据手机号更新
                return null;
            } else {
                //TODO 根据昵称更新
            }

        } catch (DaoException e) {
            logger.error("登录时查询数据库出现异常。", e);
            resultStatus.setStatusCode("Error");
            resultStatus.setStatusInfo("登录时查询数据库出现异常:" + e.getErrorMessage());
            return resultStatus;
        }
        return null;
    }

    private ResultStatus _updateWithUsername(UserMainInfo userInfo) throws DaoException {
        userObj.setUsername(userInfo.getUsername());
        userObj.setUserid("smart_" + userInfo.getUsername());
        User rst = findUser(this.userObj);
        if (rst == null) {
            resultStatus.setStatusCode("Error");
            resultStatus.setStatusInfo("当前用户不存在!");
            return resultStatus;
        }
        String password = userInfo.getPassword();
        if (password.equals("") || password.length() < 8) {
            resultStatus.setStatusCode("Error");
            resultStatus.setStatusInfo("密码不符合规范，请重新输入");
            return resultStatus;
        }
        rst.setPassword(MD5Util.md5Hex(password));
        //存库
        String sqlID = NAMESPACE_PREFIX + "updateByPrimaryKeySelective";
        if (userMapper.updateBySqlId(sqlID, rst) == 1) {
            resultStatus.setStatusCode("Success");
            resultStatus.setStatusInfo("更新密码成功!");
            return resultStatus;
        } else {
            resultStatus.setStatusCode("Error");
            resultStatus.setStatusInfo("更新密码失败!");
            return resultStatus;
        }
    }

    @Override
    public ResultStatus modifyCommonInfo(UserPersonalInfo user) {
        try {
            User rst = findUser(user);
            if (rst == null) {
                resultStatus.setStatusCode("Error");
                resultStatus.setStatusInfo("当前用户不存在!");
                return resultStatus;
            }
            //反射获取Bean里面的属性值用于存库
            Field[] fields = user.getClass().getDeclaredFields();
            Map<String, String> params = new HashMap<>();
            _reflectProperties(user, fields, params);

            String sqlID = NAMESPACE_PREFIX + "updateByPrimaryKeySelective";
            if (userMapper.updateBySqlId(sqlID, params) == 1) {
                resultStatus.setStatusCode("Success");
                resultStatus.setStatusInfo("更新用户信息成功!");
                return resultStatus;
            } else {
                resultStatus.setStatusCode("Error");
                resultStatus.setStatusInfo("更新用户信息时数据库保存失败!");
                return resultStatus;
            }
        } catch (Exception e) {
            resultStatus.setStatusCode("Error");
            resultStatus.setStatusInfo("更新用户信息失败!");
            return resultStatus;
        }
    }

    private void _reflectProperties(UserPersonalInfo user, Field[] fields, Map<String, String> params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for (Field field : fields
                ) {
            field.setAccessible(true);
            String name = field.getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
            String type = field.getGenericType().toString();    //获取属性的类型
            if (type.equals("class java.lang.String")) {   //获取到String类型
                Method m = user.getClass().getMethod("get" + name);
                String value = (String) m.invoke(user, new Object[]{});    //调用getter方法获取属性值
                if (value != null && value.length() > 0) {
                    params.put(field.getName(), value);
                }
            }
        }
    }
}
