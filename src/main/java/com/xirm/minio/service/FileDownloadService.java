package com.xirm.minio.service;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/21 20:17
 */
public interface FileDownloadService {

    /**
     * 下载数据
     * @param bucketName 数据桶
     * @param fileName  文件名称
     * @param saveFileName  保存的文件名称
     */

    public void download(String bucketName,String fileName,String saveFileName);
}
