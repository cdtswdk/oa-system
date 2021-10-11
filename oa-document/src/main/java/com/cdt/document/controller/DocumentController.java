package com.cdt.document.controller;

import com.cdt.common.pojo.DataResult;
import com.cdt.common.pojo.DatatableInfo;
import com.cdt.common.pojo.PageResult;
import com.cdt.document.service.DocumentService;
import com.cdt.model.DocumentInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/26 15:06
 * @Description:
 */
@RestController
@RequestMapping(value = "/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/DocumentInfo/{id}")
    public DataResult<DocumentInf> getDocumentInfo(@PathVariable("id") int id) {
        return this.documentService.findDocumentById(id);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public DataResult<List<DocumentInf>> getDocumentList() {
        return this.documentService.getDocumentList();
    }

    @RequestMapping(value = "/getListByPage", method = RequestMethod.GET)
    public DataResult<PageResult<DocumentInf>> getDocumentListByPage(DatatableInfo<DocumentInf> datatableInfo,
                                                             String searchType, String searchInput) {
        return this.documentService.getDocumentListByPage(datatableInfo, searchType, searchInput);
    }

    @RequestMapping(value = "/deleteDocument/{id}", method = RequestMethod.POST)
    public DataResult<DocumentInf> deleteDocument(@PathVariable("id") Integer id) {
        return this.documentService.deleteDocumentById(id);
    }

    @RequestMapping(value = "/editDocument", method = RequestMethod.POST)
    public DataResult<DocumentInf> editDocument(DocumentInf DocumentInf) {
        return this.documentService.editDocument(DocumentInf);
    }

    @RequestMapping(value = "/addDocument", method = RequestMethod.POST)
    public DataResult<DocumentInf> addDocument(HttpServletRequest request, @RequestParam(name = "file") MultipartFile file) {
        return this.documentService.addDocument(request,file);
    }
}
