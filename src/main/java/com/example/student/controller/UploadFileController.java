package com.example.student.controller;

import com.example.student.common.Result;
import com.example.student.mapper.ScoreMapper;
import com.example.student.service.UploadRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author ：ZJX
 * @date ：Created in 2023/7/28 12:02 下午
 * @description: TODO
 * @modified By：
 * @version: 8
 */

@RestController
@RequestMapping("/file")
public class UploadFileController {

    @Resource
    private UploadRecordService uploadService;

    @PostMapping(value = "/upload")
    public Result upload(MultipartFile file) {
        return uploadService.upload(file);
    }

}
