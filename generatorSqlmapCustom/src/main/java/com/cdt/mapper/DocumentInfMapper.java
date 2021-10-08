package com.cdt.mapper;

import com.cdt.model.DocumentInf;
import com.cdt.model.DocumentInfExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocumentInfMapper {
    int countByExample(DocumentInfExample example);

    int deleteByExample(DocumentInfExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DocumentInf record);

    int insertSelective(DocumentInf record);

    List<DocumentInf> selectByExample(DocumentInfExample example);

    DocumentInf selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DocumentInf record, @Param("example") DocumentInfExample example);

    int updateByExample(@Param("record") DocumentInf record, @Param("example") DocumentInfExample example);

    int updateByPrimaryKeySelective(DocumentInf record);

    int updateByPrimaryKey(DocumentInf record);
}