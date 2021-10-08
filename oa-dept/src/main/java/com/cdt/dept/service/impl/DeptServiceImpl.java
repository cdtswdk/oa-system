package com.cdt.dept.service.impl;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.dept.mapper.DeptMapper;
import com.cdt.dept.service.DeptService;
import com.cdt.model.DeptInf;
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
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public DataResult<DeptInf> findDeptById(int id) {
        try {
            DeptInf deptInf = this.deptMapper.selectByPrimaryKey(id);
            return DataResult.success(deptInf, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.notfound("查询失败");
    }

    @Override
    public DataResult<List<DeptInf>> getDeptList() {
        try {
            List<DeptInf> deptInfs = this.deptMapper.selectAll();
            return DataResult.success(deptInfs, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<PageResult<DeptInf>> getDeptListByPage(DatatableInfo<DeptInf> datatableInfo, String searchType, String searchInput) {
        try {
            Example example = new Example(DeptInf.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotEmpty(searchInput)) {
                if (StringUtils.isNotEmpty(searchType)) {
                    if ("1".equals(searchType)) {
                        criteria.andLike("name", "%" + searchInput + "%");
                    } else if ("2".equals(searchType)) {
                        criteria.andLike("remark", "%" + searchInput + "%");
                    }
                }
            }
            PageHelper.startPage(datatableInfo.getPage(), datatableInfo.getPageSize());
            List<DeptInf> deptInfs = this.deptMapper.selectByExample(example);
            PageResult<DeptInf> pageResult = new PageResult<>();
            pageResult.setItems(deptInfs);
            List<DeptInf> deptInfList = this.deptMapper.selectByExample(example);
            pageResult.setTotal((long) deptInfList.size());
            pageResult.setTotalPage((int) Math.ceil((double) deptInfList.size() / datatableInfo.getPageSize()));
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

    @Override
    public DataResult<DeptInf> addDept(DeptInf deptInf) {
        try {
            int count = this.deptMapper.insert(deptInf);
            if (count > 0) {
                return DataResult.success(null, "增加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("增加失败");
    }
}
