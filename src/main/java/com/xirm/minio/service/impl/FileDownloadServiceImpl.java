package com.xirm.minio.service.impl;

import com.xirm.minio.service.DataService;
import com.xirm.minio.service.FileDownloadService;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/21 20:18
 */
@Slf4j
@Service
public class FileDownloadServiceImpl implements FileDownloadService {

    @Resource
    private MinioClient minioClient;

    /**
     *
     * minio  文件下载
     *
     * @param bucketName
     * @param minioFileName
     * @param saveFileName
     */
    @Override
    @Async("downLoadFileTaskExecutor")
    public void download(String bucketName,String minioFileName,String saveFileName) {
        log.info("开始下载文件-bucketName: {} -minioFileName: {} -saveFileName: {}",bucketName,minioFileName,saveFileName);
        System.out.println("开始下载文件-bucketName: "+bucketName+" -saveFileName: "+saveFileName+""+" -fileName: "+saveFileName);
        InputStream in = null;
        try {
            //查询 获取对象信息
            ObjectStat stat = minioClient.statObject(bucketName,minioFileName);

            if(stat.length()>0){
                //文件下载
                in = minioClient.getObject(bucketName,minioFileName);
                //文件存储
                BufferedOutputStream out=null;
                out=new BufferedOutputStream(new FileOutputStream(saveFileName));
                int len=-1;
                byte[] b=new byte[1024];
                while((len=in.read(b))!=-1){
                    out.write(b,0,len);
                }
                in.close();
                out.close();
            }else {
                log.info("bucketName:{} fileName: {} 文件为空。",bucketName,minioFileName);
            }
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
        log.info("结束下载文件-bucketName: {} -fileName: {} -saveFileName: {}",bucketName,minioFileName,saveFileName);
        System.out.println("结束下载文件-bucketName: "+bucketName+" -minioFileName: "+minioFileName+""+" -saveFileName: "+saveFileName);
    }

}
