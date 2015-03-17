package com.datang.miou.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Activity Extra Annotation
 * 
 * @author suntongwei
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Extra {

	/**
	 * <p>
	 * 指定标识名
	 * 如果为空，则使用变量名
	 * </p>
	 * 
	 * @return
	 */
	public String value() default "";
	
	/**
	 * <p>
	 * 是否为必须参数，默认FALSE
	 * 如果为TRUE，Intent中没有改参数，则会执行finish()方法
	 * </p>
	 * 
	 * @return
	 */
	public boolean must() default false;
}
