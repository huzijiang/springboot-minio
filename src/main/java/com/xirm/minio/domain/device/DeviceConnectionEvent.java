package com.xirm.minio.domain.device;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/6/14 14:36
 */
@Table("device_connection_event")
@Data
public class DeviceConnectionEvent  implements Serializable {
    @PrimaryKeyColumn(name = "device_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column("device_id")
    private Long deviceId;

    @PrimaryKeyColumn(name = "created_at", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column("created_at")
    private Date createdAt;


    @Column("reason")
    private String reason;

    @Column("status")
    private String status;




}
