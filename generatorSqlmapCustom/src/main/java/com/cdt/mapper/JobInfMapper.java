package com.cdt.mapper;

import com.cdt.model.JobInf;
import com.cdt.model.JobInfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JobInfMapper {
    int countByExample(JobInfExample example);

    int deleteByExample(JobInfExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(JobInf record);

    int insertSelective(JobInf record);

    List<JobInf> selectByExample(JobInfExample example);

    JobInf selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") JobInf record, @Param("example") JobInfExample example);

    int updateByExample(@Param("record") JobInf record, @Param("example") JobInfExample example);

    int updateByPrimaryKeySelective(JobInf record);

    int updateByPrimaryKey(JobInf record);
}