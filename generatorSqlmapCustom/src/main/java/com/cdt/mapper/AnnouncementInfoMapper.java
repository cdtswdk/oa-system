package com.cdt.mapper;

import com.cdt.model.AnnouncementInfo;
import com.cdt.model.AnnouncementInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnnouncementInfoMapper {
    int countByExample(AnnouncementInfoExample example);

    int deleteByExample(AnnouncementInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AnnouncementInfo record);

    int insertSelective(AnnouncementInfo record);

    List<AnnouncementInfo> selectByExampleWithBLOBs(AnnouncementInfoExample example);

    List<AnnouncementInfo> selectByExample(AnnouncementInfoExample example);

    AnnouncementInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AnnouncementInfo record, @Param("example") AnnouncementInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") AnnouncementInfo record, @Param("example") AnnouncementInfoExample example);

    int updateByExample(@Param("record") AnnouncementInfo record, @Param("example") AnnouncementInfoExample example);

    int updateByPrimaryKeySelective(AnnouncementInfo record);

    int updateByPrimaryKeyWithBLOBs(AnnouncementInfo record);

    int updateByPrimaryKey(AnnouncementInfo record);
}