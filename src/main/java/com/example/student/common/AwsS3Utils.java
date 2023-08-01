package com.example.student.common;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;

import com.amazonaws.services.s3.model.PutObjectRequest;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * @author ：ZJX
 * @date ：Created in 2023/7/28 11:05 上午
 * @description: TODO
 * @modified By：
 * @version: 8
 */

@Slf4j
@Configuration
public class AwsS3Utils {
    /**
     * keyID
     */
    @Value("${s3.access_key_id}")
    private String accessKeyID;

    /**
     * key
     */
    @Value("${s3.secret_key}")
    private String secretKey;

    /**
     * url
     */
    @Value("${s3.url}")
    private String url;

    /**
     * 存储桶名称
     */
    private static String fileBucket;

    @Value("${s3.file_bucket}")
    public void setFileBucket(String fileBucket) {
        this.fileBucket = fileBucket;
    }

    /*** 初始化生成AmazonS3 客户端配置** @return*/
    @Bean
    public AmazonS3 amazonS3() {
        log.info("start create s3Client------------------------");
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(url, Region.getRegion(Regions.US_EAST_2).getName());
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyID, secretKey);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTPS);
        AmazonS3 S3client = AmazonS3Client.builder().withEndpointConfiguration(endpointConfig).withClientConfiguration(clientConfig).withCredentials(awsCredentialsProvider).disableChunkedEncoding().withPathStyleAccessEnabled(true).withForceGlobalBucketAccessEnabled(true).build();
        log.info("create s3Client success!");
        return S3client;
    }


    /**
     * 文件上传方法
     *
     * @param file 文件
     * @return 返回map含有地址、文件名称参数
     */
    public static Result uploadFile(File file) {
        String fileName = file.getName();
        if (fileName == null) {
            log.error("传入的文件名不能为空");
            return Result.error("500", "传入的文件名不能为空");
        }
        if (!validateFileName(fileName)) {
            log.error("文件名应仅包含汉字、字母、数字、下划线、横线和点号");
            return Result.error("500", "文件名应仅包含汉字、字母、数字、下划线、横线和点号");
        }

        String key = System.currentTimeMillis() + fileName;
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();

        try {
            //设置文件访问权限（可读）
            s3.putObject(new PutObjectRequest(fileBucket, key, file).withCannedAcl(CannedAccessControlList.valueOf(CannedAccessControlList.PublicRead.name())));
        } catch (AmazonServiceException e) {
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
    private static boolean validateFileName(String fileName) {
        String regex = "^[a-zA-Z0-9_\\u4e00-\\u9fa5_\\.-]+$";
        return fileName.matches(regex);
    }


}
