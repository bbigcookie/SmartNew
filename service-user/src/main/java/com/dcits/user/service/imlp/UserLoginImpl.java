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

import java.io.IOException;

/**
 * Created by gaojunc on 2017/12/24 17:06.
 * Created Reason:用户登录默认实现类
 */
@Service("userLogin")
public class UserLoginImpl implements IUserCommand {

    private static ILogger logger = LoggerFactory.getLogger(UserLoginImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResultStatus resultStatus;
    @Autowired
    private User user;


    @Override
    public User findUser(Object user) throws DaoException {
        User tmp = (User) user;
        return userMapper.findBySqlId("com.dcits.user.dao.impl.UserMapper.selectByPrimaryKey", "smart_" + tmp.getUsername(), User.class);
    }

    @Override
    public ResultStatus loginWithVerifyCode(UserMainInfo requestBody) throws IOException {
        return null;
    }

    /**
     * 使用用户名密码登录
     *
     * @param requestBody
     * @return
     */
    @Override
    public ResultStatus loginWithPassword(UserMainInfo requestBody) {
        _parseReqBody(requestBody, user);
        try {
            User rst = findUser(user);
            if (rst == null) {
                resultStatus.setStatusCode("Error");
                resultStatus.setStatusInfo("账号输入错误!");
                return resultStatus;
            } else {
                if (!user.getPassword().equals(rst.getPassword())) {
                    resultStatus.setStatusCode("Error");
                    resultStatus.setStatusInfo("密码不正确!");
                    return resultStatus;
                }
                resultStatus.setStatusCode("Success");
                resultStatus.setStatusInfo("登录成功!");
                return resultStatus;
            }

        } catch (DaoException e) {
            logger.error("登录时查询数据库出现异常。", e);
            resultStatus.setStatusCode("Error");
            resultStatus.setStatusInfo("登录时查询数据库出现异常:" + e.getErrorMessage());
            return resultStatus;
        }
    }

    @Override
    public ResultStatus register(UserMainInfo request) {
        return null;
    }

    @Override
    public ResultStatus registerWithVerifyCode(UserMainInfo request) {
        return null;
    }

    private User _parseReqBody(UserMainInfo request, User user) {
        user.setUsername(request.getUsername());
        user.setPassword(MD5Util.md5Hex(request.getPassword()));
        return user;
    }
}
