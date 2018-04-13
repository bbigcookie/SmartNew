package com.dcits.user.dao.bean;

/**
 * Created by gaojunc on 2018/3/14 15:52.
 * Created Reason: 用户注册登录时提供三种方式的JavaBean通用接口
 */
public interface IUserInfo {
    String getUsername();

    String getNickname();

    String getPhoneNum();
}
