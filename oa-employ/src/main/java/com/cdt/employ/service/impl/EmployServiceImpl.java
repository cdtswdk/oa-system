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
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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
            int count = this.employMapper.deleteByPrimaryKey(id);
            if (count > 0) {
                return DataResult.success(null, "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("删除失败");
    }

    @Override
    public DataResult<EmployeeInf> editEmploy(EmployeeInf employeeInf) {
        try {
            int count = this.employMapper.updateByPrimaryKeySelective(employeeInf);
            if (count > 0) {
                return DataResult.success(null, "修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("修改失败");
    }

    @Override
    public DataResult<EmployeeInf> addEmploy(EmployeeInf employeeInf) {
        try {
            int count = this.employMapper.insert(employeeInf);
            if (count > 0) {
                return DataResult.success(null, "增加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("增加失败");
    }
}
