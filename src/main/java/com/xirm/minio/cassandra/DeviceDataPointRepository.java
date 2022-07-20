package com.xirm.minio.cassandra;

import com.xirm.minio.domain.bean.DeviceDataPointCassandraParam;
import com.xirm.minio.domain.device.DeviceDataPoint;

import java.util.List;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/20 15:09
 */
public interface DeviceDataPointRepository  extends BaseRepository<DeviceDataPoint> {

    /**
     * 根据条件查询历史数据
     *
     * @param param 查询条件
     * @return
     *
     */
    List<DeviceDataPoint> list(DeviceDataPointCassandraParam param);

}
