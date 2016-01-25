/**
 * <p>Author		:	cielo</p>
 * <p>Date 			: 	2016 上午10:49:20</p>
 */
package com.lezic.core.crud.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段注解
 * 
 * @author cielo
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebField {

	/**
	 * 显示名
	 * 
	 * @return
	 * @author cielo
	 */
	public String label() default "显示名";

	/**
	 * 提示输入信息
	 * 
	 * @return
	 * @author cielo
	 */
	public String placeholder() default "请输入";

	/**
	 * 是否唯一
	 */
	public boolean unique() default false;

	/**
	 * 是否可为空
	 */
	public boolean nullable() default true;

	/**
	 * 最大字符数
	 */
	public int length();	
	
}
