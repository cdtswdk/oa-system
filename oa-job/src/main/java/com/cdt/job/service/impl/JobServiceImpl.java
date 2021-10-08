package com.cdt.job.service.impl;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.job.mapper.JobMapper;
import com.cdt.job.service.JobService;
import com.cdt.model.JobInf;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
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
            return DataResult.success(jobInfs, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<PageResult<JobInf>> getJobListByPage(DatatableInfo<JobInf> datatableInfo, String searchType, String searchInput) {
        try {
            Example example = new Example(JobInf.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotEmpty(searchInput)) {
                if (StringUtils.isNotEmpty(searchType)) {
                    if ("1".equals(searchType)) {
                        criteria.andLike("name", "%" + searchInput + "%");
                    } else if ("2".equals(searchType)) {
                        criteria.andLike("remark", "%" + searchInput + "%");
                    }
                }
            }
            PageHelper.startPage(datatableInfo.getPage(), datatableInfo.getPageSize());
            List<JobInf> jobInfs = this.jobMapper.selectByExample(example);
            PageResult<JobInf> pageResult = new PageResult<>();
            pageResult.setItems(jobInfs);
            List<JobInf> jobInfList = this.jobMapper.selectByExample(example);
            pageResult.setTotal((long) jobInfList.size());
            pageResult.setTotalPage((int) Math.ceil((double) jobInfList.size() / datatableInfo.getPageSize()));
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
