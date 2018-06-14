package org.framework.mybatis.mapper;

import org.framework.mybatis.mapper.api.BatchDeleteMapper;
import org.framework.mybatis.mapper.api.BatchInsertMapper;
import org.framework.mybatis.mapper.api.BatchSaveMapper;
import org.framework.mybatis.mapper.api.BatchUpdateMapper;
import org.framework.mybatis.mapper.api.BatchUpdateSelectiveMysqlMapper;
import org.framework.mybatis.mapper.api.DeleteByPrimaryKeyMapper;
import org.framework.mybatis.mapper.api.GetAllMapper;
import org.framework.mybatis.mapper.api.GetByPrimaryKeyMapper;
import org.framework.mybatis.mapper.api.GetTotalCountMapper;
import org.framework.mybatis.mapper.api.InsertMapper;
import org.framework.mybatis.mapper.api.QueryMapper;
import org.framework.mybatis.mapper.api.QueryMysqlMapper;
import org.framework.mybatis.mapper.api.UpdateByPrimaryKeyMapper;
import org.framework.mybatis.mapper.api.UpdateSelectiveMysqlMapper;

public interface EntityMapper<T> extends GetAllMapper<T>,
		GetByPrimaryKeyMapper<T>, QueryMapper<T>, GetTotalCountMapper<T>,
		InsertMapper<T>, UpdateByPrimaryKeyMapper<T>,
		DeleteByPrimaryKeyMapper<T>, BatchInsertMapper<T>,
		BatchUpdateMapper<T>, BatchDeleteMapper<T>, BatchSaveMapper<T>,
		UpdateSelectiveMysqlMapper<T>, BatchUpdateSelectiveMysqlMapper<T>, QueryMysqlMapper<T> {

}
