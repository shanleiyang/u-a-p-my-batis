package org.framework.mybatis.mapper.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.ibatis.mapping.MappedStatement;
import org.framework.mybatis.annotation.WhereAnd;

import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

@SuppressWarnings("all")
public class EntityBatchMysqlProvider extends MapperTemplate {

	public EntityBatchMysqlProvider(Class<?> mapperClass,
			MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}
	
	public String batchUpdateSelectiveMysql(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
		EntityHelper.EntityTable table = EntityHelper.getEntityTable(entityClass);
		Map<String, Field> columnMap = getAllMapField(entityClass, null);
		StringBuilder sql = new StringBuilder();
		
		sql.append("<foreach collection=\"list\" item=\"item\" separator=\";\" >");
		sql.append("update ");
	    sql.append(table.getName());
	    sql.append(" <set> ");
	    
	    EntityHelper.EntityColumn pkColumn = null;
	    List<EntityHelper.EntityColumn> clist=new ArrayList<EntityHelper.EntityColumn>();
	    
	    for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
	      if (column.isId()) {
	    	  pkColumn = column;
	      }
	      else {
	    	  sql.append("<if test=\"item.").append(column.getProperty()).append(" !=null\" >");
		      sql.append(column.getColumn());
		      sql.append("=");
		      sql.append("#{item.").append(column.getProperty()).append("}, ");
		      sql.append("</if>");
		      
		      if(columnMap.get(column.getProperty()) != null && 
		    		  columnMap.get(column.getProperty()).isAnnotationPresent(WhereAnd.class) 
		    		  
		    		  ){
		    	  clist.add(column);
		      }
		      
		      
	      }
	    }
	    sql.append(" </set> ");
	    sql.append(" where ");
	    sql.append(pkColumn.getColumn());
	    sql.append("=");
	    sql.append("#{item.").append(pkColumn.getProperty()).append("}");
	    
	    if(clist.size()>0){
	    	for(EntityHelper.EntityColumn column : clist){
	    		  sql.append("<if test=\"item.").append(column.getProperty()).append(" !=null\" >");
			      sql.append(" and "+ column.getColumn());
			      sql.append("=");
			      sql.append("#{item.").append(column.getProperty()).append("}");
			      sql.append("</if>");
	    	}
	    }

	    sql.append("</foreach>");
		
		return sql.toString();
	}
	
	private static Map<String, Field> getAllMapField(Class<?> entityClass, Map<String, Field> fieldMap) {
		if (fieldMap == null) {
			fieldMap = new HashMap<String, Field>();
		}
		if (entityClass.equals(Object.class)) {
			return fieldMap;
		}
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			if (!(Modifier.isStatic(field.getModifiers()))) {
				fieldMap.put(field.getName(), field);
			}
		}
		Class superClass = entityClass.getSuperclass();
		if ((superClass != null)
				&& (!(superClass.equals(Object.class)))
				&& (((superClass.isAnnotationPresent(Entity.class)) || ((!(Map.class
						.isAssignableFrom(superClass))) && (!(Collection.class
						.isAssignableFrom(superClass))))))) {
			return getAllMapField(entityClass.getSuperclass(), fieldMap);
		}
		return fieldMap;
	}
}
