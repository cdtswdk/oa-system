package com.cdt.user.service.impl;

import com.cdt.common.pojo.DataResult;
import com.cdt.model.SingleToken;
import com.cdt.model.UserInf;
import com.cdt.user.mapper.UserMapper;
import com.cdt.user.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/26 16:04
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInf findUserById(int id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Override
    public DataResult<UserInf> userLogin(UserInf userInf) {
        String loginname = userInf.getLoginname();
        String password = userInf.getPassword();
        Example example = new Example(UserInf.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loginname", loginname);
        List<UserInf> userInfList = this.userMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(userInfList)) {
            UserInf userInf1 = userInfList.get(0);
            if (password.equals(userInf1.getPassword())) {
                SingleToken singleToken = new SingleToken();
                HashMap<String, String> hashMap = new HashMap<>();
                if ("admin".equals(loginname)) {
                    hashMap.put("admin", "admin-token," + loginname);
                } else {
                    hashMap.put("editor", "editor-token," + loginname);
                }
                singleToken.setTokenMap(hashMap);
                userInf1.setSingleToken(singleToken);
                return DataResult.success(userInf1, "登陆成功");
            }
        }
        return DataResult.notfound("账号或密码错误");
    }

    @Override
    public DataResult<UserInf> getUserInfo(String token) {
        Example example = new Example(UserInf.class);
        Example.Criteria criteria = example.createCriteria();
        String[] split = token.split(",");
        criteria.andEqualTo("loginname", split[1]);
        List<UserInf> userInfList = this.userMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(userInfList)) {
            UserInf userInf = userInfList.get(0);
            SingleToken singleToken = new SingleToken();
            HashMap<String, String> hashMap = new HashMap<>();
            if ("admin".equals(split[1])) {
                hashMap.put("admin", "admin-token");
                singleToken.setTokenMap(hashMap);
                singleToken.setRoles(new String[]{"admin"});
                singleToken.setIntroduction("I am a super administrator");
                singleToken.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
                singleToken.setName("Super Admin");
            } else {
                hashMap.put("editor", "editor-token");
                singleToken.setTokenMap(hashMap);
                singleToken.setRoles(new String[]{"editor"});
                singleToken.setIntroduction("I am an editor");
                singleToken.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
                singleToken.setName("Normal Editor");
            }
            userInf.setSingleToken(singleToken);
            return DataResult.success(userInf, "查询成功");
        }
        return DataResult.notfound("查询失败");
    }

    @Override
    public DataResult<UserInf> userLogout() {
        return DataResult.success(null, "登出成功");
    }

    @Override
    public DataResult<UserInf> getPerInf(String loginname) {
        Example example = new Example(UserInf.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loginname", loginname);
        List<UserInf> userInfList = this.userMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(userInfList)) {
            return DataResult.success(userInfList.get(0), "查询成功");
        }
        return DataResult.notfound("查询失败");
    }

    @Override
    public DataResult<UserInf> updateUserInf(UserInf userInf) {
        try {
            this.userMapper.updateByPrimaryKeySelective(userInf);
            return DataResult.success(userInf, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("修改失败");
    }

    @Override
    public DataResult<UserInf> updatePassword(String loginname, String prePassword, String newPassword, String cfmPassword) {
        Example example = new Example(UserInf.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loginname", loginname);
        List<UserInf> userInfList = this.userMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(userInfList)) {
            UserInf userInf = userInfList.get(0);
            if (!prePassword.equals(userInf.getPassword())) {
                return DataResult.serverError("密码输入不正确");
            }
            if (!newPassword.equals(cfmPassword)) {
                return DataResult.serverError("两次密码输入不一致");
            }
            userInf.setPassword(newPassword);
            this.userMapper.updateByPrimaryKey(userInf);
            return DataResult.success(userInf, "修改成功");
        }
        return DataResult.notfound("查询不到该用户");
    }

    @Override
    public DataResult<UserInf> registerUserInf(UserInf userInf) {
        try {
            userInf.setStatus(1);
            this.userMapper.insertSelective(userInf);
            return DataResult.success(userInf, "注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("注册失败");
    }
}
