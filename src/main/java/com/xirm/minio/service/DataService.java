package com.xirm.minio.service;

import com.xirm.minio.domain.bean.DeviceDataPointCassandraParam;
import com.xirm.minio.domain.bean.DeviceMinioOriginalDataLog;
import com.xirm.minio.domain.bean.SimpleMachineDevice;
import com.xirm.minio.domain.device.DeviceDataPoint;

import java.util.Date;
import java.util.List;

/**
 *
 * @author huzj
 * @version 1.0
 * @date 2022/7/19 11:10
 */
public interface  DataService {

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

    /**
     *
     * 一次性处理
     * 查询传感器 在cassandra 中上传你的原始数据信息 转储到 mysql中
     * @param deviceMinioOriginalDataLog
     * @return
     */
    public void saveCassandraDeviceMinioDataInfoToMysql(DeviceMinioOriginalDataLog deviceMinioOriginalDataLog);

    /**
     * 从数据库中 下载 minio 文件同步日志
     * @param deviceMinioOriginalDataLog
     * @return
     */
    List<DeviceMinioOriginalDataLog> selectDeviceMinioOriginalDataLogInfoByDate(DeviceMinioOriginalDataLog deviceMinioOriginalDataLog);
}
