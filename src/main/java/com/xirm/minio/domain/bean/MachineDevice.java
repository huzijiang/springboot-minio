package com.xirm.minio.domain.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author huzj
 * @date 2022/7/19 21:01
 * @version 1.0
 */
@Data
public class MachineDevice {
    private Long deviceId;

    private Date createdAt;

    private Date updatedAt;

    private String deviceName;

    /**
    * 设备序列号，格式：{数采序列号}_{采集板编号}_{通道编号}_1
    */
    private String deviceNo;

    private Long deviceTypeId;

    private Long machineId;

    /**
    * 元数据
    */
    private String metadata;

    private Long parentDeviceId;

    /**
    * 在线状态变更原因
    */
    private String reason;

    /**
    * 在线状态
    */
    private String status;

    /**
    * 阈值告警等级:OK(正常), ATTENTION(注意), ERROR(报警)
    */
    private String alarmLevel;

    /**
    * 故障诊断确认等级:OK(正常), FAULT(故障)
    */
    private String faultLevel;

    /**
    * 故障诊断预警等级:OK(正常), ATTENTION(注意), ERROR(报警)
    */
    private String warningLevel;

    /**
    * 在线状态最后更新时间
    */
    private Long statusUpdateAt;


}
