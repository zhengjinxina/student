package com.example.student.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.example.student.common.Result;
import com.example.student.mapper.UploadMapper;
import com.example.student.service.UploadRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ZGJ
 * @date ：Created in 2023/7/28 1:19 下午
 * @description: TODO
 * @modified By：
 * @version: 8
 */
@Service
@Slf4j
public class UploadRecordServiceImpl implements UploadRecordService {

    @Resource
    private UploadMapper uploadMapper;

    @Value("${s3.file_bucket}")
    private String fileBucket;

    @Value("${s3.url}")
    private String url;


    @Override
    public Result upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            log.error("传入的文件名不能为空");
            return Result.error("500", "传入的文件名不能为空");
        }
        if (!this.validateFileName(fileName)) {
            log.error("文件名应仅包含汉字、字母、数字、下划线和点号");
            return Result.error("500", "文件名应仅包含汉字、字母、数字、下划线和点号");
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        String key = System.currentTimeMillis() + fileName;
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();

        try {
            //设置文件访问权限（可读）
            s3.putObject(new PutObjectRequest(fileBucket,key,file.getInputStream(),objectMetadata).withCannedAcl(CannedAccessControlList.valueOf(CannedAccessControlList.PublicRead.name())));
        } catch (AmazonServiceException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        //文件访问路径
        String address = "https://" + fileBucket + ".s3.us-east-2.amazonaws.com/" + key;
        //将一些关键数据返回到前端做保存用
        Map<String, String> map = new HashMap();
        map.put("address", address);
        map.put("fileName", fileName);
        log.info(address);
        return Result.success(map);
    }


    /**
     * 验证文件名称：仅包含 汉字、字母、数字、下划线和点号
     *
     * @param fileName 文件名称
     * @return 返回true表示符合要求
     */
    private boolean validateFileName(String fileName) {
        String regex = "^[a-zA-Z0-9_\\u4e00-\\u9fa5_\\.]+$";
        return fileName.matches(regex);
    }


}
