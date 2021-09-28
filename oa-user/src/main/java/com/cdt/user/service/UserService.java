package com.cdt.user.service;


import com.cdt.common.pojo.DataResult;
import com.cdt.model.UserInf;

public interface UserService {
    UserInf findUserById(int id);

    DataResult<UserInf> userLogin(UserInf userInf);

    DataResult<UserInf> getUserInfo(String loginname);

    DataResult<UserInf> userLogout();

    DataResult<UserInf> getPerInf(String loginname);
}
