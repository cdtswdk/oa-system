package com.cdt.user.service.impl;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.SingleToken;
import com.cdt.model.UserInf;
import com.cdt.user.mapper.UserMapper;
import com.cdt.user.service.UserService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
    public DataResult<UserInf> findUserById(int id) {
        try {
            UserInf userInf = this.userMapper.selectByPrimaryKey(id);
            return DataResult.success(userInf, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
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
                if (userInf1.getStatus() == 1) {
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
            if (userInf.getStatus() == 1) {
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
    public DataResult<List<UserInf>> getUserList() {
        try {
            List<UserInf> userInfList = this.userMapper.selectAll();
            return DataResult.success(userInfList, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<PageResult<UserInf>> getUserListByPage(DatatableInfo<UserInf> datatableInfo, String searchType, String searchInput) {
        try {
            Example example = new Example(UserInf.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotEmpty(searchInput)) {
                if (StringUtils.isNotEmpty(searchType)) {
                    if ("1".equals(searchType)) {
                        criteria.andLike("loginname", "%" + searchInput + "%");
                    } else if ("2".equals(searchType)) {
                        criteria.andLike("status", "%" + searchInput + "%");
                    } else if ("3".equals(searchType)) {
                        criteria.andLike("username", "%" + searchInput + "%");
                    }
                }
            }
            criteria.andEqualTo("status", 0);
            PageHelper.startPage(datatableInfo.getPage(), datatableInfo.getPageSize());
            List<UserInf> userInfList = this.userMapper.selectByExample(example);
            PageResult<UserInf> pageResult = new PageResult<>();
            pageResult.setItems(userInfList);
            List<UserInf> userInfList1 = this.userMapper.selectByExample(example);
            pageResult.setTotal((long) userInfList1.size());
            pageResult.setTotalPage((int) Math.ceil((double) userInfList1.size() / datatableInfo.getPageSize()));
            return DataResult.success(pageResult, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
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
    public DataResult<UserInf> registerUserInf(MultipartFile file, UserInf userInf) {
        try {
            if (file != null) {
                String imgName = upload(file);
                userInf.setImgname(imgName);
                userInf.setStatus(0);
                int count = this.userMapper.insertSelective(userInf);
                if (count > 0) {
                    return DataResult.success(userInf, "注册成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("注册失败");
    }

    @Override
    public DataResult<UserInf> deleteUserById(Integer id) {
        try {
            UserInf userInf = this.userMapper.selectByPrimaryKey(id);
            String imgName = userInf.getImgname();
            // 1、删除数据
            int count = this.userMapper.deleteByPrimaryKey(id);
            //2、删除文件
            String path = "C:\\Users\\14660\\Pictures\\Camera Roll";
            File file = new File(path + File.separator + imgName);
            boolean del = file.delete();
            if (count > 0 && del) {
                return DataResult.success(null, "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("删除失败");
    }

    @Override
    public DataResult<UserInf> editUser(MultipartFile file, UserInf userInf) {
        try {
            if (file == null) {
                int count = this.userMapper.updateByPrimaryKeySelective(userInf);
                if (count > 0) {
                    return DataResult.success(null, "修改成功");
                }
            } else {
                String preImgName = userInf.getImgname();
                //1、删除原头像文件
                String path = "C:\\Users\\14660\\Pictures\\Camera Roll";
                File preFile = new File(path + File.separator + preImgName);
                boolean del = preFile.delete();
                // 2、上传新头像文件
                String upload = upload(file);
                userInf.setImgname(upload);
                // 3、修改数据
                int count = this.userMapper.updateByPrimaryKeySelective(userInf);
                if (count > 0 && del) {
                    return DataResult.success(null, "修改成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("修改失败");
    }

    @Override
    public DataResult<UserInf> userLogout() {
        return DataResult.success(null, "登出成功");
    }

    public String upload(MultipartFile file) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String newFileName = "";
        try {
            // 1、获取文件名
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isBlank(originalFilename)) {
                return null;
            }
            // 2、定义一个文件路径用于存放文件
            String path = "C:\\Users\\14660\\Pictures\\Camera Roll";
            // 3、获取来自网络端上传的文件，以流的形式来获取 ：输入流
            bis = new BufferedInputStream(file.getInputStream());
            // 4、定义字节缓冲数据，用于缓冲输入流的数据
            byte[] buff = new byte[1024];
            // 定义一个int变量，用于存放单次读取数据的字节个数
            int len = 0;
            // 5、创建文件对象
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            newFileName = uuid + "-" + originalFilename;
            File writeFile = new File(path + File.separator + newFileName);
            // 6、创建输出流，并通过输出流将文件写到硬盘
            bos = new BufferedOutputStream(new FileOutputStream(writeFile));
            // 7、进行循环的读写操作
            while ((len = bis.read(buff)) != -1) {//读取--读取到缓冲字节数组中
                //写出
                bos.write(buff, 0, len);
                //刷新流
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 8、关闭流
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newFileName;
    }
}
