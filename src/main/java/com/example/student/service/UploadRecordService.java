package com.example.student.service;

import com.example.student.common.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：ZGJ
 * @date ：Created in 2023/7/28 12:14 下午
 * @description: TODO
 * @modified By：
 * @version: 8
 */
public interface UploadRecordService {
    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    Result upload(MultipartFile file);
}
