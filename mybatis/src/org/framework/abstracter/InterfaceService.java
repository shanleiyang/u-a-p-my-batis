package org.framework.abstracter;

import java.util.List;
import java.util.Map;

public interface InterfaceService<E extends AbstractEntity> {

	/**
	 * 列表查询或分页查询
	 * @param paramMap
	 * @return
	 */
	List<E> queryMysql(Map paramMap);
	
	/**
	 * 单条查询
	 * @param paramObject
	 * @return
	 */
	E get(Object paramObject);
	
	/**
	 * 单条新增
	 * @param param
	 * @return
	 */
	int insert(E param);
	
	/**
	 * 批量新增
	 * @param paramList
	 * @return
	 */
	int batchInsert(List<E> paramList);
	
	/**
	 * 带选择的单个修改
	 * @param param
	 * @return
	 */
	int updateSelectiveMysql(E param);
	
	/**
	 * 带选择的批量修改
	 * @param paramList
	 * @return
	 */
	int batchUpdateSelectiveMysql(List<E> paramList);
	
	/**
	 * 单个修改所有字段
	 * @param param
	 * @return
	 */
	int update(E param);
	
	/**
	 * 批量修改所有字段
	 * @param paramList
	 * @return
	 */
	int batchUpdate(List<E> paramList);
	
	/**
	 * 单条删除
	 * @param paramObject
	 * @return
	 */
	int delete(Object paramObject);
	
	/**
	 * 批量删除
	 * @param paramArrayOfObject
	 * @return
	 */
	int batchDelete(Object[] paramArrayOfObject);
	
	/**
	 * 单条保存
	 * @param param
	 * @return
	 */
	E save(E param);
}
