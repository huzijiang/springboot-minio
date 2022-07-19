package com.xirm.minio.domain.device;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.LinkedList;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/6/14 14:36
 */
@Table("device_long_array_temp")
@Data
public class DeviceLongArrayTemp {

    @PrimaryKeyColumn(name = "device_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column("device_id")
    private Long deviceId;

    @PrimaryKeyColumn(name = "identifier", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column("identifier")
    private String identifier;

    @Column("data_index")
    private int dataIndex;

    @Column("reported_value_array")
    private LinkedList<Float> reportedValueArray;


}
