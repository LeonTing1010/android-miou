/**
 * Copyright 2009 Datang Mobile Co.,Ltd. All rights reserved.
 * DTM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.datang.miou.utils;

import java.util.*;
import java.util.Map.Entry;

/**
 * 各种的Collection 工具方法. 主要在平台内部使用.
 * 
 * @author Lance Liao
 * @since 1.0.0
 */
public abstract class CollectionUtils {

	/**
	 * 如果给定的Collection为<code>null</code>或者empty,返回 <code>true</code> 否则返回
	 * <code>false</code>.
	 * 
	 * @param collection
	 *            需要检查的 collection
	 * @return 给定的collection是否为null或者empty
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * 如果给定的map为<code>null</code>或者empty,返回 <code>true</code> 否则返回
	 * <code>false</code>.
	 * 
	 * @param map
	 *            需要检查的 map
	 * @return 给定的map是否为null或者empty
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}

	/**
	 * 将给定的array转换为List结构. 原始数据类型（int、byte等）会转换为Wrapped 类的List
	 * <p>
	 * 给定的 <code>null</code>数组会转换为一个empty的List.
	 * 
	 * @param source
	 *            需要转换的数组，可以是原始数据类型
	 * @return 转换后的List，原始数据类型会被Wrap
	 * @see ObjectUtils#toObjectArray(Object)
	 */
	public static List<?> arrayToList(Object source) {
		return Arrays.asList(ObjectUtils.toObjectArray(source));
	}

	/**
	 * 将给定的数组合并到给定的Collection结构中.
	 * 
	 * @param array
	 *            需要合并的数组 (可以为 <code>null</code>)
	 * @param collection
	 *            需要合并到的Collection
	 */
	public static void mergeArrayIntoCollection(Object array,
			Collection<Object> collection) {
		junit.framework.Assert.assertNotNull("Collection must not be null",
				collection);
		Object[] arr = ObjectUtils.toObjectArray(array);
		for (int i = 0; i < arr.length; i++) {
			collection.add(arr[i]);
		}
	}

	/**
	 * 将给定的properties实例合并到给定的Map结构中 复制所有的properties(key-value pairs).
	 * <p>
	 * 使用 <code>Properties.propertyNames()</code>获取属性
	 * 
	 * @param props
	 *            需要合并的properties (可以为 <code>null</code>)
	 * @param map
	 *            需要合并至的目标Map
	 */
	public static void mergePropertiesIntoMap(Properties props,
			Map<String, String> map) {
		Assert.notNull(props, "Map must not be null");

		if (props != null) {
			for (Enumeration en = props.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				map.put(key, props.getProperty(key));
			}
		}
	}

	/**
	 * 检查给定的Iterator是否包含给定的元素，根据equals()方法判断
	 * 
	 * @param iterator
	 *            需要检查的iterator
	 * @param element
	 *            需要查找的元素
	 * @return <code>true</code> if found, <code>false</code> else
	 */
	public static boolean contains(Iterator iterator, Object element) {
		if (iterator != null) {
			while (iterator.hasNext()) {
				Object candidate = iterator.next();
				if (ObjectUtils.nullSafeEquals(candidate, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查给定的Enumeration中是否包含给定的元素，根据equals()方法判断
	 * 
	 * @param enumeration
	 *            需要检查的enumeration
	 * @param element
	 *            需要检查的是否包括的元素
	 * @return <code>true</code>如果包括，否则返回 <code>false</code>
	 */
	public static boolean contains(Enumeration enumeration, Object element) {
		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				Object candidate = enumeration.nextElement();
				if (ObjectUtils.nullSafeEquals(candidate, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查给定的collection中是否包含给定的元素，根据 == 方法判断 必须是同一个实例才返回true
	 * 
	 * @param collection
	 *            需要检查的collection
	 * @param element
	 *            需要检查的元素
	 * @return <code>true</code>如果包括，否则返回 <code>false</code>
	 */
	public static boolean containsInstance(Collection collection, Object element) {
		if (collection != null) {
			for (Iterator it = collection.iterator(); it.hasNext();) {
				Object candidate = it.next();
				if (candidate == element) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 提取Map<K,List<V>> 中的所有V放入List<V> 如果Map为Null返回空List
	 * 
	 * @param <V>
	 *            Value
	 * @param <K>
	 *            Key
	 * @param map
	 *            Map
	 * @return List
	 */
	public static <K, V> List<V> mapToList(Map<K, List<V>> map) {
		List<V> list = new ArrayList<V>();
		if (map != null) {
			for (Entry<K, List<V>> entry : map.entrySet()) {
				if (!CollectionUtils.isEmpty(entry.getValue())) {
					list.addAll(entry.getValue());
				}
			}
		}
		return list;
	}
}
