package org.framework.mybatis.mapper.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.framework.mybatis.annotation.ArrayColumn;
import org.framework.mybatis.annotation.BetweenColumn;
import org.framework.mybatis.annotation.LikeColumn;
import org.framework.mybatis.annotation.OrColumn;

import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.EntityHelper.EntityColumn;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

public class EntityGetProvider extends MapperTemplate {
	public EntityGetProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}
	
	public String get(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
		setResultType(ms, entityClass);
		
		StringBuilder sql = new StringBuilder();
		sql.append("select ")
				.append(EntityHelper.getSelectColumns(entityClass))
				.append(" from ");

		sql.append(tableName(entityClass));

		sql.append(" <where> ");
		Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(entityClass);
		Iterator<EntityColumn> itColumns = pkColumns.iterator();
		while(itColumns.hasNext()) {
			EntityColumn entityColumn = itColumns.next();
			sql.append(" and ").append(entityColumn.getColumn()).append(" = #{").append(entityColumn.getProperty()).append("} ");
		}
		sql.append(" </where> ");

		return sql.toString();
	}

	public String getAll(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
		setResultType(ms, entityClass);

		StringBuilder sql = new StringBuilder();
		sql.append("select ")
				.append(EntityHelper.getSelectColumns(entityClass))
				.append(" from ");

		sql.append(tableName(entityClass));
		
		return sql.toString();
	}

	public SqlNode getTotalCount(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
		List sqlNodes = new LinkedList();

		sqlNodes.add(new StaticTextSqlNode("SELECT COUNT(*) FROM "
				+ tableName(entityClass)));

		sqlNodes.add(new WhereSqlNode(ms.getConfiguration(),
				getAllIfColumnNode(entityClass)));
		return new MixedSqlNode(sqlNodes);
	}

	public String query(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);

		setResultType(ms, entityClass);

		EntityHelper.EntityTable table = EntityHelper
				.getEntityTable(entityClass);

		String DBType = ms.getConfiguration().getDatabaseId().toLowerCase();

		StringBuilder sql = new StringBuilder();
		sql.append("select ")
				.append(EntityHelper.getSelectColumns(entityClass))
				.append(" from ");

		sql.append(tableName(entityClass));

		sql.append(" <where> ");
		for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
			sql.append("<if test=\"").append(column.getProperty())
					.append(" != null\">");
			sql.append(" and ").append(column.getColumn()).append(" = ")
					.append("#{").append(column.getProperty()).append("}");
			sql.append("</if>");
			sql.append("<if test=\"").append(column.getProperty())
					.append("_$like").append(" != null\">");
			if (!("sqlserver".equals(DBType)))
				sql.append(" and ").append(column.getColumn()).append(" like ")
						.append("CONCAT('%',#{").append(column.getProperty())
						.append("_$like").append("},'%')");
			else {
				sql.append(" and ").append(column.getColumn()).append(" like ")
						.append("'%#{").append(column.getProperty())
						.append("_$like").append("}%'");
			}

			sql.append("</if>");
		}
		sql.append(" </where> ");

		sql.append("<if test=\"orderfield != null\" >").append(" order by ")
				.append("<choose>");
		EntityHelper.EntityColumn pkColumn = null;
		for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
			if (column.isId()) {
				pkColumn = column;
			}
			sql.append("<when test=\"orderfield == '")
					.append(column.getProperty()).append("'\">");
			sql.append(column.getProperty()).append(" #{orderdir}");
			sql.append("</when>");
		}
		if (pkColumn != null) {
			sql.append("<otherwise>");
			sql.append(pkColumn.getProperty()).append(" #{orderdir}");
			sql.append("</otherwise>");
		}
		sql.append("</choose>").append("</if>");
		return sql.toString();
	}

	public String queryMysql(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);

		setResultType(ms, entityClass);

		EntityHelper.EntityTable table = EntityHelper
				.getEntityTable(entityClass);

		String DBType = ms.getConfiguration().getDatabaseId().toLowerCase();
		//获取po类中所有的属性：key-属性名称, value-Field
		Map<String, Field> columnMap = getAllMapField(entityClass, null);

		StringBuilder sql = new StringBuilder();
		sql.append("select ")
				.append(EntityHelper.getSelectColumns(entityClass))
				.append(" from ");

		sql.append(tableName(entityClass));

		sql.append(" <where> ");
		for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
			sql.append("<if test=\"").append(column.getProperty()).append(" != null and ")
			   .append(column.getProperty()).append(" != '' \">");
			sql.append(" and ").append(column.getColumn()).append(" = ")
					.append("#{").append(column.getProperty()).append("}");
			sql.append("</if>");
			String property = column.getProperty();
			//处理date between区间查询
			if(columnMap != null && columnMap.get(property) != null 
					&& columnMap.get(property).isAnnotationPresent(BetweenColumn.class)) {
				sql.append("<if test=\"").append(column.getProperty()).append("Begin != null and ")
				   .append(column.getProperty()).append("Begin != '' and ")
				   .append(column.getProperty()).append("End != null and ")
				   .append(column.getProperty()).append("End != '' \">");
				sql.append(" and ").append(column.getColumn()).append(" between ")
						.append("#{").append(column.getProperty()).append("Begin} and ")
						.append("#{").append(column.getProperty()).append("End}");
				sql.append("</if>");
			}
			//处理字段IN查询
			if(columnMap != null && columnMap.get(property) != null 
					&& columnMap.get(property).isAnnotationPresent(ArrayColumn.class)) {
				sql.append("<if test=\"").append(column.getProperty()).append("Array != null\">");
				sql.append(" and ").append(column.getColumn()).append(" in ");
				sql.append("<foreach item=\"item\" index=\"index\" collection=\"").append(column.getProperty())
					.append("Array\" open=\"(\" separator=\",\" close=\")\" >");
				sql.append("#{item}");
				sql.append("</foreach>");
				sql.append("</if>");
			}
			//处理_$noequal 不等于的查询
			sql.append("<if test=\"").append(column.getProperty()).append("_$noequal").append(" != null and ")
			   .append(column.getProperty()).append("_$noequal").append(" != '' \">");
			sql.append(" <![CDATA[ ");
			sql.append(" and ").append(column.getColumn()).append(" <> ").append("#{").append(column.getProperty()).append("_$noequal}");
			sql.append(" ]]> ");
			sql.append("</if>");
			
			//后缀为_$like的模糊查询
			sql.append("<if test=\"").append(column.getProperty()).append("_$like").append(" != null and ")
			   .append(column.getProperty()).append("_$like").append(" != '' \">");
			if ("mysql".equals(DBType)){
				sql.append(" and ").append(column.getColumn()).append(" like ")
						.append("CONCAT('%',#{").append(column.getProperty())
						.append("_$like").append("},'%')");
			} else if ("oracle".equals(DBType)){
				sql.append(" and ").append(column.getColumn()).append(" like ")
				.append("'%' || #{").append(column.getProperty())
				.append("_$like").append("} || '%'");
			} else {
				sql.append(" and ").append(column.getColumn()).append(" like ")
						.append("'%#{").append(column.getProperty())
						.append("_$like").append("}%'");
			}

			sql.append("</if>");
		}
		//统一处理模糊查询条件：likeKeyWord
		if(columnMap != null && columnMap.get("likeKeyWord") != null) {
			String property = columnMap.get("likeKeyWord").getName();
			sql.append("<if test=\"").append(property).append(" != null and ")
			   .append(property).append(" != '' \">");
			if ("mysql".equals(DBType)){
				sql.append(" and CONCAT(");
			} else if ("oracle".equals(DBType)) {
				sql.append(" and (");
			}
			Iterator<String> ites = columnMap.keySet().iterator();
			StringBuffer likeWhere = new StringBuffer();
			while(ites.hasNext()) {
				Field field = columnMap.get(ites.next());
				if(field.isAnnotationPresent(Column.class) && field.isAnnotationPresent(LikeColumn.class)) {
					Column column = (Column) field.getAnnotation(Column.class);
					String columnName = column.name();
					if ((columnName == null) || (columnName.equals(""))) {
						columnName = EntityHelper.camelhumpToUnderline(field.getName());
					}
					if(likeWhere.length() > 0){
						if ("mysql".equals(DBType)){
							likeWhere.append(" , ");
						} else if("oracle".equals(DBType)){
							likeWhere.append(" || ");
						}
					}
					if ("mysql".equals(DBType)){
						likeWhere.append("IFNULL(").append(columnName).append(",'')");
					} else if("oracle".equals(DBType)){
						likeWhere.append("NVL(").append(columnName).append(",'')");
					}
				}
			}
			if ("mysql".equals(DBType)){
				sql.append(likeWhere).append(") like ").append("CONCAT('%',#{").append(property).append("},'%')");
			} else if("oracle".equals(DBType)){
				sql.append(likeWhere).append(") like ").append("('%' || #{").append(property).append("} || '%')");
			}
			sql.append("</if>");
		}
		//处理字段 OR 查询
		if(columnMap != null) {
			Collection<Field> values = columnMap.values();
			for(Field field : values) {
				if(field.isAnnotationPresent(OrColumn.class)) {
					sql.append("<if test=\"").append(field.getName()).append(" != null \">");
					sql.append(" and  ");
					sql.append("<foreach collection=\"").append(field.getName())
					.append("\" index=\"key\"  item=\"item\" open=\"(\" close=\")\" separator=\"or\"> ");
					sql.append(" ${key} = #{item} ");
					sql.append("</foreach>");
					sql.append("</if>");
				}
			}
		}
		sql.append(" </where> ");

		sql.append("<if test=\"orderfield != null and orderfield != ''\" >").append(" order by ")
				.append("<choose>");
		EntityHelper.EntityColumn pkColumn = null;
		for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
			if (column.isId()) {
				pkColumn = column;
			}
			sql.append("<when test=\"orderfield == '")
					.append(column.getProperty()).append("'\">");
			sql.append(column.getProperty()).append(" ${orderdir}");
			sql.append("</when>");
		}
		if (pkColumn != null) {
			sql.append("<otherwise>");
			sql.append(pkColumn.getProperty()).append(" ${orderdir}");
			sql.append("</otherwise>");
		}
		sql.append("</choose>").append("</if>");
		
		return sql.toString();
	}

	public SqlNode getTotalCountMysql(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
		List sqlNodes = new LinkedList();

		sqlNodes.add(new StaticTextSqlNode("SELECT COUNT(*) FROM "
				+ tableName(entityClass)));

		sqlNodes.add(new WhereSqlNode(ms.getConfiguration(),
				getAllIfColumnNode(entityClass)));
		return new MixedSqlNode(sqlNodes);
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
