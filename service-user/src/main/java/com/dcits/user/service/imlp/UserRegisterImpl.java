package com.dcits.user.service.imlp;

import com.dcits.user.constant.ResultStatus;
import com.dcits.user.dao.bean.User;
import com.dcits.user.dao.bean.UserMainInfo;
import com.dcits.user.dao.impl.UserMapper;
import com.dcits.user.exception.DaoException;
import com.dcits.user.log.ILogger;
import com.dcits.user.log.LoggerFactory;
import com.dcits.user.service.interfaces.IUserCommand;
import com.dcits.user.util.md5.MD5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;

/**
 * Created by gaojunc on 2017/12/21.
 * Created Reason: 用户注册服务
 */
@Service("userRegister")
public class UserRegisterImpl implements IUserCommand {
    private static ILogger logger = LoggerFactory.getLogger(UserRegisterImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResultStatus resultStatus = null;
    @Autowired
    private User user = null;

    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public ResultStatus loginWithVerifyCode(UserMainInfo requestBody) throws IOException {
        return null;
    }

    @Override
    public ResultStatus loginWithPassword(UserMainInfo requestBody) {
        return null;
    }

    /**
     * 根据账号密码注册用户
     *
     * @param request 请求报文
     * @return
     */
    @Override
    public ResultStatus register(UserMainInfo request) {
        _parseReqBody(request, user);
        try {
            //查看当前用户是否已经存在
            if (findUser(user) != null) {
                resultStatus.setStatusCode("Error");
                resultStatus.setStatusInfo("当前用户已经存在!");
                return resultStatus;
            }

            //存入密码的md5值
            String password = user.getPassword();
            if (password.equals("") || password.length() < 8) {
                resultStatus.setStatusCode("Error");
                resultStatus.setStatusInfo("密码为空或强度太低!");
                return resultStatus;
            }
            String md5Hex = MD5Util.md5Hex(password);
            user.setPassword(md5Hex);
            
            //创建购物车
            restTemplate.getForObject("http://shoppingcart/shoppingcart/create?username="+user.getUsername(), String.class);
            
            //存库
            String sqlId = "com.dcits.user.dao.impl.UserMapper.insert";
            if (userMapper.insertBySqlId(sqlId, user) == 1) {
                resultStatus.setStatusCode("Success");
                resultStatus.setStatusInfo("注册成功!");
                return resultStatus;
            }
        } catch (DaoException e) {
            logger.error("注册信息存入数据库出现异常。", e);
            resultStatus.setStatusCode("Error");
            resultStatus.setStatusInfo("注册信息存入数据库出现异常:" + e.getErrorMessage());
            return resultStatus;
        }
        return null;
    }

    private User _parseReqBody(UserMainInfo request, User user) {
        String username = request.getUsername();
        String password = request.getPassword();
        String phone_num = request.getPhoneNum();
        if (phone_num == null || phone_num.length() > 0)
            user.setPhoneNum("");
        else user.setPhoneNum(phone_num);
        user.setUsername(username);
        user.setUserid("smart_" + username);
        user.setPassword(password);
        user.setPhoneNum(phone_num);
        user.setRegistDate(new Date(System.currentTimeMillis()));
        user.setModifyTime(new Date(System.currentTimeMillis()));
        return user;
    }

    /**
     * 根据短信验证码注册
     *
     * @param request
     * @return
     */
    @Override
    public ResultStatus registerWithVerifyCode(UserMainInfo request) {
        return null;
    }

    @Override
    public User findUser(Object user) throws DaoException {
        return userMapper.findBySqlId("com.dcits.user.dao.impl.UserMapper.selectByPrimaryKey", user, User.class);
    }
}
