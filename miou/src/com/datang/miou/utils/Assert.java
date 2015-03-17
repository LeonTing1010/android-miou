/**
 * Copyright 2009 Datang Mobile Co.,Ltd. All rights reserved.
 * DTM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.datang.miou.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Assertion 工具类，帮助判断非法的参数列表. 帮助开发者在运行期更早的发现错误。
 * 
 * <p>
 * 举例而言，如果一个public方法不接受<code>null</code> 参数列表, Assert 能够帮助校验这种情况
 * 
 * <p>
 * 通常用于校验方法参数，而非校验配置参数，不要将其用于配置文件中的参数校验。 本类提供的方法主要用于检查编程错误。
 * 
 * <p>
 * 本类和JUnit的assertion类似，如果参数判断不合法，, 将抛出 {@link IllegalArgumentException}
 * RuntimeException. 举例如下： <pre class="code"> Assert.notNull(clazz,
 * "The class must not be null"); Assert.isTrue(i > 0,
 * "The value must be greater than zero");</pre>
 * 
 * 主要供OP Framework内部使用。
 * 
 * @author Lance Liao
 * @since 1.0.0
 */
public abstract class Assert {

	/**
	 * 判断boolean表达式, 如果表达式结果为<code>false</code>，则抛出
	 * <code>IllegalArgumentException</code> <pre class="code">Assert.isTrue(i
	 * &gt; 0, "The value must be greater than zero");</pre>
	 * 
	 * @param expression
	 *            布尔表达式
	 * @param message
	 *            如果不满足条件时，输出的错误信息
	 * @throws IllegalArgumentException
	 *             在表达式为<code>false</code>情况下。
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断boolean表达式, 如果表达式结果为<code>false</code>，则抛出
	 * <code>IllegalArgumentException</code> <pre class="code">Assert.isTrue(i
	 * &gt; 0);</pre>
	 * 
	 * @param expression
	 *            布尔表达式
	 * @throws IllegalArgumentException
	 *             在表达式为<code>false</code>情况下。
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	/**
	 * 判断Object参数为 <code>null</code> . <pre class="code">Assert.isNull(value,
	 * "The value must be null");</pre>
	 * 
	 * @param object
	 *            需要判断的对象
	 * @param message
	 *            如果判断失败的错误信息
	 * @throws IllegalArgumentException
	 *             如果Object不为 <code>null</code>抛出
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断Object参数为 <code>null</code> . <pre
	 * class="code">Assert.isNull(value);</pre>
	 * 
	 * @param object
	 *            需要判断的对象
	 * @throws IllegalArgumentException
	 *             如果Objec不t为 <code>null</code>抛出
	 */
	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	/**
	 * 判断Object参数不能为 <code>null</code> . <pre
	 * class="code">Assert.notNull(clazz)</pre>
	 * 
	 * @param object
	 *            需要判断的对象
	 * @param message
	 *            如果判断失败的错误信息
	 * @throws IllegalArgumentException
	 *             如果Object为 <code>null</code>抛出
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断Object参数不能为 <code>null</code> . <pre
	 * class="code">Assert.notNull(clazz)</pre>
	 * 
	 * @param object
	 *            需要判断的对象
	 * @throws IllegalArgumentException
	 *             如果Object为 <code>null</code>抛出
	 */
	public static void notNull(Object object) {
		notNull(object,
				"[Assertion failed] - this argument is required; it must not be null");
	}

