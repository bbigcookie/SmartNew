package com.dcits.user.dao.bean;

/**
 * Created by gaojunc on 2018/3/13 17:51.
 * Created Reason: 用户在web端进行基本操作时，需要向后台传输而填写的主要信息
 */
public class UserMainInfo {
    private String password;

    private String username;

    private String phoneNum;

    private String nickname;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
