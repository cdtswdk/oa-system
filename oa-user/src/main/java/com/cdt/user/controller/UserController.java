package com.cdt.user.controller;

import com.cdt.user.domain.UserInf;
import com.cdt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
