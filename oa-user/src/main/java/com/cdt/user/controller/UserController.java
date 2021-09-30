package com.cdt.user.controller;

import com.cdt.common.pojo.DataResult;
import com.cdt.model.UserInf;
import com.cdt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/26 15:06
 * @Description:
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/findUser/{id}")
    public UserInf getUser(@PathVariable(value = "id") int id) {
        System.out.println(this.userService.findUserById(id));
        return this.userService.findUserById(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public DataResult<UserInf> login(UserInf userInf) {
        return this.userService.userLogin(userInf);
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public DataResult<UserInf> getUserInf(String token) {
        return this.userService.getUserInfo(token);
    }

    @RequestMapping(value = "/perInf", method = RequestMethod.GET)
    public DataResult<UserInf> getPerInf(String loginname) {
        return this.userService.getPerInf(loginname);
    }

    @RequestMapping(value = "/updateUserInf", method = RequestMethod.POST)
    public DataResult<UserInf> updateUserInf(UserInf userInf) {
        return this.userService.updateUserInf(userInf);
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public DataResult<UserInf> updatePwd(String loginname,
                                         String prePassword, String newPassword,
                                         String cfmPassword) {
        return this.userService.updatePassword(loginname, prePassword, newPassword, cfmPassword);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public DataResult<UserInf> register(UserInf userInf) {
        return this.userService.registerUserInf(userInf);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public DataResult<UserInf> logout() {
        return this.userService.userLogout();
    }
}
