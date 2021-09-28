package com.cdt.user.controller;

import com.cdt.common.pojo.DataResult;
import com.cdt.model.UserInf;
import com.cdt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public DataResult<UserInf> logout() {
        return this.userService.userLogout();
    }
}
