package com.cdt.dept.service.impl;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.dept.mapper.DeptMapper;
import com.cdt.dept.service.DeptService;
import com.cdt.model.DeptInf;
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
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public DeptInf findDeptById(int id) {
        return this.deptMapper.selectByPrimaryKey(id);
    }

    @Override
    public DataResult<PageResult<DeptInf>> getDeptList(DatatableInfo<DeptInf> datatableInfo) {
        try {
            Example example = new Example(DeptInf.class);
            Example.Criteria criteria = example.createCriteria();
            PageHelper.startPage(datatableInfo.getOffset(), datatableInfo.getPageSize());
            List<DeptInf> deptInfs = this.deptMapper.selectAll();
            PageResult<DeptInf> pageResult = new PageResult<>();
            pageResult.setItems(deptInfs);
            pageResult.setTotal((long) deptInfs.size());
            pageResult.setTotalPage(this.deptMapper.selectCountByExample(example));
            return DataResult.success(pageResult, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<DeptInf> deleteDeptById(Integer id) {
        try {
            int count = this.deptMapper.deleteByPrimaryKey(id);
            if (count > 0) {
                return DataResult.success(null, "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("删除失败");
    }

    @Override
    public DataResult<DeptInf> editDept(DeptInf deptInf) {
        try {
            int count = this.deptMapper.updateByPrimaryKeySelective(deptInf);
            if (count > 0) {
                return DataResult.success(null, "修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("修改失败");
    }
}
