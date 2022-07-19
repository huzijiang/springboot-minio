package com.xirm.minio.cassandra;

import com.xirm.minio.domain.device.DeviceDataPoint;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/6/14 16:21
 */
public interface CassandraRepositoryDeviceDataPoint extends CrudRepository<DeviceDataPoint, Serializable> {




}
