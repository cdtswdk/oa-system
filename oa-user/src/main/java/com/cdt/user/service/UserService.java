package com.cdt.user.service;


import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.UserInf;

import java.util.List;

public interface UserService {
    DataResult<UserInf> findUserById(int id);

    DataResult<UserInf> userLogin(UserInf userInf);

    DataResult<UserInf> getUserInfo(String loginname);

    DataResult<UserInf> userLogout();

    DataResult<UserInf> getPerInf(String loginname);

    DataResult<UserInf> updateUserInf(UserInf userInf);

    DataResult<UserInf> updatePassword(String loginname, String prePassword, String newPassword, String cfmPassword);

    DataResult<UserInf> registerUserInf(UserInf userInf);

    DataResult<List<UserInf>> getUserList();

    DataResult<PageResult<UserInf>> getUserListByPage(DatatableInfo<UserInf> datatableInfo, String searchType, String searchInput);

    DataResult<UserInf> deleteUserById(Integer id);

    DataResult<UserInf> editUser(UserInf userInf);
}
