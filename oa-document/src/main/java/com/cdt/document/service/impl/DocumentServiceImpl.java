package com.cdt.document.service.impl;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.document.mapper.DocumentMapper;
import com.cdt.document.service.DocumentService;
import com.cdt.model.DocumentInf;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/29 16:30
 * @Description:
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public DataResult<DocumentInf> findDocumentById(int id) {
        try {
            DocumentInf DocumentInf = this.documentMapper.selectByPrimaryKey(id);
            return DataResult.success(DocumentInf, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.notfound("查询失败");
    }

    @Override
    public DataResult<List<DocumentInf>> getDocumentList() {
        try {
            List<DocumentInf> DocumentInfs = this.documentMapper.selectAll();
            return DataResult.success(DocumentInfs, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<PageResult<DocumentInf>> getDocumentListByPage(DatatableInfo<DocumentInf> datatableInfo, String searchType, String searchInput) {
        try {
            Example example = new Example(DocumentInf.class);
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
            List<DocumentInf> DocumentInfs = this.documentMapper.selectByExample(example);
            PageResult<DocumentInf> pageResult = new PageResult<>();
            pageResult.setItems(DocumentInfs);
            List<DocumentInf> DocumentInfList = this.documentMapper.selectByExample(example);
            pageResult.setTotal((long) DocumentInfList.size());
            pageResult.setTotalPage((int) Math.ceil((double) DocumentInfList.size() / datatableInfo.getPageSize()));
            return DataResult.success(pageResult, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("查询失败");
    }

    @Override
    public DataResult<DocumentInf> deleteDocumentById(Integer id) {
        try {
            int count = this.documentMapper.deleteByPrimaryKey(id);
            if (count > 0) {
                return DataResult.success(null, "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("删除失败");
    }

    @Override
    public DataResult<DocumentInf> editDocument(DocumentInf DocumentInf) {
        try {
            int count = this.documentMapper.updateByPrimaryKeySelective(DocumentInf);
            if (count > 0) {
                return DataResult.success(null, "修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("修改失败");
    }

    @Override
    public DataResult<DocumentInf> addDocument(HttpServletRequest request, MultipartFile file) {

        try {
            if (file != null) {
                String originalFilename = file.getOriginalFilename();
                DocumentInf documentInf = new DocumentInf();
                if (StringUtils.isNotEmpty(originalFilename)) {
                    String[] split = originalFilename.split("\\.");
                    documentInf.setFilename(split[0]);
                }
                documentInf.setRemark(originalFilename);
                String uploader = request.getHeader("X-Token");
                documentInf.setUploader(uploader.substring(uploader.indexOf(",") + 1));
                int count = this.documentMapper.insertSelective(documentInf);
                if (count > 0) {
                    return DataResult.success(null, "增加成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataResult.serverError("增加失败");
    }
}
