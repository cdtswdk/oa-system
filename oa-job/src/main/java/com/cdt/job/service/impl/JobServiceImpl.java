package com.cdt.job.service.impl;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.job.mapper.JobMapper;
import com.cdt.job.service.JobService;
import com.cdt.model.JobInf;
import com.github.pagehelper.PageHelper;
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
public class JobServiceImpl implements JobService {

    @Autowired
    private JobMapper jobMapper;

    @Override
    public DataResult<JobInf> findJobById(int id) {
        try {
            JobInf jobInf = this.jobMapper.selectByPrimaryKey(id);
            if (jobInf != null) {
                return DataResult.success(jobInf, "查询成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.notfound("查询失败");
    }

    @Override
    public DataResult<List<JobInf>> getJobList() {
        try {
            List<JobInf> jobInfs = this.jobMapper.selectAll();
            return DataResult.success(jobInfs,"查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<PageResult<JobInf>> getJobListByPage(DatatableInfo<JobInf> datatableInfo) {
        try {
            Example example = new Example(JobInf.class);
            PageHelper.startPage(datatableInfo.getOffset(), datatableInfo.getPageSize());
            List<JobInf> jobInfs = this.jobMapper.selectAll();
            PageResult<JobInf> pageResult = new PageResult<>();
            pageResult.setItems(jobInfs);
            pageResult.setTotal((long) jobInfs.size());
            pageResult.setTotalPage(this.jobMapper.selectCountByExample(example));
            return DataResult.success(pageResult, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<JobInf> deleteJobById(Integer id) {
        try {
            int count = this.jobMapper.deleteByPrimaryKey(id);
            if (count > 0) {
                return DataResult.success(null, "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("删除失败");
    }

    @Override
    public DataResult<JobInf> editJob(JobInf jobInf) {
        try {
            int count = this.jobMapper.updateByPrimaryKeySelective(jobInf);
            if (count > 0) {
                return DataResult.success(null, "修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("修改失败");
    }

    @Override
    public DataResult<JobInf> addJob(JobInf jobInf) {
        try {
            int count = this.jobMapper.insert(jobInf);
            if (count > 0) {
                return DataResult.success(null, "增加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("增加失败");
    }
}
