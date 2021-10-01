package com.cdt.employ.service;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.EmployeeInf;

public interface EmployService {

    EmployeeInf findEmployById(int id);

    DataResult<PageResult<EmployeeInf>> getEmployList(DatatableInfo<EmployeeInf> datatableInfo);

    DataResult<EmployeeInf> deleteEmployById(Integer id);

    DataResult<EmployeeInf> editEmploy(EmployeeInf employeeInf);

    DataResult<EmployeeInf> addEmploy(EmployeeInf employeeInf);
}
