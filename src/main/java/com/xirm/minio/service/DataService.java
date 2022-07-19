package com.xirm.minio.service;

import com.xirm.minio.domain.bean.SimpleMachineDevice;

import java.util.List;

/**
 * 龙源
 * @author huzj
 * @version 1.0
 * @date 2022/7/19 11:10
 */
public interface  DataService {

    /**
     *  自动 多线程下载数据
     * @param minnioFileName
     * @param saveFileName
     */
    public void download(String minnioFileName,String saveFileName);


    /**
     * 查询 所有需要同属的 传感器设备信息
     */
    public List<SimpleMachineDevice> selectMachineDeviceIds();


}
