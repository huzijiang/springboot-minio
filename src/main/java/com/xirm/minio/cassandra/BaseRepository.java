package com.xirm.minio.cassandra;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/20 15:02
 */
import org.springframework.data.cassandra.core.query.Query;
import java.io.Serializable;
import java.util.List;

/**
 * @description：基础 仓储层
 * @author：twislyn
 * @date：Created in 2020/05/21
 * @modified By：
 * @version: 1.0
 */
public interface BaseRepository<T> {

    /**
     * 按主键查询
     * @param id
     * @return
     */
    T getById(Serializable id);

    /**
     * 按条件查询对象
     * @param query
     * @return
     */
    T getObj(Query query);

    /**
     * 按条件查询列表
     * @param query
     * @return
     */
    List<T> listObjs(Query query);

//    /**
//     * 分页查询
//     * @param pagination
//     * @param query
//     * @return
//     */
//    Slice<T> pageObjs(Pagination pagination, Query query);
}

