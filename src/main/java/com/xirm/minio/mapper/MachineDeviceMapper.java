package com.xirm.minio.mapper;

import com.xirm.minio.domain.bean.MachineDevice;

/**
 * @author huzj
 * @date 2022/7/19 21:01
 * @version 1.0
 */
public interface MachineDeviceMapper {
    int deleteByPrimaryKey(Long deviceId);

    int insert(MachineDevice record);

    int insertSelective(MachineDevice record);

    MachineDevice selectByPrimaryKey(Long deviceId);

    int updateByPrimaryKeySelective(MachineDevice record);

    int updateByPrimaryKey(MachineDevice record);
}