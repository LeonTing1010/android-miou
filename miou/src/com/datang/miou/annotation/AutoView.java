package com.datang.miou.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自动加载视图
 * 
 * @author suntongwei
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoView {
	
	/**
	 * android res Id
	 * 
	 * @return
	 */
	public int value() default Integer.MIN_VALUE;
	
}
