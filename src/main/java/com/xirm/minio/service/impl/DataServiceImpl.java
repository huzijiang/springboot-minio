package com.xirm.minio.service.impl;

import com.xirm.minio.cassandra.DeviceDataPointRepository;
import com.xirm.minio.domain.bean.DeviceDataPointCassandraParam;
import com.xirm.minio.domain.bean.SimpleMachineDevice;
import com.xirm.minio.domain.device.DeviceDataPoint;
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

    @Autowired
    private MachineDeviceMapper machineDeviceMapper;

    @Autowired
    private DeviceDataPointRepository deviceDataPointRepositoryImpl;

    /**
     *
     * @param bucketName
     * @param fileName
     * @param saveFileName
     */
    @Override
    public void download(String bucketName,String fileName,String saveFileName) {
        InputStream in = null;
        try {
            //查询 获取对象信息
            StatObjectResponse stat = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
            System.out.println(stat.size());
            if(stat.size()>0){
                //文件下载
                in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());

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
    /**
     *
     * 查询 所有需要同属的 传感器设备信息
     *
     *
     */
    @Override
    public List<DeviceDataPoint> selectCassandraDeviceMinioDataInfo(DeviceDataPointCassandraParam deviceDataPointCassandraParam) {

        return deviceDataPointRepositoryImpl.list(deviceDataPointCassandraParam);
    }

}
