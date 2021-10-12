package com.cdt.employ.service.impl;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.employ.mapper.EmployMapper;
import com.cdt.employ.service.EmployService;
import com.cdt.model.EmployeeInf;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/29 16:30
 * @Description:
 */
@Service
public class EmployServiceImpl implements EmployService {

    @Autowired
    private EmployMapper employMapper;

    @Override
    public DataResult<EmployeeInf> findEmployById(int id) {
        try {
            EmployeeInf employeeInf = this.employMapper.selectByPrimaryKey(id);
            if (employeeInf != null) {
                return DataResult.success(employeeInf, "查询成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.notfound("查询失败");
    }

    @Override
    public DataResult<List<EmployeeInf>> getEmployList() {
        try {
            List<EmployeeInf> employeeInfs = this.employMapper.selectAll();
            return DataResult.success(employeeInfs, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<PageResult<EmployeeInf>> getEmployListByPage(DatatableInfo<EmployeeInf> datatableInfo, String searchType, String searchInput) {
        try {
            Example example = new Example(EmployeeInf.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotEmpty(searchInput)) {
                if (StringUtils.isNotEmpty(searchType)) {
                    if ("1".equals(searchType)) {
                        criteria.andLike("name", "%" + searchInput + "%");
                    } else if ("2".equals(searchType)) {
                        criteria.andLike("sex", "%" + searchInput + "%");
                    } else if ("3".equals(searchType)) {
                        criteria.andLike("deptId", "%" + searchInput + "%");
                    } else if ("4".equals(searchType)) {
                        criteria.andLike("jobId", "%" + searchInput + "%");
                    }
                }
            }
            PageHelper.startPage(datatableInfo.getPage(), datatableInfo.getPageSize());
            List<EmployeeInf> employeeInfs = this.employMapper.selectByExample(example);
            PageResult<EmployeeInf> pageResult = new PageResult<>();
            pageResult.setItems(employeeInfs);
            List<EmployeeInf> employeeInfList = this.employMapper.selectByExample(example);
            pageResult.setTotal((long) employeeInfList.size());
            pageResult.setTotalPage((int) Math.ceil((double) employeeInfList.size() / datatableInfo.getPageSize()));
            return DataResult.success(pageResult, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<EmployeeInf> deleteEmployById(Integer id) {
        try {
            EmployeeInf employeeInf = this.employMapper.selectByPrimaryKey(id);
            String imgName = employeeInf.getImgname();
            // 1、删除数据
            int count = this.employMapper.deleteByPrimaryKey(id);
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
    public DataResult<EmployeeInf> editEmploy(MultipartFile file, EmployeeInf employeeInf) {
        try {
            if (file == null) {
                int count = this.employMapper.updateByPrimaryKeySelective(employeeInf);
                if (count > 0) {
                    return DataResult.success(null, "修改成功");
                }
            } else {
                String preImgName = employeeInf.getImgname();
                //1、删除原头像文件
                String path = "C:\\Users\\14660\\Pictures\\Camera Roll";
                File preFile = new File(path + File.separator + preImgName);
                boolean del = preFile.delete();
                // 2、上传新头像文件
                String upload = upload(file);
                employeeInf.setImgname(upload);
                // 3、修改数据
                int count = this.employMapper.updateByPrimaryKeySelective(employeeInf);
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
    public DataResult<EmployeeInf> addEmploy(MultipartFile file, EmployeeInf employeeInf) {
        try {
            if (file != null) {
                String imgName = upload(file);
                employeeInf.setImgname(imgName);
                int count = this.employMapper.insertSelective(employeeInf);
                if (count > 0) {
                    return DataResult.success(employeeInf, "注册成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("增加失败");
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
