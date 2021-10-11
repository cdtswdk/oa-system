package com.cdt.announcement.service.impl;

import com.cdt.announcement.mapper.AnnouncementMapper;
import com.cdt.announcement.mapper.UserMapper;
import com.cdt.announcement.service.AnnouncementService;
import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.AnnouncementInfo;
import com.cdt.model.UserInf;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/29 16:30
 * @Description:
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public DataResult<AnnouncementInfo> findAnnouncementById(int id) {
        try {
            AnnouncementInfo AnnouncementInfo = this.announcementMapper.selectByPrimaryKey(id);
            return DataResult.success(AnnouncementInfo, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.notfound("查询失败");
    }

    @Override
    public DataResult<List<AnnouncementInfo>> getAnnouncementList() {
        try {
            List<AnnouncementInfo> AnnouncementInfos = this.announcementMapper.selectAll();
            return DataResult.success(AnnouncementInfos, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<PageResult<AnnouncementInfo>> getAnnouncementListByPage(DatatableInfo<AnnouncementInfo> datatableInfo, String searchType, String searchInput) {
        try {
            Example example = new Example(AnnouncementInfo.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotEmpty(searchInput)) {
                if (StringUtils.isNotEmpty(searchType)) {
                    if ("1".equals(searchType)) {
                        criteria.andLike("title", "%" + searchInput + "%");
                    } else if ("2".equals(searchType)) {
                        criteria.andLike("content", "%" + searchInput + "%");
                    }
                }
            }
            example.setOrderByClause(" createtime desc ");
            PageHelper.startPage(datatableInfo.getPage(), datatableInfo.getPageSize());
            List<AnnouncementInfo> AnnouncementInfos = this.announcementMapper.selectByExample(example);
            PageResult<AnnouncementInfo> pageResult = new PageResult<>();
            pageResult.setItems(AnnouncementInfos);
            List<AnnouncementInfo> AnnouncementInfoList = this.announcementMapper.selectByExample(example);
            pageResult.setTotal((long) AnnouncementInfoList.size());
            pageResult.setTotalPage((int) Math.ceil((double) AnnouncementInfoList.size() / datatableInfo.getPageSize()));
            return DataResult.success(pageResult, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<AnnouncementInfo> deleteAnnouncementById(Integer id) {
        try {
            int count = this.announcementMapper.deleteByPrimaryKey(id);
            if (count > 0) {
                return DataResult.success(null, "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("删除失败");
    }

    @Override
    public DataResult<AnnouncementInfo> editAnnouncement(HttpServletRequest request, AnnouncementInfo announcementInfo) {
        try {
            String header = request.getHeader("X-Token");
            String loginname = header.split(",")[1];
            Example example = new Example(UserInf.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("loginname", loginname);
            List<UserInf> userInfList = this.userMapper.selectByExample(example);
            UserInf userInf = null;
            if (CollectionUtils.isNotEmpty(userInfList)) {
                userInf = userInfList.get(0);
                announcementInfo.setUid(userInf.getId());
            } else {
                announcementInfo.setUid(-1);
            }
            int count = this.announcementMapper.updateByPrimaryKeySelective(announcementInfo);
            if (count > 0) {
                return DataResult.success(null, "修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("修改失败");
    }

    @Override
    public DataResult<AnnouncementInfo> addAnnouncement(HttpServletRequest request, AnnouncementInfo announcementInfo) {
        try {
            String header = request.getHeader("X-Token");
            String loginname = header.split(",")[1];
            Example example = new Example(UserInf.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("loginname", loginname);
            List<UserInf> userInfList = this.userMapper.selectByExample(example);
            UserInf userInf = null;
            if (CollectionUtils.isNotEmpty(userInfList)) {
                userInf = userInfList.get(0);
                announcementInfo.setUid(userInf.getId());
            } else {
                announcementInfo.setUid(-1);
            }
            announcementInfo.setCreatetime(new Date());
            int count = this.announcementMapper.insert(announcementInfo);
            if (count > 0) {
                return DataResult.success(null, "增加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("增加失败");
    }
}
