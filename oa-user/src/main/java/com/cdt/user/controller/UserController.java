package com.cdt.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.UserInf;
import com.cdt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public DataResult<UserInf> getUser(@PathVariable(value = "id") int id) {
        return this.userService.findUserById(id);
    }

    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public DataResult<List<UserInf>> getUserList() {
        return this.userService.getUserList();
    }

    @RequestMapping(value = "/getUserListByPage", method = RequestMethod.GET)
    public DataResult<PageResult<UserInf>> getUserListByPage(DatatableInfo<UserInf> datatableInfo,
                                                             String searchType, String searchInput) {
        return this.userService.getUserListByPage(datatableInfo, searchType, searchInput);
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

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.POST)
    public DataResult<UserInf> deleteUser(@PathVariable("id") Integer id) {
        return this.userService.deleteUserById(id);
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public DataResult<UserInf> editUser(UserInf userInf) {
        return this.userService.editUser(userInf);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public DataResult<UserInf> register(@RequestParam(required = false, name = "file") MultipartFile file,
                                        @RequestParam(name = "registerUser", required = false) String registerUser) {
        UserInf userInf = JSONObject.parseObject(registerUser, UserInf.class);
        if (file != null) {
            System.out.println("filename " + file.getName());
            System.out.println("OriginalFilename " + file.getOriginalFilename());
            System.out.println("ContentType " + file.getContentType());
            userInf.setImgname(file.getOriginalFilename());
        }
        return this.userService.registerUserInf(userInf);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public DataResult<UserInf> logout() {
        return this.userService.userLogout();
    }
}
