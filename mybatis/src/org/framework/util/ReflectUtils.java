package org.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtils {

	/**
	 * 得到指定类型的指定位置的泛型实参
	 *
	 * @param clazz
	 * @param index
	 * @param <T>
	 * @return
	 */
	public static <T> Class<T> findParameterizedType(Class<?> clazz, int index) {
		Type parameterizedType = clazz.getGenericSuperclass();
		// CGLUB subclass target object(泛型在父类上)
		if (!(parameterizedType instanceof ParameterizedType)) {
			parameterizedType = clazz.getSuperclass().getGenericSuperclass();
		}
		if (!(parameterizedType instanceof ParameterizedType)) {
			return null;
		}
		Type[] actualTypeArguments = ((ParameterizedType) parameterizedType).getActualTypeArguments();
		if (actualTypeArguments == null || actualTypeArguments.length == 0) {
			return null;
		}
		return (Class<T>) actualTypeArguments[0];
	}

	/**
	 * 利用反射获取指定对象的指定属性
	 *
	 * @param obj
	 *            目标对象
	 * @param fieldName
	 *            目标属性
	 * @return 目标属性的值
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Object result = null;
		Field field = getField(obj, fieldName);
		if (field != null) {
			field.setAccessible(true);
			try {
				result = field.get(obj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 利用反射获取指定对象里面的指定属性
	 *
	 * @param obj
	 *            目标对象
	 * @param fieldName
	 *            目标属性
	 * @return 目标字段
	 */
	private static Field getField(Object obj, String fieldName) {
		Field field = null;
		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
				// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
			}
		}
		return field;
	}

	/**
	 * 利用反射设置指定对象的指定属性为指定的值
	 *
	 * @param obj
	 *            目标对象
	 * @param fieldName
	 *            目标属性
	 * @param fieldValue
	 *            目标值
	 */
	public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
		Field field = ReflectUtils.getField(obj, fieldName);
		if (field != null) {
			try {
				field.setAccessible(true);
				field.set(obj, fieldValue);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
