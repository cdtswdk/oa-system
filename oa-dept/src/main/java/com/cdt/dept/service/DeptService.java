package com.cdt.dept.service;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.DeptInf;

import java.util.List;

public interface DeptService {

    DataResult<DeptInf> findDeptById(int id);

    DataResult<List<DeptInf>> getDeptList();

    DataResult<PageResult<DeptInf>> getDeptListByPage(DatatableInfo<DeptInf> datatableInfo, String searchType, String searchInput);

    DataResult<DeptInf> deleteDeptById(Integer id);

    DataResult<DeptInf> editDept(DeptInf deptInf);

    DataResult<DeptInf> addDept(DeptInf deptInf);
}
