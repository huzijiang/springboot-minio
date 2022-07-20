package com.xirm.minio.service.impl;

import com.xirm.minio.domain.bean.SimpleMachineDevice;
import com.xirm.minio.mapper.MachineDeviceMapper;
import com.xirm.minio.service.DataService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/19 11:11
 */
@Slf4j
@Service
public class DataServiceImpl  implements DataService {

    @Resource
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    private MachineDeviceMapper machineDeviceMapper;



    @Override
    public void download(String minnioFileName,String saveFileName) {
        InputStream in = null;
        try {
            //查询 mysql 获取 传感器的数据信息 并拼接
            List<SimpleMachineDevice> simpleMachineDeviceList=null;

            try{
                simpleMachineDeviceList=machineDeviceMapper.selectMachineDeviceIds();

            }catch (Exception e){
                log.error(e.toString());
                e.printStackTrace();
                log.error("查询mysql 数据异常.");
            }
            //查询 cassandra， 解析获取文件名称  原始数据 identifier: originalData




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

    /**
     *
     * 查询 所有需要同属的 传感器设备信息
     *
     *
     */
    @Override
    public List<SimpleMachineDevice> selectMachineDeviceIds() {

        List<SimpleMachineDevice> longs=machineDeviceMapper.selectMachineDeviceIds();

        return longs;
    }


}
