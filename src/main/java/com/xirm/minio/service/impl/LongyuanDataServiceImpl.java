package com.xirm.minio.service.impl;

import com.xirm.minio.service.LongyuanDataService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/19 11:11
 */
public class LongyuanDataServiceImpl  implements LongyuanDataService {

    @Resource
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Override
    public void download(String minnioFileName,String saveFileName) {
        InputStream in = null;
        try {
            //查询 mysql 获取 传感器的数据信息 并拼接


            //查询 cassandra， 解析获取文件名称


            //查询 获取对象信息
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(minnioFileName).build());

            //文件下载
            in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(minnioFileName).build());


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


}
