package org.framework.abstracter;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.framework.mybatis.annotation.UUID;
import org.framework.mybatis.mapper.EntityMapper;
import org.framework.util.ReflectUtils;
import org.framework.util.UUIDUtils;
import org.springframework.core.annotation.AnnotationUtils;

public abstract class AbstractService<E extends AbstractEntity> implements InterfaceService<E> {

	public abstract EntityMapper getDaoMapper();
	
	private Class<E> entityClass;
	
	private UUID uuid;
	
	/**
	 * 通过构造方法初始化一些东西
	 */
	protected AbstractService() {
		entityClass = ReflectUtils.findParameterizedType(getClass(), 0);
		uuid = findUUIDAnnotation(entityClass);
	}
	
	/**
	 * 列表查询或分页查询
	 * @param paramMap
	 * @return
	 */
	public List<E> queryMysql(Map paramMap) {
		return getDaoMapper().queryMysql(paramMap);
	}
	
	/**
	 * 单条查询
	 * @param paramObject
	 * @return
	 */
	public E get(Object paramObject) {
		return (E)getDaoMapper().get(paramObject);
	}
	
	/**
	 * 单条新增
	 * @param param
	 * @return
	 */
	public int insert(E param) {
		if(uuid != null && (param.getId() == null || "".equals(param.getId()))) {
			param.setId(UUIDUtils.nextUUID(uuid.value()));
		}
		if(param instanceof InsertAble) {
			((InsertAble) param).setInsertTime(new Date());
		}
		return getDaoMapper().insert(param);
	}
	
	/**
	 * 批量新增
	 * @param paramList
	 * @return
	 */
	public int batchInsert(List<E> paramList) {
		if(uuid != null) {
			for(E e : paramList) {
				if(e.getId() == null || "".equals(e.getId()))
					e.setId(UUIDUtils.nextUUID(uuid.value()));
				if(e instanceof InsertAble) {
					((InsertAble) e).setInsertTime(new Date());
				}
			}
		}
		return getDaoMapper().batchInsert(paramList);
	}
	
	/**
	 * 带选择的单个修改
	 * @param param
	 * @return
	 */
	public int updateSelectiveMysql(E param) {
		if(param instanceof UpdateAble) {
			((UpdateAble) param).setUpdateTime(new Date());
		}
		return getDaoMapper().updateSelectiveMysql(param);
	}
	
	/**
	 * 带选择的批量修改
	 * @param paramList
	 * @return
	 */
	public int batchUpdateSelectiveMysql(List<E> paramList) {
		for(E e : paramList) {
			if(e instanceof UpdateAble) {
				((UpdateAble) e).setUpdateTime(new Date());
			}
		}
		return getDaoMapper().batchUpdateSelectiveMysql(paramList);
	}
	
	/**
	 * 单个修改所有字段
	 * @param param
	 * @return
	 */
	public int update(E param) {
		if(param instanceof UpdateAble) {
			((UpdateAble) param).setUpdateTime(new Date());
		}
		return getDaoMapper().update(param);
	}
	
	/**
	 * 批量修改所有字段
	 * @param paramList
	 * @return
	 */
	public int batchUpdate(List<E> paramList) {
		for(E e : paramList) {
			if(e instanceof UpdateAble) {
				((UpdateAble) e).setUpdateTime(new Date());
			}
		}
		return getDaoMapper().batchUpdate(paramList);
	}
	
	/**
	 * 单条删除
	 * @param paramObject
	 * @return
	 */
	public int delete(Object paramObject) {
		return getDaoMapper().delete(paramObject);
	}
	
	/**
	 * 批量删除
	 * @param paramArrayOfObject
	 * @return
	 */
	public int batchDelete(Object[] paramArrayOfObject) {
		return getDaoMapper().batchDelete(paramArrayOfObject);
	}
	
	/**
	 * 单条保存
	 * @param param
	 * @return
	 */
	public E save(E param) {
		if (param.getId() == null || "".equals(param.getId())) {
            // 新增数据
			param.setId(UUIDUtils.nextUUID());
			if(param instanceof InsertAble) {
				((InsertAble) param).setInsertTime(new Date());
			}
            getDaoMapper().insert(param);
        } else {
        	if(param instanceof UpdateAble) {
				((UpdateAble) param).setUpdateTime(new Date());
			}
        	// 更新数据
        	getDaoMapper().updateSelectiveMysql(param);
        }
        return param;
	}
	
	/**
	 * 获取setId上的注解UUID
	 * @param entityClass
	 * @return
	 */
	private UUID findUUIDAnnotation(Class<E> entityClass) {
		UUID uuid;
		try {
			Method method = entityClass.getDeclaredMethod("setId", Serializable.class);
			uuid = AnnotationUtils.findAnnotation(method, UUID.class);
			return uuid;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
