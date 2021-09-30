package com.cdt.dept.service;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.DeptInf;

public interface DeptService {

    DeptInf findDeptById(int id);

    DataResult<PageResult<DeptInf>> getDeptList(DatatableInfo<DeptInf> datatableInfo);

    DataResult<DeptInf> deleteDeptById(Integer id);

    DataResult<DeptInf> editDept(DeptInf deptInf);
}
