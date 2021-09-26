package com.cdt.user.service.impl;

import com.cdt.user.domain.UserInf;
import com.cdt.user.mapper.UserMapper;
import com.cdt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
