package com.xirm.minio.cassandra.impl;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.xirm.minio.cassandra.DeviceDataPointRepository;
import com.xirm.minio.domain.bean.DeviceDataPointCassandraParam;
import com.xirm.minio.domain.device.DeviceDataPoint;

import java.text.SimpleDateFormat;
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

    SimpleDateFormat sdfall= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    /**
     * 根据条件查询历史数据
     *
     * @param param 查询条件
     * @return
     */
    @Override
    public List<DeviceDataPoint> list(DeviceDataPointCassandraParam param) {

        Select select = QueryBuilder.select().all()
                .from("device_data_point")
                .where(QueryBuilder.eq("device_id", param.getDeviceId()))
                .and(QueryBuilder.eq("identifier", param.getIdentifier()))
                .and(QueryBuilder.gt("created_at",param.getBeginTime()))
                .and(QueryBuilder.lt("created_at",param.getEndTime()))
                .allowFiltering();
        System.out.println(select.toString());
        return cassandraTemplate.select(select, DeviceDataPoint.class);

//        Query query = Query
//                .query(where("device_id").is(param.getDeviceId()))
//                .and(where("identifier").is(param.getIdentifier()))
//                .and(where("created_at").gt(param.beginTime))
//                .and(where("created_at").lt(param.endTime));
//
//        query = query.withAllowFiltering();
//
//        query = query.columns(Columns.from("device_id", "identifier", "created_at", "metadata"));
//
//        System.out.println(query);
//
//        return this.cassandraTemplate.select(query, DeviceDataPoint.class);
    }
}
