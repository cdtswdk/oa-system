package com.cdt.announcement.service;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.AnnouncementInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AnnouncementService {

    DataResult<AnnouncementInfo> findAnnouncementById(int id);

    DataResult<List<AnnouncementInfo>> getAnnouncementList();

    DataResult<PageResult<AnnouncementInfo>> getAnnouncementListByPage(DatatableInfo<AnnouncementInfo> datatableInfo, String searchType, String searchInput);

    DataResult<AnnouncementInfo> deleteAnnouncementById(Integer id);

    DataResult<AnnouncementInfo> editAnnouncement(HttpServletRequest request, AnnouncementInfo AnnouncementInfo);

    DataResult<AnnouncementInfo> addAnnouncement(HttpServletRequest request, AnnouncementInfo AnnouncementInfo);
}
