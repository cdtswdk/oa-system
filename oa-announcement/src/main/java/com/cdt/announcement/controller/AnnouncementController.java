package com.cdt.announcement.controller;

import com.cdt.announcement.service.AnnouncementService;
import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.AnnouncementInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/26 15:06
 * @Description:
 */
@RestController
@RequestMapping(value = "/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/announcementInfo/{id}")
    public DataResult<AnnouncementInfo> getAnnouncementInfo(@PathVariable("id") int id) {
        return this.announcementService.findAnnouncementById(id);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public DataResult<List<AnnouncementInfo>> getAnnouncementList() {
        return this.announcementService.getAnnouncementList();
    }

    @RequestMapping(value = "/getListByPage", method = RequestMethod.GET)
    public DataResult<PageResult<AnnouncementInfo>> getAnnouncementListByPage(DatatableInfo<AnnouncementInfo> datatableInfo,
                                                                              String searchType, String searchInput) {
        return this.announcementService.getAnnouncementListByPage(datatableInfo, searchType, searchInput);
    }

    @RequestMapping(value = "/deleteAnnouncement/{id}", method = RequestMethod.POST)
    public DataResult<AnnouncementInfo> deleteAnnouncement(@PathVariable("id") Integer id) {
        return this.announcementService.deleteAnnouncementById(id);
    }

    @RequestMapping(value = "/editAnnouncement", method = RequestMethod.POST)
    public DataResult<AnnouncementInfo> editAnnouncement(HttpServletRequest request, AnnouncementInfo announcementInfo) {
        return this.announcementService.editAnnouncement(request, announcementInfo);
    }

    @RequestMapping(value = "/addAnnouncement", method = RequestMethod.POST)
    public DataResult<AnnouncementInfo> addAnnouncement(HttpServletRequest request, AnnouncementInfo announcementInfo) {
        return this.announcementService.addAnnouncement(request, announcementInfo);
    }
}
