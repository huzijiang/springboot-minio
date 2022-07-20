package com.xirm.minio.domain.device;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/6/14 14:36
 */
@Table("device_data_point")
@Data
public class DeviceDataPoint {
    @PrimaryKeyColumn(name = "device_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column("device_id")
    private BigInteger deviceId;

    @PrimaryKeyColumn(name = "identifier", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column("identifier")
    private String     identifier;

    @PrimaryKeyColumn(name = "created_at", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column("created_at")
    private Date createdAt;

    @Column("action_parameters")
    private String actionParameters;

    @Column("desired_at")
    private Date desiredAt;

    @Column("desired_value")
    private String desiredValue;

    @Column("direction")
    private String direction;

    @Column("level")
    private String level;

    @Column("metadata")
    private String metadata;

    @Column("reported_at")
    private Date reportedAt;

    @Column("reported_value")
    private String reportedValue;

    @Column("reported_value_array")
    private LinkedList<Float> reported_value_array;

    @Column("result")
    private String result;


}
