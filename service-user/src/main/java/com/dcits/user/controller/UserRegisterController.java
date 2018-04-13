package com.dcits.user.controller;

import com.dcits.user.constant.ResultStatus;
import com.dcits.user.dao.bean.UserMainInfo;
import com.dcits.user.log.ILogger;
import com.dcits.user.log.LoggerFactory;
import com.dcits.user.service.interfaces.IUserCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gaojunc on 2018/3/13 13:53.
 * Created Reason: 用户注册controller
 */
@RestController
@RequestMapping(value = "/register")
@Api(value = "register")
public class UserRegisterController {

    private ILogger logger = LoggerFactory.getLogger(UserRegisterController.class);

    @Autowired
    private IUserCommand userRegister;

    @RequestMapping(value = "registerUsingUsername", method = RequestMethod.POST)
    @ApiOperation(value = "注册新用户（账号密码）", notes = "使用账号密码形式注册用户")
    public ResultStatus registerNewUser(@RequestBody UserMainInfo user) {
        return userRegister.register(user);
    }
}
