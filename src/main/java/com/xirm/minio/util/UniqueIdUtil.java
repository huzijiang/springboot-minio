package com.xirm.minio.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/22 10:27
 */
public class UniqueIdUtil {


    public synchronized static long uniqId() {
        Random random=new Random();
        String nanoRandom = System.nanoTime() + "" + random.nextInt(99999);
        int hash = Math.abs(UUID.randomUUID().hashCode());
        int needAdd = 19 - String.valueOf(hash).length() + 1;
        return Long.valueOf(hash + "" + nanoRandom.substring(needAdd));
    }
}
