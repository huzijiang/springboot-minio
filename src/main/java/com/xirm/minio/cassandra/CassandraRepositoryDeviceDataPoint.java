package com.xirm.minio.cassandra;

import com.xirm.minio.domain.device.DeviceDataPoint;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/6/14 16:21
 */
public interface CassandraRepositoryDeviceDataPoint extends CrudRepository<DeviceDataPoint, Serializable> {


    /**
     *
     * @param device_id   设备编号
     * @param identifier  传感器标识
     * @param data_date   数据时间
     * @return
     */
    public Optional<DeviceDataPoint> findByVehicleIdAndDate(String device_id, String identifier, String data_date);


}
