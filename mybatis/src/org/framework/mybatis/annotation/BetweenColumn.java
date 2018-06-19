package org.framework.mybatis.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记po类中字段类型为区间查询类型字段
 * @author sh_leiyang
 *
 */
@Target({ java.lang.annotation.ElementType.METHOD,
	java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BetweenColumn {
	String name() default "";
}