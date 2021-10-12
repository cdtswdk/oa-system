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
import java.io.*;
import java.util.List;
import java.util.UUID;

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
                        criteria.andLike("filename", "%" + searchInput + "%");
                    } else if ("2".equals(searchType)) {
                        criteria.andLike("remark", "%" + searchInput + "%");
                    } else if ("3".equals(searchType)) {
                        criteria.andLike("uploader", "%" + searchInput + "%");
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
            DocumentInf documentInf = this.documentMapper.selectByPrimaryKey(id);
            String filename = documentInf.getRemark();
            //1、删除数据
            int count = this.documentMapper.deleteByPrimaryKey(id);
            //2、删除文件
            String path = "C:\\Users\\14660\\Pictures\\Camera Roll";
            File file = new File(path + File.separator + filename);
            boolean del = file.delete();
            if (count > 0 && del) {
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
                String newFileName = upload(file);
                if (StringUtils.isBlank(newFileName)) {
                    return DataResult.serverError("文件名不能为空");
                }
                String originalFilename = file.getOriginalFilename();
                DocumentInf documentInf = new DocumentInf();
                if (StringUtils.isNotEmpty(originalFilename)) {
                    documentInf.setFilename(originalFilename);
                }
                documentInf.setRemark(newFileName);
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

    public String upload(MultipartFile file) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String newFileName = "";
        try {
            // 1、获取文件名
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isBlank(originalFilename)) {
                return null;
            }
            // 2、定义一个文件路径用于存放文件
            String path = "C:\\Users\\14660\\Pictures\\Camera Roll";
            // 3、获取来自网络端上传的文件，以流的形式来获取 ：输入流
            bis = new BufferedInputStream(file.getInputStream());
            // 4、定义字节缓冲数据，用于缓冲输入流的数据
            byte[] buff = new byte[1024];
            // 定义一个int变量，用于存放单次读取数据的字节个数
            int len = 0;
            // 5、创建文件对象
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            newFileName = uuid + "-" + originalFilename;
            File writeFile = new File(path + File.separator + newFileName);
            // 6、创建输出流，并通过输出流将文件写到硬盘
            bos = new BufferedOutputStream(new FileOutputStream(writeFile));
            // 7、进行循环的读写操作
            while ((len = bis.read(buff)) != -1) {//读取--读取到缓冲字节数组中
                //写出
                bos.write(buff, 0, len);
                //刷新流
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 8、关闭流
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newFileName;
    }
}
