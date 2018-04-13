package com.dcits.user.service.interfaces;

import com.dcits.user.constant.ResultStatus;
import com.dcits.user.dao.bean.UserMainInfo;
import com.dcits.user.dao.bean.UserPersonalInfo;

/**
 * Created by gaojunc on 2018/3/14 10:26.
 * Created Reason: 用户信息更新接口
 */
public interface IUserUpdate extends IUser {

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    ResultStatus modifyPassword(UserMainInfo user);

    /**
     * 修改其他通用信息
     *
     * @param user
     * @return
     */
    ResultStatus modifyCommonInfo(UserPersonalInfo user);

}
