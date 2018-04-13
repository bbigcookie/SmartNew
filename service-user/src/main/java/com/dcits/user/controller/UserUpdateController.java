package com.dcits.user.controller;

import com.dcits.user.constant.ResultStatus;
import com.dcits.user.dao.bean.UserMainInfo;
import com.dcits.user.dao.bean.UserPersonalInfo;
import com.dcits.user.service.interfaces.IUserUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gaojunc on 2018/3/14 10:36.
 * Created Reason:用户修改信息controller
 */
@RestController
@RequestMapping(value = "/update")
@Api(value = "更新操作")
public class UserUpdateController {

    @Autowired
    private IUserUpdate userUpdate;

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码", notes = "使用账号密码形式注册用户")
    public ResultStatus modifyPassword(@RequestBody UserMainInfo userMainInfo) {
        return userUpdate.modifyPassword(userMainInfo);
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改个人信息", notes = "修改用户的个人信息")
    public ResultStatus modifyPersonInfo(@PathVariable(value = "id") String userName, @RequestBody UserPersonalInfo user) {
        //将账户名放入bean
        user.setUsername(userName);
        user.setUserid("smart_" + userName);
        return userUpdate.modifyCommonInfo(user);
    }

}
