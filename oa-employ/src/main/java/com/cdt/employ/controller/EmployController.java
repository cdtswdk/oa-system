package com.cdt.employ.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.employ.service.EmployService;
import com.cdt.model.EmployeeInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @RequestMapping(value = "/employInfo/{id}", method = RequestMethod.GET)
    public DataResult<EmployeeInf> getEmployeeInfo(@PathVariable("id") int id) {
        return this.employService.findEmployById(id);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public DataResult<List<EmployeeInf>> getEmployList() {
        return this.employService.getEmployList();
    }

    @RequestMapping(value = "/getListByPage", method = RequestMethod.GET)
    public DataResult<PageResult<EmployeeInf>> getEmployListByPage(DatatableInfo<EmployeeInf> datatableInfo,
                                                                   String searchType, String searchInput) {
        return this.employService.getEmployListByPage(datatableInfo, searchType, searchInput);
    }

    @RequestMapping(value = "/deleteEmploy/{id}", method = RequestMethod.POST)
    public DataResult<EmployeeInf> deleteEmploy(@PathVariable("id") Integer id) {
        return this.employService.deleteEmployById(id);
    }

    @RequestMapping(value = "/editEmploy", method = RequestMethod.POST)
    public DataResult<EmployeeInf> editEmploy(@RequestParam(required = false, name = "file") MultipartFile file,
                                              @RequestParam(name = "editEmployee") String editEmployee) {
        EmployeeInf employeeInf = JSONObject.parseObject(editEmployee, EmployeeInf.class);

        return this.employService.editEmploy(file, employeeInf);
    }

    @RequestMapping(value = "/addEmploy", method = RequestMethod.POST)
    public DataResult<EmployeeInf> addEmploy(@RequestParam(name = "file") MultipartFile file,
                                             @RequestParam(name = "addEmployee") String addEmployee) {
        EmployeeInf employeeInf = JSONObject.parseObject(addEmployee, EmployeeInf.class);

        return this.employService.addEmploy(file, employeeInf);
    }
}
