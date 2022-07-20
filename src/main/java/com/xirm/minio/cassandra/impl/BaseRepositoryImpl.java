package com.xirm.minio.cassandra.impl;



import com.xirm.minio.cassandra.BaseRepository;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Query;
import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/20 15:02
 */
public class BaseRepositoryImpl<T> implements BaseRepository<T> {
    @Resource
    CassandraTemplate cassandraTemplate;

    protected Class<T> entityClass = currentModelClass();

    /**
     * 获取泛型类型
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    protected Class<T> currentModelClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T getById(Serializable id) {
        return cassandraTemplate.selectOneById(id, entityClass);
    }

    @Override
    public T getObj(Query query) {
        return cassandraTemplate.selectOne(query, entityClass);
    }

    @Override
    public List<T> listObjs(Query query) {
        return cassandraTemplate.select(query, entityClass);
    }

}


