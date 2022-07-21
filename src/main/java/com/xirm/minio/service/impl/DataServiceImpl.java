package com.xirm.minio.service.impl;

import com.xirm.minio.cassandra.DeviceDataPointRepository;
import com.xirm.minio.domain.bean.DeviceDataPointCassandraParam;
import com.xirm.minio.domain.bean.SimpleMachineDevice;
import com.xirm.minio.domain.device.DeviceDataPoint;
import com.xirm.minio.mapper.MachineDeviceMapper;
import com.xirm.minio.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/19 11:11
 */
@Slf4j
@Service
public class DataServiceImpl  implements DataService {

    @Autowired
    private MachineDeviceMapper machineDeviceMapper;

    @Autowired
    private DeviceDataPointRepository deviceDataPointRepositoryImpl;



    /**
     * mysql查询 所有需要同属的 传感器设备信息
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
