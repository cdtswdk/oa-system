package com.cdt.job.controller;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.job.service.JobService;
import com.cdt.model.JobInf;
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
@RequestMapping(value = "/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/jobInfo/{id}", method = RequestMethod.GET)
    public DataResult<JobInf> getJobInfo(@PathVariable("id") int id) {
        return this.jobService.findJobById(id);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public DataResult<List<JobInf>> getJobList() {
        return this.jobService.getJobList();
    }

    @RequestMapping(value = "/getListByPage", method = RequestMethod.GET)
    public DataResult<PageResult<JobInf>> getJobListByPage(DatatableInfo<JobInf> datatableInfo,
                                                           String searchType,String searchInput) {
        return this.jobService.getJobListByPage(datatableInfo,searchType,searchInput);
    }

    @RequestMapping(value = "/deleteJob/{id}", method = RequestMethod.POST)
    public DataResult<JobInf> deleteJob(@PathVariable("id") Integer id) {
        return this.jobService.deleteJobById(id);
    }

    @RequestMapping(value = "/editJob", method = RequestMethod.POST)
    public DataResult<JobInf> editJob(JobInf jobInf) {
        return this.jobService.editJob(jobInf);
    }

    @RequestMapping(value = "/addJob", method = RequestMethod.POST)
    public DataResult<JobInf> addJob(JobInf jobInf) {
        return this.jobService.addJob(jobInf);
    }
}
