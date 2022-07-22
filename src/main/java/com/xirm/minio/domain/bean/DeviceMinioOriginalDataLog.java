package com.xirm.minio.domain.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author huzj
 * @date 2022/7/22 10:02
 * @version 1.0
 */
@Data
public class DeviceMinioOriginalDataLog {
    /**
    * 主键
    */
    private Long id;

    /**
    * 传感器编号
    */
    private Long deviceId;

    /**
    * 上传日期
    */
    private Date createdAt;

    /**
    * 报告时间
    */
    private Date reportedAt;

    /**
    * 数据类别
    */
    private String identifier;

    /**
    * 文件名称
    */
    private String fileName;


}
