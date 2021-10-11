package com.cdt.document.service;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.model.DocumentInf;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DocumentService {

    DataResult<DocumentInf> findDocumentById(int id);

    DataResult<List<DocumentInf>> getDocumentList();

    DataResult<PageResult<DocumentInf>> getDocumentListByPage(DatatableInfo<DocumentInf> datatableInfo, String searchType, String searchInput);

    DataResult<DocumentInf> deleteDocumentById(Integer id);

    DataResult<DocumentInf> editDocument(DocumentInf DocumentInf);

    DataResult<DocumentInf> addDocument(HttpServletRequest request, MultipartFile file);
}
