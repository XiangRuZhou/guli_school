package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.FileService;
import com.atguigu.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String fileUpload(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        //创建实例
        OSS ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);
        String fileUrl =null;
        try {
            //获取上传文件流
            InputStream inputStream = file.getInputStream();
            //构建路径
            String filePath = "edu/user/faceImg/";
            //文件名：uuid.扩展名
            //真实文件名
            String original = file.getOriginalFilename();
            //生成上传之后的文件名
            String fileName = UUID.randomUUID().toString();
            //获取文件类型
            String fileType = original.substring(original.lastIndexOf("."));
            //上传之后的文件名及类型
            String newName = filePath+fileName + fileType;
            //文件上传至阿里云
            ossClient.putObject(bucketName, newName, inputStream);
            //https://edu-l-0519.oss-cn-chengdu.aliyuncs.com/1.jpg
            fileUrl = "https://"+bucketName+"."+endPoint+"/"+newName;
            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("文件上传失败！",e);
        }finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
