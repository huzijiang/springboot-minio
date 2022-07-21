package com.xirm.minio.service.impl;

import com.xirm.minio.service.DataService;
import com.xirm.minio.service.FileDownloadService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
     * @param fileName
     * @param saveFileLocation
     */
    @Override
    @Async("downLoadFileTaskExecutor")
    public void download(String bucketName,String fileName,String saveFileLocation) {
        log.info("开始下载文件-bucketName: {} -fileName: {} -saveFileName: {}",bucketName,fileName,saveFileLocation);
        System.out.println("开始下载文件-bucketName: "+bucketName+" -saveFileLocation: "+saveFileLocation+""+" -fileName: "+fileName);
        InputStream in = null;
        try {
            //查询 获取对象信息
//            ObjectStat stat = minioClient.statObject(bucketName,fileName);
            System.out.println(stat.size());
            if(stat.size()>0){
                in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
                //文件下载
//                in = minioClient.getObject(bucketName,fileName);

                //文件存储
                BufferedOutputStream out=null;
                out=new BufferedOutputStream(new FileOutputStream(saveFileLocation+fileName));
                int len=-1;
                byte[] b=new byte[1024];
                while((len=in.read(b))!=-1){
                    out.write(b,0,len);
                }
                in.close();
                out.close();
            }else {
                log.info("bucketName:{} fileName: {} 文件为空。",bucketName,fileName);
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
        log.info("结束下载文件-bucketName: {} -fileName: {} -saveFileName: {}",bucketName,fileName,saveFileLocation);
        System.out.println("结束下载文件-bucketName: "+bucketName+" -saveFileName: "+saveFileLocation+""+" -fileName: "+fileName);
    }

}
