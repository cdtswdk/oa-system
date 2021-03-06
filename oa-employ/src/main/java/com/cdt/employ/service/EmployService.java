package com.cdt.employ.service;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.EmployeeInf;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployService {

    DataResult<EmployeeInf> findEmployById(int id);

    DataResult<List<EmployeeInf>> getEmployList();

    DataResult<PageResult<EmployeeInf>> getEmployListByPage(DatatableInfo<EmployeeInf> datatableInfo, String searchType, String searchInput);

    DataResult<EmployeeInf> deleteEmployById(Integer id);

    DataResult<EmployeeInf> editEmploy(MultipartFile file, EmployeeInf employeeInf);

    DataResult<EmployeeInf> addEmploy(MultipartFile file, EmployeeInf employeeInf);
}
