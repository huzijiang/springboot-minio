package com.xirm.minio.domain.bean;

import lombok.Data;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/19 21:04
 */
@Data
public class SimpleMachineDevice {

    /**
     * 集团
     */
    private String projectName;

    /**
     * 区域名称
     */
    private String factoryName;

    /**
     * 风场名称
     */
    private String workshopName;

    /**
     * 风机编号
     */
    private Long deviceId;


    /**
     * 风机名称-完整的设备名称，导出文件的文件名
     */
    private String fullDeviceName;


}
