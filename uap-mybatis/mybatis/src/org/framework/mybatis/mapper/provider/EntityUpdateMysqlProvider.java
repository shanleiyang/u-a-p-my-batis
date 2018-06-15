package org.framework.mybatis.mapper.provider;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SetSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;

import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

@SuppressWarnings("all")
public class EntityUpdateMysqlProvider extends MapperTemplate {

	public EntityUpdateMysqlProvider(Class<?> mapperClass,
			MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}
	
	public SqlNode updateSelectiveMysql(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
	    List sqlNodes = new LinkedList();

	    sqlNodes.add(new StaticTextSqlNode("UPDATE " + tableName(entityClass)));

	    Set columnList = EntityHelper.getColumns(entityClass);
	    List ifNodes = new LinkedList();
	    EntityHelper.EntityColumn column;
	    for (Iterator localIterator = columnList.iterator(); localIterator.hasNext();) {
	    	column = (EntityHelper.EntityColumn)localIterator.next();
			if (!(column.isId())) {
				ifNodes.add(getIfNotNull(column, new StaticTextSqlNode(column.getColumn() + " = #{" + column.getProperty() + "}, ")));
			}
	    }
	    sqlNodes.add(new SetSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes)));

	    columnList = EntityHelper.getPKColumns(entityClass);
	    List whereNodes = new LinkedList();
	    for (Iterator localIterator = columnList.iterator(); localIterator.hasNext();) {
	    	column = (EntityHelper.EntityColumn)localIterator.next();
	    	whereNodes.add(getIfNotNull(column, new StaticTextSqlNode(column.getColumn() + " = #{" + column.getProperty() + "} ")));
	    }
	    sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), new MixedSqlNode(whereNodes)));
	    return new MixedSqlNode(sqlNodes);
	}

}
