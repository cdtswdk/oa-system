package com.cdt.dept.controller;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.dept.service.DeptService;
import com.cdt.model.DeptInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/26 15:06
 * @Description:
 */
@RestController
@RequestMapping(value = "/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/deptInfo/{id}")
    public DataResult<DeptInf> getDeptInfo(@PathVariable("id") int id){
        return this.deptService.findDeptById(id);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public DataResult<List<DeptInf>> getDeptList() {
        return this.deptService.getDeptList();
    }

    @RequestMapping(value = "/getListByPage", method = RequestMethod.GET)
    public DataResult<PageResult<DeptInf>> getDeptListByPage(DatatableInfo<DeptInf> datatableInfo) {
        return this.deptService.getDeptListByPage(datatableInfo);
    }

    @RequestMapping(value = "/deleteDept/{id}", method = RequestMethod.POST)
    public DataResult<DeptInf> deleteDept(@PathVariable("id") Integer id) {
        return this.deptService.deleteDeptById(id);
    }

    @RequestMapping(value = "/editDept", method = RequestMethod.POST)
    public DataResult<DeptInf> editDept(DeptInf deptInf) {
        return this.deptService.editDept(deptInf);
    }

    @RequestMapping(value = "/addDept", method = RequestMethod.POST)
    public DataResult<DeptInf> addDept(DeptInf deptInf) {
        return this.deptService.addDept(deptInf);
    }
}
