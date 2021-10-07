package com.cdt.job.service;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.JobInf;

import java.util.List;

public interface JobService {

    DataResult<JobInf> findJobById(int id);

    DataResult<List<JobInf>> getJobList();

    DataResult<PageResult<JobInf>> getJobListByPage(DatatableInfo<JobInf> datatableInfo);

    DataResult<JobInf> deleteJobById(Integer id);

    DataResult<JobInf> editJob(JobInf jobInf);

    DataResult<JobInf> addJob(JobInf jobInf);
}
