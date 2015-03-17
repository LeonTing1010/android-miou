package com.datang.miou.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Activity Result Annotation
 * 
 * @author suntongwei
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Result {

	/**
	 * <p>
	 * RequestId
	 * value默认值为Integer.MIN_VALUE，表示全部都需要过滤
	 * </p>
	 * 
	 * @return
	 */
	public int value() default Integer.MIN_VALUE;
}
