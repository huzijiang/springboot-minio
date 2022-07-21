package com.xirm.minio.controller;

import com.google.gson.Gson;
import com.xirm.minio.domain.bean.DeviceDataPointCassandraParam;
import com.xirm.minio.domain.bean.DeviceDataPointMetaData;
import com.xirm.minio.domain.bean.SimpleMachineDevice;
import com.xirm.minio.domain.device.DeviceDataPoint;
import com.xirm.minio.service.DataService;
import com.xirm.minio.service.FileDownloadService;
import com.xirm.minio.util.ResponseData;
import com.xirm.minio.util.ResponseDataUtil;
import io.minio.*;
import io.minio.http.Method;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/3 13:33
 */
@RestController
public class MinioFileController {

    @Autowired
    private Executor personInfoTaskExecutor;

    @Autowired
    FileDownloadService fileDownloadService;

    @Autowired
    DataService dataService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdfroot = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfall= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


    public static String rootpath="/Users/huzj/Desktop/test";
    /**
     * 下载文件， 从本月1 日 算起 开始下载
     * @param filename
     * @param response
     */
    @GetMapping("/download/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse response){
        Gson gson=new Gson();
        sdfall.setTimeZone(TimeZone.getTimeZone("UTC"));

        //从月初开始

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        //查询数据待下载
        List<SimpleMachineDevice> simpleMachineDevices=new ArrayList<>();//dataService.selectMachineDeviceIds();
        SimpleMachineDevice asimpleMachineDevice=new SimpleMachineDevice();
        asimpleMachineDevice.setDeviceId(616669233266429957L);
            asimpleMachineDevice.setProjectName("龙源集团");
            asimpleMachineDevice.setFactoryName("区域名字");
            asimpleMachineDevice.setWorkshopName("风场名字");
            asimpleMachineDevice.setFullDeviceName("A-0-01-10");
        simpleMachineDevices.add(asimpleMachineDevice);


        Calendar now = Calendar.getInstance();;
        while (calendar.before(now)){
            for (SimpleMachineDevice simpleMachineDevice:simpleMachineDevices) {

                //查询 cassandra 原始数据文件 文件名称
                DeviceDataPointCassandraParam deviceDataPointCassandraParam=new DeviceDataPointCassandraParam();
                deviceDataPointCassandraParam.setDeviceId(new BigInteger(simpleMachineDevice.getDeviceId().toString()));
                deviceDataPointCassandraParam.setIdentifier("originalData");
                deviceDataPointCassandraParam.setBeginTime(calendar.getTime());

                //计算截止时间后 并恢复
                calendar.add(Calendar.DAY_OF_YEAR,1);
                deviceDataPointCassandraParam.setEndTime(calendar.getTime());

                List<DeviceDataPoint> deviceDataPointList=dataService.selectCassandraDeviceMinioDataInfo(deviceDataPointCassandraParam);

                System.out.println(deviceDataPointList);

                //解析cassandra 信息
                for (DeviceDataPoint deviceDataPoint:deviceDataPointList) {
                    DeviceDataPointMetaData deviceDataPointMetaData=gson.fromJson(deviceDataPoint.getMetadata().replace("\"\"","\""),DeviceDataPointMetaData.class);
                    System.out.println(deviceDataPointMetaData.toString());
                    //直接到文件
                    File fileFactoryName=new File(rootpath.concat(simpleMachineDevice.getProjectName()).concat(sdfroot.format(calendar.getTime()))
                            .concat(File.separator)
                            .concat(simpleMachineDevice.getFactoryName())
                            .concat(File.separator)
                            .concat(simpleMachineDevice.getWorkshopName())
                            .concat(File.separator)
                            .concat(simpleMachineDevice.getFullDeviceName()));

                    if(fileFactoryName.exists()){
                        continue;
                    }else {
                        fileFactoryName.mkdirs();
                    }

                    fileDownloadService.download(deviceDataPointMetaData.getBucketName(),
                            deviceDataPointMetaData.getObjectName(),
                            fileFactoryName.getPath());
                }

            }
        }

    }

    /**
     * 监控线程池状态
     * @return
     */
    @GetMapping("/asyncExceutorInfo")
    public Map getThreadInfo() {
        Map map =new HashMap();
        Object[] myThread = {personInfoTaskExecutor};
        for (Object thread : myThread) {
            ThreadPoolTaskExecutor threadTask = (ThreadPoolTaskExecutor) thread;
            ThreadPoolExecutor threadPoolExecutor =threadTask.getThreadPoolExecutor();
            System.out.println("提交任务数"+threadPoolExecutor.getTaskCount());
            System.out.println("完成任务数"+threadPoolExecutor.getCompletedTaskCount() );
            System.out.println("当前有"+threadPoolExecutor.getActiveCount()+"个线程正在处理任务");
            System.out.println("还剩"+threadPoolExecutor.getQueue().size()+"个任务");
            map.put("提交任务数-->",threadPoolExecutor.getTaskCount());
            map.put("完成任务数-->",threadPoolExecutor.getCompletedTaskCount());
            map.put("当前有多少线程正在处理任务-->",threadPoolExecutor.getActiveCount());
            map.put("还剩多少个任务未执行-->",threadPoolExecutor.getQueue().size());
            map.put("当前可用队列长度-->",threadPoolExecutor.getQueue().remainingCapacity());
            map.put("当前时间-->", new Date().toString());
        }
        return map;
    }







}
