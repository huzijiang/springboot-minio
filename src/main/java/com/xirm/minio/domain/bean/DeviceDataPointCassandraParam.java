package com.xirm.minio.domain.bean;

import lombok.Data;

import java.math.BigInteger;

/**
 *
 *
 * @author huzj
 * @version 1.0
 * @date 2022/7/20 15:24
 */
@Data
public class DeviceDataPointCassandraParam {

    public BigInteger deviceId;

    public String identifier;

    public String beginTime;

    public String endTime;

}
