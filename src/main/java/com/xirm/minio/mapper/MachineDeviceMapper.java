package com.xirm.minio.mapper;

import com.xirm.minio.domain.bean.MachineDevice;
import com.xirm.minio.domain.bean.SimpleMachineDevice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huzj
 * @date 2022/7/19 21:01
 * @version 1.0
 */
@Repository
@Mapper
public interface MachineDeviceMapper {

    int deleteByPrimaryKey(Long deviceId);

    int insert(MachineDevice record);

    int insertSelective(MachineDevice record);

    MachineDevice selectByPrimaryKey(Long deviceId);

    int updateByPrimaryKeySelective(MachineDevice record);

    int updateByPrimaryKey(MachineDevice record);

    /**
     *  传感器设备列表
     * @return
     */
    List<SimpleMachineDevice> selectMachineDeviceIds();
}
