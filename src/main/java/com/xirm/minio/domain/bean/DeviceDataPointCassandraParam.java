package com.xirm.minio.domain.bean;

import lombok.Data;

/**
 *
 *
 * @author huzj
 * @version 1.0
 * @date 2022/7/20 15:24
 */
@Data
public class DeviceDataPointCassandraParam {

    public String deviceId;

    public String identifier;

    public String beginTime;

    public String endTime;

}
