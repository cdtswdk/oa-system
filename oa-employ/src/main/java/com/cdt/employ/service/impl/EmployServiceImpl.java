package com.cdt.employ.service.impl;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.employ.mapper.EmployMapper;
import com.cdt.employ.service.EmployService;
import com.cdt.model.EmployeeInf;
import com.github.pagehelper.PageHelper;
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
    public EmployeeInf findEmployById(int id) {
        return this.employMapper.selectByPrimaryKey(id);
    }

    @Override
    public DataResult<PageResult<EmployeeInf>> getEmployList(DatatableInfo<EmployeeInf> datatableInfo) {
        try {
            Example example = new Example(EmployeeInf.class);
            Example.Criteria criteria = example.createCriteria();
            PageHelper.startPage(datatableInfo.getOffset(), datatableInfo.getPageSize());
            List<EmployeeInf> deptInfs = this.employMapper.selectAll();
            PageResult<EmployeeInf> pageResult = new PageResult<>();
            pageResult.setItems(deptInfs);
            pageResult.setTotal((long) deptInfs.size());
            pageResult.setTotalPage(this.employMapper.selectCountByExample(example));
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
            if(count > 0){
                return DataResult.success(null,"增加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("增加失败");
    }
}
