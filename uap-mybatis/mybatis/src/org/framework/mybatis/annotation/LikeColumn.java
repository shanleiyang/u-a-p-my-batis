package org.framework.mybatis.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记po类中字段为模糊查询字段
 * @author sh_leiyang
 *
 */
@Target({ java.lang.annotation.ElementType.METHOD,
	java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LikeColumn {
	String name() default "";
}