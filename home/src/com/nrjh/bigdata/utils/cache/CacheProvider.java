package com.nrjh.bigdata.utils.cache;

import java.io.Serializable;

/**
 * cache provider
 * @author shanleiyang
 */
public interface CacheProvider<E extends Serializable> {
    /**
     * 放入cache中
     * @param key
     * @param cacheObject
     */
    void put(String key, E cacheObject);


    /**
     * 获取放在cache中的内容
     * @param key
     * @return
     */
    E get(String key);

    /**
     * 清除cache中对应的值
     * @param key
     */
    void remove(String key);

    /**
     * 清除所有的cache
     */
    void clear();
    /**
     * cache大小
     * @return
     */
    int size();
}
