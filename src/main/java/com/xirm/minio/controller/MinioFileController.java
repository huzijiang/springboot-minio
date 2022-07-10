package com.xirm.minio.controller;

import com.xirm.minio.util.ResponseData;
import com.xirm.minio.util.ResponseDataUtil;
import io.minio.*;
import io.minio.http.Method;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/3 13:33
 */
@RestController
public class MinioFileController {

    @Resource
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 文件上传
     * @param file
     * @return ResponseData
     */
    @PostMapping("/upload")
    public ResponseData upload(@RequestParam(name = "file") MultipartFile[] file){
        if(file == null || file.length ==0){

            return ResponseDataUtil.buildError("");
        }
        //防御性编程，批量限制少于10个
        if(file.length > 10){
            return ResponseDataUtil.buildError("请上传少于" + file.length + "个文件");
        }
        try {
            List<String> urlList = new ArrayList<>(file.length);
            for (MultipartFile multipartFile : file){
                InputStream in = multipartFile.getInputStream();

                String fileName = multipartFile.getOriginalFilename();
                String[] split = fileName.split("\\.");
                if (split.length > 1) {
                    fileName = split[0] + "_" + System.currentTimeMillis() + "." + split[1];
                } else {
                    fileName = fileName + System.currentTimeMillis();
                }

                minioClient.putObject(PutObjectArgs.builder()
                        .object(fileName)
                        .bucket(bucketName)
                        .stream(in, multipartFile.getSize(),-1)
                        .contentType(multipartFile.getContentType())
                        .build());

                in.close();

                String str = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName)
                        .object(multipartFile.getOriginalFilename())
                        .method(Method.PUT)
                        .build());

                //返回下载的url
                String downloadUrl="http://localhost:8080/download/"+fileName;

                urlList.add(downloadUrl);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("urlList", urlList);
            return ResponseDataUtil.buildSuccess(map.get("urlList"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDataUtil.buildError("上传失败:" + e.getMessage());
        }
    }

    /**
     * 下载文件
     * @param filename
     * @param response
     */
    @GetMapping("/download/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse response){
        InputStream in = null;
        try {
            //获取对象信息
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(filename).build());

            response.setContentType(stat.contentType());
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "utf-8"));
            //文件下载
            in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(filename).build());
            IOUtils.copy(in, response.getOutputStream());

        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();

        }finally {
            if(in != null){
                try {
                    in.close();
                }catch (IOException e){
                    e.getMessage();
                }
            }
        }
    }
    /**
     * 删除文件
     * @param filename
     * @return ResponseData
     */
    @DeleteMapping("/delete/{filename}")
    public ResponseData delete(@PathVariable("fileName") String filename){
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(filename).build());
        }catch (Exception e){
            e.getMessage();
            return ResponseDataUtil.buildError("删除失败");
        }
        return ResponseDataUtil.buildSuccess("删除成功");
    }



}
