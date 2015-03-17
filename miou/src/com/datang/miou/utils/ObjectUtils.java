/**
 * Copyright 2009 Datang Mobile Co.,Ltd. All rights reserved.
 * DTM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.datang.miou.utils;

import java.lang.reflect.Array;
import java.util.Arrays;


/**
 * 各种不同的对象工具类方法 主要用于OP framework内部。
 * 
 * @author Lance Liao
 * @since 1.0.0
 */
public abstract class ObjectUtils {

	private static final int INITIAL_HASH = 7;
	private static final int MULTIPLIER = 31;

	private static final String EMPTY_STRING = "";
	private static final String NULL_STRING = "null";
	private static final String ARRAY_START = "{";
	private static final String ARRAY_END = "}";
	private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;
	private static final String ARRAY_ELEMENT_SEPARATOR = ", ";

	/**
	 * 检查Throwable是否为checked exception. 也就是说，ex不是runtimeException或者error
	 * 
	 * @param ex
	 *            需要检查的Throwable
	 * @return throwable 是否为checked exception
	 * @see Exception
	 * @see RuntimeException
	 * @see Error
	 */
	public static boolean isCheckedException(Throwable ex) {
		return !(ex instanceof RuntimeException || ex instanceof Error);
	}

	/**
	 * 检查给定的exception是否能转换为给定的Class数组，同时满足为Check whether the given exception is
	 * compatible with the exceptions declared in a throws clause.
	 * 
	 * @param ex
	 *            需要检查的Throwable
	 * @param declaredExceptions
	 *            the exceptions declared in the throws clause
	 * @return whether the given exception is compatible
	 */
	public static boolean isCompatibleWithThrowsClause(Throwable ex,
			Class<?>[] declaredExceptions) {
		if (!isCheckedException(ex)) {
			return true;
		}
		if (declaredExceptions != null) {
			for (int i = 0; i < declaredExceptions.length; i++) {
				if (declaredExceptions[i].isAssignableFrom(ex.getClass())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查给定的数组为empty或者为<code>null<code/>
	 * 
	 * @param array
	 *            需要检查的数组
	 * @return 是否给定的数组为empty或者为null
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * 检查给定的array是否包含给定的元素，通过equals判断
	 * 
	 * @param array
	 *            需要检查的array ，可以为 <code>null</code>,为null时返回false。
	 * @param element
	 *            需要检查的元素
	 * @return 是否包含给定的元素
	 */
	public static boolean containsElement(Object[] array, Object element) {
		// 如果为null，返回false
		if (array == null) {
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if (nullSafeEquals(array[i], element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 将给定的object增加到给定的array中，返回增加元素后的新数组
	 * 
	 * @param array
	 *            需要增加的数组 (可以为 <code>null</code>)
	 * @param obj
	 *            需要增加的元素
	 * @return 增加后的数组，和原数组类型相同，如果原数组为null，返回增加后元素的数组
	 */
	public static Object[] addObjectToArray(Object[] array, Object obj) {
		Class compType = Object.class;
		if (array != null) {
			compType = array.getClass().getComponentType();
		} else if (obj != null) {
			compType = obj.getClass();
		}
		int newArrLength = (array != null ? array.length + 1 : 1);
		Object[] newArr = (Object[]) Array.newInstance(compType, newArrLength);
		if (array != null) {
			System.arraycopy(array, 0, newArr, 0, array.length);
		}
		newArr[newArr.length - 1] = obj;
		return newArr;
	}

	/**
	 * 将给定的数组（可以为原始类型数组）转换为对象类型数组，原始类型数组将被封装
	 * <p>
	 * 如果source为 <code>null</code> ，将转换为一个empty的对象数组
	 * 
	 * @param source
	 *            需要转换的数组，可以为原始数据类型
	 * @return 转换后的数组 (永不为 <code>null</code>)
	 * @throws IllegalArgumentException
	 *             如果source不为null且不是数组类型
	 */
	public static Object[] toObjectArray(Object source) {
		if (source instanceof Object[]) {
			return (Object[]) source;
		}
		if (source == null) {
			return new Object[0];
		}
		Assert.isTrue(source.getClass().isArray(), "Source is not an array:"
				+ source);

		int length = Array.getLength(source);
		if (length == 0) {
			return new Object[0];
		}
		Class wrapperType = Array.get(source, 0).getClass();
		Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
		for (int i = 0; i < length; i++) {
			newArray[i] = Array.get(source, i);
		}
		return newArray;
	}

	/**
	 * 判断给定的对象是否是equals，如果相等，返回<code>true</code> 如果两个对象都为<code>null</code> 也返回
	 * <code>true<code/>,
	 * 如果只有一个为<code>null<code/>,返回<code>false</code>
	 * <p>
	 * 如果是array的比较，通过 <code>Arrays.equals</code>, 比较array中元素是否equals.
	 * 
	 * @param o1
	 *            第一个要比较的对象
	 * @param o2
	 *            第二个要比较的对象
	 * @return 是否给定的对象是equals
	 * @see java.util.Arrays#equals
	 */
	public static boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.equals(o2)) {
			return true;
		}
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			if (o1 instanceof Object[] && o2 instanceof Object[]) {
				return Arrays.equals((Object[]) o1, (Object[]) o2);
			}
			if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
				return Arrays.equals((boolean[]) o1, (boolean[]) o2);
			}
			if (o1 instanceof byte[] && o2 instanceof byte[]) {
				return Arrays.equals((byte[]) o1, (byte[]) o2);
			}
			if (o1 instanceof char[] && o2 instanceof char[]) {
				return Arrays.equals((char[]) o1, (char[]) o2);
			}
			if (o1 instanceof double[] && o2 instanceof double[]) {
				return Arrays.equals((double[]) o1, (double[]) o2);
			}
			if (o1 instanceof float[] && o2 instanceof float[]) {
				return Arrays.equals((float[]) o1, (float[]) o2);
			}
			if (o1 instanceof int[] && o2 instanceof int[]) {
				return Arrays.equals((int[]) o1, (int[]) o2);
			}
			if (o1 instanceof long[] && o2 instanceof long[]) {
				return Arrays.equals((long[]) o1, (long[]) o2);
			}
			if (o1 instanceof short[] && o2 instanceof short[]) {
				return Arrays.equals((short[]) o1, (short[]) o2);
			}
		}
		return false;
	}

	/**
	 * 返回给定的对象的hashcode; 一般通过Object上的方法完成 <code>{@link Object#hashCode()}</code>
	 * .
	 * <p>
	 * 如果对象是一个array，本方法通过<code>nullSafeHashCode</code> 方法判断. 如果对象为
	 * <code>null</code>, 本方法返回0.
	 * 
	 * @param obj
	 *            给定的对象
	 * @return 给定的对象的hashcode
	 * @see #nullSafeHashCode(Object[])
	 * @see #nullSafeHashCode(boolean[])
	 * @see #nullSafeHashCode(byte[])
	 * @see #nullSafeHashCode(char[])
	 * @see #nullSafeHashCode(double[])
	 * @see #nullSafeHashCode(float[])
	 * @see #nullSafeHashCode(int[])
	 * @see #nullSafeHashCode(long[])
	 * @see #nullSafeHashCode(short[])
	 */
	public static int nullSafeHashCode(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj.getClass().isArray()) {
			if (obj instanceof Object[]) {
				return nullSafeHashCode((Object[]) obj);
			}
			if (obj instanceof boolean[]) {
				return nullSafeHashCode((boolean[]) obj);
			}
			if (obj instanceof byte[]) {
				return nullSafeHashCode((byte[]) obj);
			}
			if (obj instanceof char[]) {
				return nullSafeHashCode((char[]) obj);
			}
			if (obj instanceof double[]) {
				return nullSafeHashCode((double[]) obj);
			}
			if (obj instanceof float[]) {
				return nullSafeHashCode((float[]) obj);
			}
			if (obj instanceof int[]) {
				return nullSafeHashCode((int[]) obj);
			}
			if (obj instanceof long[]) {
				return nullSafeHashCode((long[]) obj);
			}
			if (obj instanceof short[]) {
				return nullSafeHashCode((short[]) obj);
			}
		}
		return obj.hashCode();
	}

	/**
	 * 返回给定对象数组的hashcode，基于数组中元素的hashcode计算 如果 <code>array</code> 为
	 * <code>null</code>, 本方法返回 0.
	 * 
	 * @param array
	 *            给定对象数组
	 * @return 给定对象数组的hashcode
	 */
	public static int nullSafeHashCode(Object[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + nullSafeHashCode(array[i]);
		}
		return hash;
	}

	/**
	 * 返回给定boolean数组的hashcode，基于数组中元素的hashcode计算 如果 <code>array</code> 为
	 * <code>null</code>, 本方法返回 0.
	 * 
	 * @param array
	 *            给定boolean数组的
	 * @return 给定boolean数组的hashcode
	 */
	public static int nullSafeHashCode(boolean[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * 返回给定byte对象数组的hashcode，基于数组中元素的hashcode计算 如果 <code>array</code> 为
	 * <code>null</code>, 本方法返回 0.
	 * 
	 * @param array
	 *            给定byte对象数组
	 * @return 给定byte对象数组的hashcode
	 */
	public static int nullSafeHashCode(byte[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * 返回给定char数组的hashcode，基于数组中元素的hashcode计算 如果 <code>array</code> 为
	 * <code>null</code>, 本方法返回 0.
	 * 
	 * @param array
	 *            给定char数组
	 * @return 给定char数组的hashcode
	 */
	public static int nullSafeHashCode(char[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * 返回给定double数组的hashcode，基于数组中元素的hashcode计算 如果 <code>array</code> 为
	 * <code>null</code>, 本方法返回 0.
	 */
	/**
	 * @param array
	 *            给定double数组
	 * @return 给定double数组的hashcode
	 */
	public static int nullSafeHashCode(double[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * 返回给定float数组的hashcode，基于数组中元素的hashcode计算 如果 <code>array</code> 为
	 * <code>null</code>, 本方法返回 0.
	 * 
	 * @param array
	 *            给定float数组
	 * @return 给定float数组的hashcode
	 */
	public static int nullSafeHashCode(float[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * 返回给定int数组的hashcode，基于数组中元素的hashcode计算 如果 <code>array</code> 为
	 * <code>null</code>, 本方法返回 0.
	 * 
	 * @param array
	 *            给定int数组
	 * @return 给定int数组的hashcode
	 */
	public static int nullSafeHashCode(int[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * 返回给定long数组的hashcode，基于数组中元素的hashcode计算 如果 <code>array</code> 为
	 * <code>null</code>, 本方法返回 0.
	 * 
	 * @param array
	 *            给定long数组
	 * @return 给定long数组的hashcode
	 */
	public static int nullSafeHashCode(long[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * 返回给定short数组的hashcode，基于数组中元素的hashcode计算 如果 <code>array</code> 为
	 * <code>null</code>, 本方法返回 0.
	 * 
	 * @param array
	 *            给定short数组
	 * @return 给定short数组的hashcode
	 */
	public static int nullSafeHashCode(short[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * 返回boolean的hashcode，和 <code>{@link Boolean#hashCode()}</code>一致.
	 * 
	 * @param bool
	 *            boolean
	 * @return boolean的hashcode
	 * @see Boolean#hashCode()
	 */
	public static int hashCode(boolean bool) {
		return bool ? 1231 : 1237;
	}

	/**
	 * 返回double的hashcode <code>{@link Double#hashCode()}</code>.
	 * 
	 * @param dbl
	 *            double
	 * @return double的hashcode
	 * @see Double#hashCode()
	 */
	public static int hashCode(double dbl) {
		long bits = Double.doubleToLongBits(dbl);
		return hashCode(bits);
	}

	/**
	 * 返回float的hashcode <code>{@link Float#hashCode()}</code>.
	 * 
	 * @param flt
	 *            float
	 * @return float的hashcode
	 * @see Float#hashCode()
	 */
	public static int hashCode(float flt) {
		return Float.floatToIntBits(flt);
	}

	/**
	 * 返回long的hashcode <code>{@link Long#hashCode()}</code>.
	 * 
	 * @param lng
	 *            long
	 * @return long的hashcode
	 * @see Long#hashCode()
	 */
	public static int hashCode(long lng) {
		return (int) (lng ^ (lng >>> 32));
	}

	// ---------------------------------------------------------------------
	// Convenience methods for toString output
	// ---------------------------------------------------------------------

	/**
	 * 返回对象的标识，通过类名和hashcode标识。
	 * 
	 * @param obj
	 *            传入的对象 (可以为 <code>null</code>)
	 * @return the 对象的标识。 如果对象为 <code>null</code>，返回 “”；
	 */
	public static String identityToString(Object obj) {
		if (obj == null) {
			return EMPTY_STRING;
		}
		return obj.getClass().getName() + "@" + getIdentityHexString(obj);
	}

	/**
	 * 返回16进制的hashcode标识
	 * 
	 * @param obj
	 *            传入的对象
	 * @return 16进制的hashcode标识
	 */
	public static String getIdentityHexString(Object obj) {
		return Integer.toHexString(System.identityHashCode(obj));
	}

	/**
	 * Return a content-based String representation if <code>obj</code> is not
	 * <code>null</code>; otherwise returns an empty String.
	 * <p>
	 * Differs from {@link #nullSafeToString(Object)} in that it returns an
	 * empty String rather than "null" for a <code>null</code> value.
	 * 
	 * @param obj
	 *            the object to build a display String for
	 * @return a display String representation of <code>obj</code>
	 * @see #nullSafeToString(Object)
	 */
	public static String getDisplayString(Object obj) {
		if (obj == null) {
			return EMPTY_STRING;
		}
		return nullSafeToString(obj);
	}

	/**
	 * 返回给定对象的类名。
	 * <p>
	 * 返回 <code>"null"</code> 如果 <code>obj</code> 为 <code>null</code>.
	 * 
	 * @param obj
	 *            需要检查的对象 (可以为 <code>null</code>)
	 * @return 相应的类名
	 */
	public static String nullSafeClassName(Object obj) {
		return (obj != null ? obj.getClass().getName() : NULL_STRING);
	}

	/**
	 * 返回给定对象的String标识
	 * <p>
	 * 如果传入对象为array，基于数组的内容构建String 返回 <code>"null"</code> 如果 <code>obj</code> 为
	 * <code>null</code>.
	 * 
	 * @param obj
	 *            传入的对象
	 * @return <code>obj</code>的String标识
	 */
	public static String nullSafeToString(Object obj) {
		if (obj == null) {
			return NULL_STRING;
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		if (obj instanceof Object[]) {
			return nullSafeToString((Object[]) obj);
		}
		if (obj instanceof boolean[]) {
			return nullSafeToString((boolean[]) obj);
		}
		if (obj instanceof byte[]) {
			return nullSafeToString((byte[]) obj);
		}
		if (obj instanceof char[]) {
			return nullSafeToString((char[]) obj);
		}
		if (obj instanceof double[]) {
			return nullSafeToString((double[]) obj);
		}
		if (obj instanceof float[]) {
			return nullSafeToString((float[]) obj);
		}
		if (obj instanceof int[]) {
			return nullSafeToString((int[]) obj);
		}
		if (obj instanceof long[]) {
			return nullSafeToString((long[]) obj);
		}
		if (obj instanceof short[]) {
			return nullSafeToString((short[]) obj);
		}
		String str = obj.toString();
		return (str != null ? str : EMPTY_STRING);
	}

	/**
	 * 返回特定数组的String 打印，基于数组的内容构建。 类似：
	 * <p>
	 * {a1,a2,a3}
	 * <p>
	 * 返回<code>"null"</code> 如果 <code>array</code> 是 <code>null</code>.
	 * 
	 * @param array
	 *            需要构建的数组
	 * @return <code>array</code>代表的String打印
	 */
	public static String nullSafeToString(Object[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append(String.valueOf(array[i]));
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	/**
	 * 返回特定数组的String 打印，基于数组的内容构建。 类似：
	 * <p>
	 * {a1,a2,a3}
	 * <p>
	 * 返回<code>"null"</code> 如果 <code>array</code> 是 <code>null</code>.
	 * 
	 * @param array
	 *            需要构建的数组
	 * @return <code>array</code>代表的String打印
	 */
	public static String nullSafeToString(boolean[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}

			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	/**
	 * 返回特定数组的String 打印，基于数组的内容构建。 类似：
	 * <p>
	 * {a1,a2,a3}
	 * <p>
	 * 返回<code>"null"</code> 如果 <code>array</code> 是 <code>null</code>.
	 * 
	 * @param array
	 *            需要构建的数组
	 * @return <code>array</code>代表的String打印
	 */
	public static String nullSafeToString(byte[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	/**
	 * 返回特定数组的String 打印，基于数组的内容构建。 类似：
	 * <p>
	 * {a1,a2,a3}
	 * <p>
	 * 返回<code>"null"</code> 如果 <code>array</code> 是 <code>null</code>.
	 * 
	 * @param array
	 *            需要构建的数组
	 * @return <code>array</code>代表的String打印
	 */
	public static String nullSafeToString(char[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append('\'').append(array[i]).append('\'');
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	/**
	 * 返回特定数组的String 打印，基于数组的内容构建。 类似：
	 * <p>
	 * {a1,a2,a3}
	 * <p>
	 * 返回<code>"null"</code> 如果 <code>array</code> 是 <code>null</code>.
	 * 
	 * @param array
	 *            需要构建的数组
	 * @return <code>array</code>代表的String打印
	 */
	public static String nullSafeToString(double[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}

			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	/**
	 * 返回特定数组的String 打印，基于数组的内容构建。 类似：
	 * <p>
	 * {a1,a2,a3}
	 * <p>
	 * 返回<code>"null"</code> 如果 <code>array</code> 是 <code>null</code>.
	 * 
	 * @param array
	 *            需要构建的数组
	 * @return <code>array</code>代表的String打印
	 */
	public static String nullSafeToString(float[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}

			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	/**
	 * 返回特定数组的String 打印，基于数组的内容构建。 类似：
	 * <p>
	 * {a1,a2,a3}
	 * <p>
	 * 返回<code>"null"</code> 如果 <code>array</code> 是 <code>null</code>.
	 * 
	 * @param array
	 *            需要构建的数组
	 * @return <code>array</code>代表的String打印
	 */
	public static String nullSafeToString(int[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	/**
	 * 返回特定数组的String 打印，基于数组的内容构建。 类似：
	 * <p>
	 * {a1,a2,a3}
	 * <p>
	 * 返回<code>"null"</code> 如果 <code>array</code> 是 <code>null</code>.
	 * 
	 * @param array
	 *            需要构建的数组
	 * @return <code>array</code>代表的String打印
	 */
	public static String nullSafeToString(long[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	/**
	 * 返回特定数组的String 打印，基于数组的内容构建。 类似：
	 * <p>
	 * {a1,a2,a3}
	 * <p>
	 * 返回<code>"null"</code> 如果 <code>array</code> 是 <code>null</code>.
	 * 
	 * @param array
	 *            需要构建的数组
	 * @return <code>array</code>代表的String打印
	 */
	public static String nullSafeToString(short[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

}
