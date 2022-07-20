package com.xirm.minio.service;

import com.xirm.minio.domain.bean.DeviceDataPointCassandraParam;
import com.xirm.minio.domain.bean.SimpleMachineDevice;
import com.xirm.minio.domain.device.DeviceDataPoint;

import java.util.List;

/**
 *
 * @author huzj
 * @version 1.0
 * @date 2022/7/19 11:10
 */
public interface  DataService {

    /**
     * 下载数据
     * @param bucketName 数据桶
     * @param fileName  文件名称
     * @param saveFileName  保存的文件名称
     */

    public void download(String bucketName,String fileName,String saveFileName);


    /**
     * 查询 所有需要同属的 传感器设备信息
     */
    public List<SimpleMachineDevice> selectMachineDeviceIds();


    /**
     * 查询传感器 在cassandra 中上传你的原始数据
     * @param deviceDataPointCassandraParam
     * @return
     */
    public List<DeviceDataPoint> selectCassandraDeviceMinioDataInfo(DeviceDataPointCassandraParam deviceDataPointCassandraParam);




}
