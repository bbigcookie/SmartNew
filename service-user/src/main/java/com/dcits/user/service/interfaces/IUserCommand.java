package com.dcits.user.service.interfaces;

import com.dcits.user.constant.ResultStatus;
import com.dcits.user.dao.bean.UserMainInfo;

import java.io.IOException;

/**
 * Created by gaojunc on 2017/12/24 17:02.
 * Created Reason: 用户注册登录接口
 */
public interface IUserCommand extends IUser {

    /**
     * 使用手机号验证码登录
     *
     * @return 响应报文
     */
    ResultStatus loginWithVerifyCode(UserMainInfo requestBody) throws IOException;

    /**
     * 使用账号密码登录
     *
     * @return 响应报文
     */
    ResultStatus loginWithPassword(UserMainInfo requestBody);

    /**
     * 注册用户
     *
     * @param request 请求报文
     * @return
     */
    ResultStatus register(UserMainInfo request);

    /**
     * 使用验证码注册新用户
     *
     * @return
     */
    ResultStatus registerWithVerifyCode(UserMainInfo request);
}
