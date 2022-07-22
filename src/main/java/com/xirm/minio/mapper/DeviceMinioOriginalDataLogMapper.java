package com.xirm.minio.mapper;

import com.xirm.minio.domain.bean.DeviceMinioOriginalDataLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huzj
 * @date 2022/7/22 10:02
 * @version 1.0
 */
@Repository
@Mapper
public interface DeviceMinioOriginalDataLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DeviceMinioOriginalDataLog record);

    int insertSelective(DeviceMinioOriginalDataLog record);

    DeviceMinioOriginalDataLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceMinioOriginalDataLog record);

    int updateByPrimaryKey(DeviceMinioOriginalDataLog record);

    List<DeviceMinioOriginalDataLog> selectDeviceMinioOriginalDataLogInfoByDate(DeviceMinioOriginalDataLog deviceMinioOriginalDataLog);
}
