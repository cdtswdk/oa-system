package com.cdt.employ.controller;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.employ.service.EmployService;
import com.cdt.model.EmployeeInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/26 15:06
 * @Description:
 */
@RestController
@RequestMapping(value = "/employ")
public class EmployController {

    @Autowired
    private EmployService employService;

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public DataResult<PageResult<EmployeeInf>> getEmployList(DatatableInfo<EmployeeInf> datatableInfo) {
        return this.employService.getEmployList(datatableInfo);
    }

    @RequestMapping(value = "/deleteEmploy/{id}", method = RequestMethod.POST)
    public DataResult<EmployeeInf> deleteEmploy(@PathVariable("id") Integer id) {
        return this.employService.deleteEmployById(id);
    }

    @RequestMapping(value = "/editEmploy", method = RequestMethod.POST)
    public DataResult<EmployeeInf> editEmploy(EmployeeInf employeeInf) {
        return this.employService.editEmploy(employeeInf);
    }

    @RequestMapping(value = "/addEmploy", method = RequestMethod.POST)
    public DataResult<EmployeeInf> addEmploy(EmployeeInf employeeInf) {
        return this.employService.addEmploy(employeeInf);
    }
}