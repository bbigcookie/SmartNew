package com.dcits.user.controller;

import com.dcits.user.constant.ResultStatus;
import com.dcits.user.dao.bean.UserMainInfo;
import com.dcits.user.log.ILogger;
import com.dcits.user.log.LoggerFactory;
import com.dcits.user.service.interfaces.IUserCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gaojunc on 2018/3/13 19:35.
 * Created Reason: 用户登录controller
 */
@RestController
@RequestMapping(value = "/login")
@Api(value = "/login")
public class UserLoginController {
    private static ILogger log = LoggerFactory.getLogger(UserLoginController.class);
    @Autowired
    private IUserCommand userLogin;

    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", notes = "使用账号密码登录")
    @ApiResponses({
            @ApiResponse(code = 200, message = "登录成功")
    })
    @HystrixCommand(fallbackMethod = "errorFallBack")
    public ResultStatus login(@RequestBody UserMainInfo user) {
        return userLogin.loginWithPassword(user);

    }

    public ResultStatus errorFallBack(UserMainInfo userMainInfo) {
        String name = userMainInfo.getNickname().equals("") ? userMainInfo.getUsername() : userMainInfo.getNickname();
        ResultStatus resultStatus = new ResultStatus();
        resultStatus.setStatusCode("Error");
        resultStatus.setStatusInfo("Hello," + name + "出错了，出发FallBack");
        return resultStatus;
    }
}