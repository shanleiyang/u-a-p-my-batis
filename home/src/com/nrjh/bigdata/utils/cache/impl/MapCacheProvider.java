package com.nrjh.bigdata.utils.cache.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.nrjh.bigdata.utils.cache.CacheProvider;
/**
 * MAP封装缓存
 */
public class MapCacheProvider implements CacheProvider {
	 public MapCacheProvider() {

	    }
	 private static MapCacheProvider instance = new MapCacheProvider();
	 private static Map<String, Serializable> cacheContainer = new HashMap<String, Serializable>();

	    public static MapCacheProvider getInstance() {
	        return instance;
	    }

	    @Override
	    public void put(String key, Serializable cacheObject) {
	        cacheContainer.put(key, cacheObject);
	    }

	    @Override
	    public Serializable get(String key) {
	        return cacheContainer.get(key);
	    }

	    @Override
	    public void remove(String key) {
	        cacheContainer.remove(key);
	    }

	    @Override
	    public void clear() {
	        cacheContainer.clear();
	    }   
	    
	    /**
	     * cache大小
	     * @return
	     */
	    public int size(){
	    	return cacheContainer.size();
	    }
}
