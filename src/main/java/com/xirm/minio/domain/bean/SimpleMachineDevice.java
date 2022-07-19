package com.xirm.minio.domain.bean;

import lombok.Data;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/19 21:04
 */
@Data
public class SimpleMachineDevice {

    private Long deviceId;

    /**
     * 完整的设备名称
     */
    private String fullDeviceName;


}
