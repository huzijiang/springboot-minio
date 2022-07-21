package com.xirm.minio.service.impl;

import com.xirm.minio.cassandra.DeviceDataPointRepository;
import com.xirm.minio.domain.bean.DeviceDataPointCassandraParam;
import com.xirm.minio.domain.bean.SimpleMachineDevice;
import com.xirm.minio.domain.device.DeviceDataPoint;
import com.xirm.minio.mapper.MachineDeviceMapper;
import com.xirm.minio.service.DataService;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.io.*;
import java.sql.SQLOutput;
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
     * minio  文件下载
     *
     * @param bucketName
     * @param fileName
     * @param saveFileName
     */
    @Override
    @Async("downLoadFileTaskExecutor")
    public void download(String bucketName,String fileName,String saveFileName) {
        log.info("开始下载文件-bucketName: {} -fileName: {} -saveFileName: {}",bucketName,fileName,saveFileName);
        System.out.println("开始下载文件-bucketName: "+bucketName+" -fileName: "+fileName+" -saveFileName: "+saveFileName+"");
        InputStream in = null;
        try {
            //查询 获取对象信息
            ObjectStat stat = minioClient.statObject(bucketName,fileName);
            System.out.println(stat.length());
            if(stat.length()>0){
                //文件下载
                in = minioClient.getObject(bucketName,fileName);

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
        log.info("结束下载文件-bucketName: {} -fileName: {} -saveFileName: {}",bucketName,fileName,saveFileName);
        System.out.println("结束下载文件-bucketName: "+bucketName+" -fileName: "+fileName+" -saveFileName: "+saveFileName+"");
    }

    /**
     *
     * mysql查询 所有需要同属的 传感器设备信息
     *
     *
     */
    @Override
    public List<SimpleMachineDevice> selectMachineDeviceIds() {
        log.info("开始查询 mysql ");
        System.out.println("开始查询 mysql");
        List<SimpleMachineDevice> simpleMachineDeviceList=machineDeviceMapper.selectMachineDeviceIds();
        log.info("结束查询 mysql: 共有数据 {} 条",simpleMachineDeviceList.size());
        System.out.println("结束查询 mysql: 共有数据 +"+simpleMachineDeviceList.size()+"+ 条");
        return simpleMachineDeviceList;
    }
    /**
     *
     * cassandra 查询 minio 文件目录
     *
     *
     */
    @Override
    public List<DeviceDataPoint> selectCassandraDeviceMinioDataInfo(DeviceDataPointCassandraParam deviceDataPointCassandraParam) {
        long beginTime= System.currentTimeMillis();
        log.info("开始查询 cassandra:  {} ",deviceDataPointCassandraParam.toString());
        List<DeviceDataPoint> deviceDataPointList=deviceDataPointRepositoryImpl.list(deviceDataPointCassandraParam);
        log.info("结束查询 cassandra:  {}  有记录 {} 条  耗时 ",deviceDataPointCassandraParam.toString(),deviceDataPointList.size(),System.currentTimeMillis()-beginTime);

        return deviceDataPointList;
    }

}
