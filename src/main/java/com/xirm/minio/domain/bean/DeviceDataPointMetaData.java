package com.xirm.minio.domain.bean;

import lombok.Data;

/**
 *
 *  文件存储元数据
 * @author huzj
 * @version 1.0
 * @date 2022/7/20 16:11
 */
@Data
public class DeviceDataPointMetaData {

    public String bucketName;

    public double samplingFrequency;

    public String objectName;
}