	/**
	 * 判断给定的String不为 null 或 length大于0, String不能为 <code>null</code> 并且不能为空串 <pre
	 * class="code">Assert.hasLength(name, "Name must not be empty");</pre>
	 * 
	 * @param text
	 *            需要检查的String
	 * @param message
	 *            如果判断失败的错误信息
	 * @see StringUtils#hasLength
	 */
	public static void hasLength(String text, String message) {
		if (!StringUtils.hasLength(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断给定的String不为 null 或 length大于0, String不能为 <code>null</code> 并且不能为空串 <pre
	 * class="code">Assert.hasLength(name);</pre>
	 * 
	 * @param text
	 *            需要检查的String
	 * @see StringUtils#hasLength
	 */
	public static void hasLength(String text) {
		hasLength(
				text,
				"[Assertion failed] - this String argument must have length; it must not be null or empty");
	}

	/**
	 * 断言给定的字符串是否包含有效文本，给定的String不能是 <code>null</code> ，并且至少包含一个非空格字符 <pre
	 * class="code">Assert.hasText(name, "'name' must not be empty");</pre>
	 * 
	 * @param text
	 *            需要检查的文本
	 * @param message
	 *            断言失败时异常的描述
	 * @see StringUtils#hasText
	 */
	public static void hasText(String text, String message) {
		if (!StringUtils.hasText(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言给定的字符串是否包含有效文本，给定的String不能是 <code>null</code> ，并且至少包含一个非空格字符 <pre
	 * class="code">Assert.hasText(name, "'name' must not be empty");</pre>
	 * 
	 * @param text
	 *            需要检查的文本
	 * @see StringUtils#hasText
	 */
	public static void hasText(String text) {
		hasText(
				text,
				"[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
	}

	/**
	 * 断言给定的文本不包括给定的Substring <pre class="code">Assert.doesNotContain(name,
	 * "rod", "Name must not contain 'rod'");</pre>
	 * 
	 * @param textToSearch
	 *            需要检查的文本
	 * @param substring
	 *            需要查找的substring
	 * @param message
	 *            断言失败时的异常的描述
	 */
	public static void doesNotContain(String textToSearch, String substring,
			String message) {
		if (StringUtils.hasLength(textToSearch)
				&& StringUtils.hasLength(substring)
				&& textToSearch.indexOf(substring) != -1) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言给定的文本不包括给定的Substring <pre class="code">Assert.doesNotContain(name,
	 * "rod");</pre>
	 * 
	 * @param textToSearch
	 *            需要检查的文本
	 * @param substring
	 *            需要查找的substring
	 */
	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring,
				"[Assertion failed] - this String argument must not contain the substring ["
						+ substring + "]");
	}

	/**
	 * 断言给定的array包括至少一个元素；array必须不为 <code>null</code> 并且包括至少一个元素 <pre
	 * class="code">Assert.notEmpty(array,
	 * "The array must have elements");</pre>
	 * 
	 * @param array
	 *            需要检查的array
	 * @param message
	 *            断言失败时的异常描述
	 * @throws IllegalArgumentException
	 *             断言失败时抛出该异常
	 */
	public static void notEmpty(Object[] array, String message) {
		if (ObjectUtils.isEmpty(array)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言给定的array包括至少一个元素；array必须不为 <code>null</code> 并且包括至少一个元素 <pre
	 * class="code">Assert.notEmpty(array);</pre>
	 * 
	 * @param array
	 *            需要检查的array
	 * @throws IllegalArgumentException
	 *             断言失败时抛出该异常
	 */
	public static void notEmpty(Object[] array) {
		notEmpty(
				array,
				"[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	/**
	 * 断言给定的array中不包括null 元素 注意: 当array 为empty时不断言失败 <pre
	 * class="code">Assert.noNullElements(array,
	 * "The array must have non-null elements");</pre>
	 * 
	 * @param array
	 *            需要检查的array
	 * @param message
	 *            断言失败时的异常描述信息
	 * @throws IllegalArgumentException
	 *             断言失败时抛出的异常
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}
	}

	/**
	 * 断言给定的array中不包括null 元素 注意: 当array 为empty时不断言失败 <pre
	 * class="code">Assert.noNullElements(array);</pre>
	 * 
	 * @param array
	 *            需要检查的array
	 * @throws IllegalArgumentException
	 *             断言失败时抛出该异常
	 */
	public static void noNullElements(Object[] array) {
		noNullElements(array,
				"[Assertion failed] - this array must not contain any null elements");
	}

	/**
	 * 断言Collection中包含元素，也就是说Collection中不能为 <code>null</code> 并且至少包括一个元素. <pre
	 * class="code">Assert.notEmpty(collection,
	 * "Collection must have elements");</pre>
	 * 
	 * @param collection
	 *            需要检查的Collection
	 * @param message
	 *            断言失败时
	 * @throws IllegalArgumentException
	 *             断言失败时抛出该异常
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言Collection中包含元素，也就是说Collection中不能为 <code>null</code> 并且至少包括一个元素. <pre
	 * class="code">Assert.notEmpty(collection,
	 * "Collection must have elements");</pre>
	 * 
	 * @param collection
	 *            需要检查的Collection
	 * @throws IllegalArgumentException
	 *             断言失败时抛出该异常
	 */
	public static void notEmpty(Collection<?> collection) {
		notEmpty(
				collection,
				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	/**
	 * 断言Map包括entry，map不能为<code>null</code> 并且至少包含一个entry <pre
	 * class="code">Assert.notEmpty(map, "Map must have entries");</pre>
	 * 
	 * @param map
	 *            需要检查的Map
	 * @param message
	 *            断言失败时的异常描述信息
	 * @throws IllegalArgumentException
	 *             断言失败时抛出该异常
	 */
	public static void notEmpty(Map<?, ?> map, String message) {
		if (CollectionUtils.isEmpty(map)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言Map包括entry，map不能为<code>null</code> 并且至少包含一个entry <pre
	 * class="code">Assert.notEmpty(map);</pre>
	 * 
	 * @param map
	 *            需要检查的map
	 * @throws IllegalArgumentException
	 *             断言失败时抛出该异常
	 */
	public static void notEmpty(Map<?, ?> map) {
		notEmpty(
				map,
				"[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}

	/**
	 * 断言给定的Object时给定的Class 类型 <pre class="code">Assert.instanceOf(Foo.class,
	 * foo);</pre>
	 * 
	 * @param clazz
	 *            需要检查的类型
	 * @param obj
	 *            需要检查的对象
	 * @throws IllegalArgumentException
	 *             断言失败时抛出该异常
	 * @see Class#isInstance
	 */
	public static void isInstanceOf(Class<?> clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

	/**
	 * 断言给定的Object时给定的Class 类型 <pre class="code">Assert.instanceOf(Foo.class,
	 * foo);</pre>
	 * 
	 * @param type
	 *            需要检查的Class类型
	 * @param obj
	 *            需要检查的对象
	 * @param message
	 *            断言失败时的异常信息
	 * @throws IllegalArgumentException
	 *             断言失败时抛出该异常
	 * @see Class#isInstance
	 */
	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException(message + "Object of class ["
					+ (obj != null ? obj.getClass().getName() : "null")
					+ "] must be an instance of " + type);
		}
	}

	/**
	 * 断言 <code>superType.isAssignableFrom(subType)</code> is <code>true</code>.
	 * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
	 * 
	 * @param superType
	 *            需要检查的父类
	 * @param subType
	 *            需要检查的子类
	 * @throws IllegalArgumentException
	 *             断言失败时抛出该异常
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}

	/**
	 * 断言 <code>superType.isAssignableFrom(subType)</code> is <code>true</code>.
	 * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
	 * 
	 * @param superType
	 *            需要检查的父类
	 * @param subType
	 *            需要检查的子类
	 * @param message
	 *            断言失败时的异常描述信息
	 * @throws IllegalArgumentException
	 *             断言失败时抛出该异常
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType,
			String message) {
		notNull(superType, "Type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			throw new IllegalArgumentException(message + subType
					+ " is not assignable to " + superType);
		}
	}

	/**
	 * 判断一个boolean表达式,如果表达式为<code>false</code>，则抛出{@link IllegalStateException}
	 * 也可以
	 * <p>
	 * 调用 {@link #isTrue(boolean)} 当判断失败是抛出 {@link IllegalArgumentException}.
	 * <pre class="code">Assert.state(id == null,
	 * "The id property must not already be initialized");</pre>
	 * 
	 * @param expression
	 *            需要判断的布尔表达式
	 * @param message
	 *            the 判断失败是的错误信息
	 * @throws IllegalStateException
	 *             如果判断结果为 <code>false</code>，抛出该异常
	 */
	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	/**
	 * 判断一个boolean表达式,如果表达式为<code>false</code>，则抛出{@link IllegalStateException}
	 * 也可以
	 * <p>
	 * 调用 {@link #isTrue(boolean)} 当判断失败是抛出 {@link IllegalArgumentException}.
	 * <pre class="code">Assert.state(id == null);</pre>
	 * 
	 * @param expression
	 *            需要判断的布尔表达式
	 * @throws IllegalStateException
	 *             如果判断结果为 <code>false</code>，抛出该异常
	 */
	public static void state(boolean expression) {
		state(expression,
				"[Assertion failed] - this state invariant must be true");
	}

}
