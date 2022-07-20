package com.xirm.minio.cassandra.impl;

import com.xirm.minio.cassandra.DeviceDataPointRepository;
import com.xirm.minio.domain.bean.DeviceDataPointCassandraParam;
import com.xirm.minio.domain.device.DeviceDataPoint;

import java.util.List;
import org.springframework.data.cassandra.core.query.Columns;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;

import static org.springframework.data.cassandra.core.query.Criteria.where;


/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/20 15:16
 */
@Repository
public class DeviceDataPointRepositoryImpl extends BaseRepositoryImpl<DeviceDataPoint> implements DeviceDataPointRepository {


    /**
     * 根据条件查询历史数据
     *
     * @param param 查询条件
     * @return
     */
    @Override
    public List<DeviceDataPoint> list(DeviceDataPointCassandraParam param) {
        Query query = Query
                .query(where("device_id").is(param.getDeviceId()))
                .and(where("identifier").is(param.getIdentifier()))
                .and(where("created_at").gt(param.beginTime))
                .and(where("created_at").lt(param.endTime));

        query = query.withAllowFiltering();

        query = query.columns(Columns.from("device_id", "identifier", "created_at", "metadata"));

        System.out.println(query);

        return this.cassandraTemplate.select(query, DeviceDataPoint.class);
    }
}
