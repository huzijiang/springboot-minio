package com.xirm.minio.controller;

import com.xirm.minio.domain.bean.DeviceDataPointCassandraParam;
import com.xirm.minio.domain.bean.SimpleMachineDevice;
import com.xirm.minio.domain.device.DeviceDataPoint;
import com.xirm.minio.service.DataService;
import com.xirm.minio.util.ResponseData;
import com.xirm.minio.util.ResponseDataUtil;
import io.minio.*;
import io.minio.http.Method;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/3 13:33
 */
@RestController
public class MinioFileController {


    @Autowired
    DataService dataService;

    /**
     * 下载文件
     * @param filename
     * @param response
     */
    @GetMapping("/download/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse response){
        //ok
        List<SimpleMachineDevice> simpleMachineDevices=dataService.selectMachineDeviceIds();
        System.out.println(simpleMachineDevices.size());
        //
        DeviceDataPointCassandraParam deviceDataPointCassandraParam=new DeviceDataPointCassandraParam();
        deviceDataPointCassandraParam.setDeviceId("724320072792936448");
        deviceDataPointCassandraParam.setIdentifier("WaveformIndex");
        deviceDataPointCassandraParam.setBeginTime("2022-06-15 00:00:00.000");
        deviceDataPointCassandraParam.setEndTime("2022-06-15 23:59:59.000");

        List<DeviceDataPoint> deviceDataPointList=dataService.selectCassandraDeviceMinioDataInfo(deviceDataPointCassandraParam);
        System.out.println(deviceDataPointList);

        //TODO createFile




    }







}
