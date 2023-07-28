package com.example.student.common;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
    private static org.apache.logging.log4j.Logger logger;
    private static final String FILE_TYPE = "fileType";

    /**
     * keyID
     */
    private static String accessKeyID = "1";

    /**
     * key
     */
    private static String secretKey = "1/1/1";


    /**
     * url
     */
    private static String url = "h1e";


    /*** 初始化生成AmazonS3 客户端配置** @return*/
    @Bean
    public AmazonS3 amazonS3() {
        System.out.println("start create s3Client------------------------");
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(url, Region.getRegion(Regions.US_EAST_1).getName());
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyID, secretKey);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTPS);
        AmazonS3 S3client = AmazonS3Client.builder().withEndpointConfiguration(endpointConfig).withClientConfiguration(clientConfig).withCredentials(awsCredentialsProvider).disableChunkedEncoding().withPathStyleAccessEnabled(true).withForceGlobalBucketAccessEnabled(true).build();
//        log.info("create s3Client success");
        System.out.println("create s3Client success----------------------");
        return S3client;
    }


}
