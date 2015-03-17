/**
 * Copyright 2009 Datang Mobile Co.,Ltd. All rights reserved.
 * DTM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.datang.miou.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 各类的 {@link String} 工具方法.
 * 
 * <p>
 * 主要用于OP Framwork内部; 参考 <a href="http://jakarta.apache.org/commons/lang/">Jakarta's Commons Lang</a>
 * 
 * <p>
 * 本类补充了JDK String 中方法的不足 本类还提供了很多便利方法用于转化分隔符分割的String，例如CSV String等
 * 
 * @author Lance Liao
 * @since 1.0.0
 * @see org.apache.commons.lang.StringUtils
 */
public abstract class StringUtils {

	private static final String FOLDER_SEPARATOR = "/";

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	private static final String TOP_PATH = "..";

	private static final String CURRENT_PATH = ".";

	private static final char EXTENSION_SEPARATOR = '.';

	// ---------------------------------------------------------------------
	// General convenience methods for working with Strings
	// ---------------------------------------------------------------------
	public static final SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
	public static final DecimalFormat NumberFormat = new DecimalFormat("######.##");

	public static String decomalFormat(double data) {
		return NumberFormat.format(data);
	}

	public static String dateFormat(Date date) {
		return DateFormat.format(date);
	}

	/**
	 * 转换速率
	 * 
	 * @param speed
	 * @return
	 */
	public static String speedFormat(double speed) {
		DecimalFormat df = null;
		double kbps = (speed * 8) / 1024;
		if (kbps > 1024) {
			df = new DecimalFormat("######.##");
			return df.format(kbps / 1024) + "Mbps";
		}
		df = new DecimalFormat("######");
		return df.format(kbps) + "Kbps";
	}

	/**
	 * 转换大小
	 * 
	 * @param total
	 * @return
	 */
	public static String sizeFormat(double total) {
		DecimalFormat df = null;
		double kbps = total / 1024;
		if (kbps > 1024) {
			df = new DecimalFormat("#########.##");
			return df.format(kbps / 1024) + "MB";
		}
		df = new DecimalFormat("#########");
		return df.format(kbps) + "KB";
	}

	/**
	 * Check that the given CharSequence is neither <code>null</code> nor of length 0. Note: Will return <code>true</code> for a CharSequence that purely consists of whitespace.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength(&quot;&quot;) = false
	 * StringUtils.hasLength(&quot; &quot;) = true
	 * StringUtils.hasLength(&quot;Hello&quot;) = true
	 * </pre>
	 * 
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * Check that the given String is neither <code>null</code> nor of length 0. Note: Will return <code>true</code> for a String that purely consists of whitespace.
	 * 
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 * @see #hasLength(CharSequence)
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	/**
	 * Check whether the given CharSequence has actual text. More specifically, returns <code>true</code> if the string not <code>null</code>, its length is greater than 0, and it contains
	 * at least one non-whitespace character.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText(&quot;&quot;) = false
	 * StringUtils.hasText(&quot; &quot;) = false
	 * StringUtils.hasText(&quot;12345&quot;) = true
	 * StringUtils.hasText(&quot; 12345 &quot;) = true
	 * </pre>
	 * 
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not <code>null</code>, its length is greater than 0, and it does not contain whitespace only
	 * @see Character#isWhitespace
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given String has actual text. More specifically, returns <code>true</code> if the string not <code>null</code>, its length is greater than 0, and it contains at
	 * least one non-whitespace character.
	 * 
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not <code>null</code>, its length is greater than 0, and it does not contain whitespace only
	 * @see #hasText(CharSequence)
	 */
	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	/**
	 * Check whether the given CharSequence contains any whitespace characters.
	 * 
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not empty and contains at least 1 whitespace character
	 * @see Character#isWhitespace
	 */
	public static boolean containsWhitespace(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given String contains any whitespace characters.
	 * 
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not empty and contains at least 1 whitespace character
	 * @see #containsWhitespace(CharSequence)
	 */
	public static boolean containsWhitespace(String str) {
		return containsWhitespace((CharSequence) str);
	}

	/**
	 * Trim leading and trailing whitespace from the given String.
	 * 
	 * @param str the String to check
	 * @return the trimmed String
	 * @see Character#isWhitespace
	 */
	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Trim <i>all</i> whitespace from the given String: leading, trailing, and inbetween characters.
	 * 
	 * @param str the String to check
	 * @return the trimmed String
	 * @see Character#isWhitespace
	 */
	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		int index = 0;
		while (buf.length() > index) {
			if (Character.isWhitespace(buf.charAt(index))) {
				buf.deleteCharAt(index);
			} else {
				index++;
			}
		}
		return buf.toString();
	}

	/**
	 * Trim leading whitespace from the given String.
	 * 
	 * @param str the String to check
	 * @return the trimmed String
	 * @see Character#isWhitespace
	 */
	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	/**
	 * Trim trailing whitespace from the given String.
	 * 
	 * @param str the String to check
	 * @return the trimmed String
	 * @see Character#isWhitespace
	 */
	public static String trimTrailingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Trim all occurences of the supplied leading character from the given String.
	 * 
	 * @param str the String to check
	 * @param leadingCharacter the leading character to be trimmed
	 * @return the trimmed String
	 */
	public static String trimLeadingCharacter(String str, char leadingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && buf.charAt(0) == leadingCharacter) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	/**
	 * Trim all occurences of the supplied trailing character from the given String.
	 * 
	 * @param str the String to check
	 * @param trailingCharacter the trailing character to be trimmed
	 * @return the trimmed String
	 */
	public static String trimTrailingCharacter(String str, char trailingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && buf.charAt(buf.length() - 1) == trailingCharacter) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Test if the given String starts with the specified prefix, ignoring upper/lower case.
	 * 
	 * @param str the String to check
	 * @param prefix the prefix to look for
	 * @see String#startsWith
	 * @return if start with return true, or return false
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		// if (str == null || prefix == null) {
		// return false;
		// }
		// if (str.startsWith(prefix)) {
		// return true;
		// }
		// if (str.length() < prefix.length()) {
		// return false;
		// }
		// String lcStr = str.substring(0, prefix.length());
		// return lcStr.equalsIgnoreCase(prefix);
		// modify by zsj
		if (str == null || prefix == null) {
			return false;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		return str.regionMatches(true, 0, prefix, 0, prefix.length());
	}

	/**
	 * Test if the given String ends with the specified suffix, ignoring upper/lower case.
	 * 
	 * @param str the String to check
	 * @param suffix the suffix to look for
	 * @see String#endsWith
	 * @return if end with the specified suffix return true, or return false
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}

		String lcStr = str.substring(str.length() - suffix.length());
		return lcStr.equalsIgnoreCase(suffix);
	}

	/**
	 * Test whether the given string matches the given substring at the given index.
	 * 
	 * @param str the original string (or StringBuffer)
	 * @param index the index in the original string to start matching against
	 * @param substring the substring to match at the given index
	 * @return if matche, return true or return false
	 */
	public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
		for (int j = 0; j < substring.length(); j++) {
			int i = index + j;
			if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Count the occurrences of the substring in string s.
	 * 
	 * @param str string to search in. Return 0 if this is null.
	 * @param sub string to search for. Return 0 if this is null.
	 * @return the number of occurrences
	 */
	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0, pos = 0, idx = 0;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	/**
	 * Replace all occurences of a substring within a string with another string.
	 * 
	 * @param inString String to examine
	 * @param oldPattern String to replace
	 * @param newPattern String to insert
	 * @return a String with the replacements
	 */
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
			return inString;
		}
		StringBuffer sbuf = new StringBuffer();
		// output StringBuffer we'll build up
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));
		// remember to append any characters to the right of a match
		return sbuf.toString();
	}

	/**
	 * Delete all occurrences of the given substring.
	 * 
	 * @param inString the original String
	 * @param pattern the pattern to delete all occurrences of
	 * @return the resulting String
	 */
	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	/**
	 * Delete any character in a given String.
	 * 
	 * @param inString the original String
	 * @param charsToDelete a set of characters to delete. E.g. "az\n" will delete 'a's, 'z's and new lines.
	 * @return the resulting String
	 */
	public static String deleteAny(String inString, String charsToDelete) {
		if (!hasLength(inString) || !hasLength(charsToDelete)) {
			return inString;
		}
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				out.append(c);
			}
		}
		return out.toString();
	}

	// ---------------------------------------------------------------------
	// Convenience methods for working with formatted Strings
	// ---------------------------------------------------------------------

	/**
	 * Quote the given String with single quotes.
	 * 
	 * @param str the input String (e.g. "myString")
	 * @return the quoted String (e.g. "'myString'"), or <code>null<code> if the input was <code>null</code>
	 */
	public static String quote(String str) {
		return (str != null ? "'" + str + "'" : null);
	}

	/**
	 * Turn the given Object into a String with single quotes if it is a String; keeping the Object as-is else.
	 * 
	 * @param obj the input Object (e.g. "myString")
	 * @return the quoted String (e.g. "'myString'"), or the input object as-is if not a String
	 */
	public static Object quoteIfString(Object obj) {
		return (obj instanceof String ? quote((String) obj) : obj);
	}

	/**
	 * Unqualify a string qualified by a '.' dot character. For example, "this.name.is.qualified", returns "qualified".
	 * 
	 * @param qualifiedName the qualified name
	 * @return Unqualified string
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	/**
	 * Unqualify a string qualified by a separator character. For example, "this:name:is:qualified" returns "qualified" if using a ':' separator.
	 * 
	 * @param qualifiedName the qualified name
	 * @param separator the separator
	 * @return Unqualified string
	 */
	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	/**
	 * Capitalize a <code>String</code>, changing the first letter to upper case as per {@link Character#toUpperCase(char)}. No other letters are changed.
	 * 
	 * @param str the String to capitalize, may be <code>null</code>
	 * @return the capitalized String, <code>null</code> if null
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	/**
	 * Uncapitalize a <code>String</code>, changing the first letter to lower case as per {@link Character#toLowerCase(char)}. No other letters are changed.
	 * 
	 * @param str the String to uncapitalize, may be <code>null</code>
	 * @return the uncapitalized String, <code>null</code> if null
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}

	/**
	 * Extract the filename from the given path, e.g. "mypath/myfile.txt" -> "myfile.txt".
	 * 
	 * @param path the file path (may be <code>null</code>)
	 * @return the extracted filename, or <code>null</code> if none
	 */
	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
	}

	/**
	 * Extract the filename extension from the given path, e.g. "mypath/myfile.txt" -> "txt".
	 * 
	 * @param path the file path (may be <code>null</code>)
	 * @return the extracted filename extension, or <code>null</code> if none
	 */
	public static String getFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(sepIndex + 1) : null);
	}

	/**
	 * Strip the filename extension from the given path, e.g. "mypath/myfile.txt" -> "mypath/myfile".
	 * 
	 * @param path the file path (may be <code>null</code>)
	 * @return the path with stripped filename extension, or <code>null</code> if none
	 */
	public static String stripFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
	}

	/**
	 * Apply the given relative path to the given path, assuming standard Java folder separation (i.e. "/" separators);
	 * 
	 * @param path the path to start from (usually a full file path)
	 * @param relativePath the relative path to apply (relative to the full file path above)
	 * @return the full file path that results from applying the relative path
	 */
	public static String applyRelativePath(String path, String relativePath) {
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		if (separatorIndex != -1) {
			String newPath = path.substring(0, separatorIndex);
			if (!relativePath.startsWith(FOLDER_SEPARATOR)) {
				newPath += FOLDER_SEPARATOR; // NOPMD
			}
			return newPath + relativePath;
		} else {
			return relativePath;
		}
	}

	/**
	 * Normalize the path by suppressing sequences like "path/.." and inner simple dots.
	 * <p>
	 * The result is convenient for path comparison. For other uses, notice that Windows separators ("\") are replaced by simple slashes.
	 * 
	 * @param path the original path
	 * @return the normalized path
	 */
	public static String cleanPath(String path) {
		if (path == null) {
			return null;
		}
		String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);

		// Strip prefix from path to analyze, to not treat it as part of the
		// first path element. This is necessary to correctly parse paths like
		// "file:core/../core/io/Resource.class", where the ".." should just
		// strip the first "core" directory while keeping the "file:" prefix.
		int prefixIndex = pathToUse.indexOf(':');
		String prefix = "";
		if (prefixIndex != -1) {
			prefix = pathToUse.substring(0, prefixIndex + 1);
			pathToUse = pathToUse.substring(prefixIndex + 1);
		}
		if (pathToUse.startsWith(FOLDER_SEPARATOR)) {
			prefix = prefix + FOLDER_SEPARATOR; // NOPMD
			pathToUse = pathToUse.substring(1);
		}

		String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);
		List<String> pathElements = new LinkedList<String>();
		int tops = 0;

		for (int i = pathArray.length - 1; i >= 0; i--) {
			String element = pathArray[i];
			if (CURRENT_PATH.equals(element)) { // NOPMD
				// Points to current directory - drop it.
			} else if (TOP_PATH.equals(element)) {
				// Registering top path found.
				tops++;
			} else {
				if (tops > 0) {
					// Merging path element with element corresponding to top
					// path.
					tops--;
				} else {
					// Normal path element found.
					pathElements.add(0, element);
				}
			}
		}

		// Remaining top paths need to be retained.
		for (int i = 0; i < tops; i++) {
			pathElements.add(0, TOP_PATH);
		}

		return prefix + collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
	}

	/**
	 * Compare two paths after normalization of them.
	 * 
	 * @param path1 first path for comparison
	 * @param path2 second path for comparison
	 * @return whether the two paths are equivalent after normalization
	 */
	public static boolean pathEquals(String path1, String path2) {
		return cleanPath(path1).equals(cleanPath(path2));
	}

	/**
	 * Parse the given <code>localeString</code> into a {@link java.util.Locale}.
	 * <p>
	 * This is the inverse operation of {@link java.util.Locale#toString Locale's toString}.
	 * 
	 * @param localeString the locale string, following <code>Locale's</code> <code>toString()</code> format ("en", "en_UK", etc); also accepts spaces as separators, as an alternative to
	 *        underscores
	 * @return a corresponding <code>Locale</code> instance
	 */
	public static Locale parseLocaleString(String localeString) {
		String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
		String language = (parts.length > 0 ? parts[0] : "");
		String country = (parts.length > 1 ? parts[1] : "");
		String variant = "";
		if (parts.length >= 2) {
			// There is definitely a variant, and it is everything after the
			// country
			// code sans the separator between the country code and the variant.
			int endIndexOfCountryCode = localeString.indexOf(country) + country.length();
			// Strip off any leading '_' and whitespace, what's left is the
			// variant.
			variant = trimLeadingWhitespace(localeString.substring(endIndexOfCountryCode));
			if (variant.charAt(0) == '_') {
				variant = trimLeadingCharacter(variant, '_');
			}
		}
		return (language.length() > 0 ? new Locale(language, country, variant) : null);
	}

	/**
	 * Determine the RFC 3066 compliant language tag, as used for the HTTP "Accept-Language" header.
	 * 
	 * @param locale the Locale to transform to a language tag
	 * @return the RFC 3066 compliant language tag as String
	 */
	public static String toLanguageTag(Locale locale) {
		return locale.getLanguage() + (hasText(locale.getCountry()) ? "-" + locale.getCountry() : "");
	}

	// ---------------------------------------------------------------------
	// Convenience methods for working with String arrays
	// ---------------------------------------------------------------------

	/**
	 * Append the given String to the given String array, returning a new array consisting of the input array contents plus the given String.
	 * 
	 * @param array the array to append to (can be <code>null</code>)
	 * @param str the String to append
	 * @return the new array (never <code>null</code>)
	 */
	public static String[] addStringToArray(String[] array, String str) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[] { str };
		}
		String[] newArr = new String[array.length + 1];
		System.arraycopy(array, 0, newArr, 0, array.length);
		newArr[array.length] = str;
		return newArr;
	}

	/**
	 * Concatenate the given String arrays into one, with overlapping array elements included twice.
	 * <p>
	 * The order of elements in the original arrays is preserved.
	 * 
	 * @param array1 the first array (can be <code>null</code>)
	 * @param array2 the second array (can be <code>null</code>)
	 * @return the new array (<code>null</code> if both given arrays were <code>null</code>)
	 */
	public static String[] concatenateStringArrays(String[] array1, String[] array2) {
		if (ObjectUtils.isEmpty(array1)) {
			return array2;
		}
		if (ObjectUtils.isEmpty(array2)) {
			return array1;
		}
		String[] newArr = new String[array1.length + array2.length];
		System.arraycopy(array1, 0, newArr, 0, array1.length);
		System.arraycopy(array2, 0, newArr, array1.length, array2.length);
		return newArr;
	}

	/**
	 * Merge the given String arrays into one, with overlapping array elements only included once.
	 * <p>
	 * The order of elements in the original arrays is preserved (with the exception of overlapping elements, which are only included on their first occurence).
	 * 
	 * @param array1 the first array (can be <code>null</code>)
	 * @param array2 the second array (can be <code>null</code>)
	 * @return the new array (<code>null</code> if both given arrays were <code>null</code>)
	 */
	public static String[] mergeStringArrays(String[] array1, String[] array2) {
		if (ObjectUtils.isEmpty(array1)) {
			return array2;
		}
		if (ObjectUtils.isEmpty(array2)) {
			return array1;
		}
		List<String> result = new ArrayList<String>();
		result.addAll(Arrays.asList(array1));
		for (int i = 0; i < array2.length; i++) {
			String str = array2[i];
			if (!result.contains(str)) {
				result.add(str);
			}
		}
		return toStringArray(result);
	}

	/**
	 * Turn given source String array into sorted array.
	 * 
	 * @param array the source array
	 * @return the sorted array (never <code>null</code>)
	 */
	public static String[] sortStringArray(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[0];
		}
		Arrays.sort(array);
		return array;
	}

	/**
	 * Copy the given Collection into a String array. The Collection must contain String elements only.
	 * 
	 * @param collection the Collection to copy
	 * @return the String array (<code>null</code> if the passed-in Collection was <code>null</code>)
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return (String[]) collection.toArray(new String[collection.size()]);
	}

	/**
	 * Copy the given Enumeration into a String array. The Enumeration must contain String elements only.
	 * 
	 * @param enumeration the Enumeration to copy
	 * @return the String array (<code>null</code> if the passed-in Enumeration was <code>null</code>)
	 */
	public static String[] toStringArray(Enumeration<String> enumeration) {
		if (enumeration == null) {
			return null;
		}
		List<String> list = Collections.list(enumeration);
		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * Trim the elements of the given String array, calling <code>String.trim()</code> on each of them.
	 * 
	 * @param array the original String array
	 * @return the resulting array (of the same size) with trimmed elements
	 */
	public static String[] trimArrayElements(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[0];
		}
		String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			result[i] = (element != null ? element.trim() : null);
		}
		return result;
	}

	/**
	 * Remove duplicate Strings from the given array. Also sorts the array, as it uses a TreeSet.
	 * 
	 * @param array the String array
	 * @return an array without duplicates, in natural sort order
	 */
	public static String[] removeDuplicateStrings(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return array;
		}
		Set<String> set = new TreeSet<String>();
		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}
		return toStringArray(set);
	}

	/**
	 * Split a String at the first occurrence of the delimiter. Does not include the delimiter in the result.
	 * 
	 * @param toSplit the string to split
	 * @param delimiter to split the string up with
	 * @return a two element array with index 0 being before the delimiter, and index 1 being after the delimiter (neither element includes the delimiter); or <code>null</code> if the
	 *         delimiter wasn't found in the given input String
	 */
	public static String[] split(String toSplit, String delimiter) {
		if (!hasLength(toSplit) || !hasLength(delimiter)) {
			return null;
		}
		int offset = toSplit.indexOf(delimiter);
		if (offset < 0) {
			return null;
		}
		String beforeDelimiter = toSplit.substring(0, offset);
		String afterDelimiter = toSplit.substring(offset + delimiter.length());
		return new String[] { beforeDelimiter, afterDelimiter };
	}

	/**
	 * Take an array Strings and split each element based on the given delimiter. A <code>Properties</code> instance is then generated, with the left of the delimiter providing the key, and
	 * the right of the delimiter providing the value.
	 * <p>
	 * Will trim both the key and value before adding them to the <code>Properties</code> instance.
	 * 
	 * @param array the array to process
	 * @param delimiter to split each element using (typically the equals symbol)
	 * @return a <code>Properties</code> instance representing the array contents, or <code>null</code> if the array to process was null or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
		return splitArrayElementsIntoProperties(array, delimiter, null);
	}

	/**
	 * Take an array Strings and split each element based on the given delimiter. A <code>Properties</code> instance is then generated, with the left of the delimiter providing the key, and
	 * the right of the delimiter providing the value.
	 * <p>
	 * Will trim both the key and value before adding them to the <code>Properties</code> instance.
	 * 
	 * @param array the array to process
	 * @param delimiter to split each element using (typically the equals symbol)
	 * @param charsToDelete one or more characters to remove from each element prior to attempting the split operation (typically the quotation mark symbol), or <code>null</code> if no
	 *        removal should occur
	 * @return a <code>Properties</code> instance representing the array contents, or <code>null</code> if the array to process was <code>null</code> or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, String charsToDelete) {

		if (ObjectUtils.isEmpty(array)) {
			return null;
		}
		Properties result = new Properties();
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			if (charsToDelete != null) {
				element = deleteAny(array[i], charsToDelete);
			}
			String[] splittedElement = split(element, delimiter);
			if (splittedElement == null) {
				continue;
			}
			result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
		}
		return result;
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer. Trims tokens and omits empty tokens.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of delimiter characters. Each of those characters can be used to separate tokens. A delimiter is always a single
	 * character; for multi-character delimiters, consider using <code>delimitedListToStringArray</code>
	 * 
	 * @param str the String to tokenize
	 * @param delimiters the delimiter characters, assembled as String (each of those characters is individually considered as delimiter).
	 * @return an array of the tokens
	 * @see java.util.StringTokenizer
	 * @see String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of delimiter characters. Each of those characters can be used to separate tokens. A delimiter is always a single
	 * character; for multi-character delimiters, consider using <code>delimitedListToStringArray</code>
	 * 
	 * @param str the String to tokenize
	 * @param delimiters the delimiter characters, assembled as String (each of those characters is individually considered as delimiter)
	 * @param trimTokens trim the tokens via String's <code>trim</code>
	 * @param ignoreEmptyTokens omit empty tokens from the result array (only applies to tokens that are empty after trimming; StringTokenizer will not consider subsequent delimiters as
	 *        token in the first place).
	 * @return an array of the tokens (<code>null</code> if the input String was <code>null</code>)
	 * @see java.util.StringTokenizer
	 * @see String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	/**
	 * Take a String which is a delimited list and convert it to a String array.
	 * <p>
	 * A single delimiter can consists of more than one character: It will still be considered as single delimiter string, rather than as bunch of potential delimiter characters - in
	 * contrast to <code>tokenizeToStringArray</code>.
	 * 
	 * @param str the input String
	 * @param delimiter the delimiter between elements (this is a single delimiter, rather than a bunch individual delimiter characters)
	 * @return an array of the tokens in the list
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(String str, String delimiter) {
		return delimitedListToStringArray(str, delimiter, null);
	}

	/**
	 * Take a String which is a delimited list and convert it to a String array.
	 * <p>
	 * A single delimiter can consists of more than one character: It will still be considered as single delimiter string, rather than as bunch of potential delimiter characters - in
	 * contrast to <code>tokenizeToStringArray</code>.
	 * 
	 * @param str the input String
	 * @param delimiter the delimiter between elements (this is a single delimiter, rather than a bunch individual delimiter characters)
	 * @param charsToDelete a set of characters to delete. Useful for deleting unwanted line breaks: e.g. "\r\n\f" will delete all new lines and line feeds in a String.
	 * @return an array of the tokens in the list
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
		if (str == null) {
			return new String[0];
		}
		if (delimiter == null) {
			return new String[] { str };
		}
		List<String> result = new ArrayList<String>();
		if ("".equals(delimiter)) {
			for (int i = 0; i < str.length(); i++) {
				result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
			}
		} else {
			int pos = 0;
			int delPos = 0;
			while ((delPos = str.indexOf(delimiter, pos)) != -1) {
				result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
				pos = delPos + delimiter.length();
			}
			if (str.length() > 0 && pos <= str.length()) {
				// Add rest of String, but not in case of empty input.
				result.add(deleteAny(str.substring(pos), charsToDelete));
			}
		}
		return toStringArray(result);
	}

	/**
	 * Convert a CSV list into an array of Strings.
	 * 
	 * @param str the input String
	 * @return an array of Strings, or the empty array in case of empty input
	 */
	public static String[] commaDelimitedListToStringArray(String str) {
		return delimitedListToStringArray(str, ",");
	}

	/**
	 * Convenience method to convert a CSV string list to a set. Note that this will suppress duplicates.
	 * 
	 * @param str the input String
	 * @return a Set of String entries in the list
	 */
	public static Set commaDelimitedListToSet(String str) {
		Set<String> set = new TreeSet<String>();
		String[] tokens = commaDelimitedListToStringArray(str);
		for (int i = 0; i < tokens.length; i++) {
			set.add(tokens[i]);
		}
		return set;
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV) String. E.g. useful for <code>toString()</code> implementations.
	 * 
	 * @param coll the Collection to display
	 * @param delim the delimiter to use (probably a ",")
	 * @param prefix the String to start each element with
	 * @param suffix the String to end each element with
	 * @return the delimited String
	 */
	public static String collectionToDelimitedString(Collection coll, String delim, String prefix, String suffix) {
		if (CollectionUtils.isEmpty(coll)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Iterator it = coll.iterator();
		while (it.hasNext()) {
			sb.append(prefix).append(it.next()).append(suffix);
			if (it.hasNext()) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV) String. E.g. useful for <code>toString()</code> implementations.
	 * 
	 * @param coll the Collection to display
	 * @param delim the delimiter to use (probably a ",")
	 * @return the delimited String
	 */
	public static String collectionToDelimitedString(Collection coll, String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}

	/**
	 * Convenience method to return a Collection as a CSV String. E.g. useful for <code>toString()</code> implementations.
	 * 
	 * @param coll the Collection to display
	 * @return the delimited String
	 */
	public static String collectionToCommaDelimitedString(Collection coll) {
		return collectionToDelimitedString(coll, ",");
	}

	/**
	 * Convenience method to return a String array as a delimited (e.g. CSV) String. E.g. useful for <code>toString()</code> implementations.
	 * 
	 * @param arr the array to display
	 * @param delim the delimiter to use (probably a ",")
	 * @return the delimited String
	 */
	public static String arrayToDelimitedString(Object[] arr, String delim) {
		if (ObjectUtils.isEmpty(arr)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a String array as a CSV String. E.g. useful for <code>toString()</code> implementations.
	 * 
	 * @param arr the array to display
	 * @return the delimited String
	 */
	public static String arrayToCommaDelimitedString(Object[] arr) {
		return arrayToDelimitedString(arr, ",");
	}

	/**
	 * 将两个路径合并起来
	 * 
	 * @param path 路径
	 * @param separator 分隔符
	 * @param file 文件
	 * @return 合并以后的文件路径
	 */
	public static String concat(String path, String separator, String file) {
		StringBuffer sb = new StringBuffer(path.length() + separator.length() + file.length());
		sb.append(path).append(separator);
		sb.append(file);
		return sb.toString();
	}

	/**
	 * 合并字符串
	 * 
	 * @param strs 字符串
	 * @return 合并以后的字符串
	 */
	public static String concat(String... strs) {
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 校验字符串是否合法。
	 * <p>
	 * 字符串只包含正则表达式所指定字符或者符合字符出现顺序的规则
	 * 
	 * <pre>
	 * // &quot;&circ;[a-zA-Z0-9_]*$&quot; 用于表示只包含大小写字母，数字和下划线，还可以为空
	 * validString(&quot;1gfsf&quot;, &quot;&circ;[a-zA-Z0-9_]*$&quot;);// true
	 * validString(&quot;&quot;, &quot;&circ;[a-zA-Z0-9_]*$&quot;);// true
	 * validString(&quot;1gf?sf&quot;, &quot;&circ;[a-zA-Z0-9_]*$&quot;);// false
	 * // &quot;&circ;[a-zA-Z0-9_]*$&quot; 用于表示只包含大小写字母，数字和下划线，还可以为空
	 * // 且长度不要超过4
	 * validString(&quot;afaf&quot;, &quot;&circ;[a-zA-Z0-9_]{0,4}$&quot;);// true
	 * validString(&quot;afafa&quot;, &quot;&circ;[a-zA-Z0-9_]{0,4}$&quot;);// false
	 * </pre>
	 * 
	 * @param validString 需要校验的字符串
	 * @param regex 正则表达式
	 * @return 如果合法返回true，否则返回false
	 */
	public static boolean validString(String validString, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(validString);
		return matcher.matches();
	}

	/**
	 * 判断字符串是不是含有中文或者其他复杂文字
	 * <p>
	 * 
	 * <pre>
	 * 
	 * isSimpleString(&quot;幸勇&quot;); // false
	 * isSimpleString(&quot;xingyong&quot;); // true
	 * 
	 * </pre>
	 * 
	 * @param string 要测试的字符串
	 * @return 如果不包含复杂文字就返回true，否则返回false
	 */
	public static boolean isSimpleString(String string) {
		return string.length() == string.getBytes().length;
	}

	/**
	 * 合并字符串
	 * 
	 * @param strings 字符串或者字符串数组
	 * @return 合并以后的字符串，如果输入参数为空，则返回空字符串
	 */
	public static String composeStrings(String... strings) {
		StringBuilder builder = new StringBuilder("");
		Assert.notNull(strings);
		for (String string : strings) {
			Assert.notNull(string);
			builder.append(string);
		}
		return builder.toString();
	}

	/**
	 * 比较两个字符串是否相等
	 * <p/>
	 * 如果 str1 == str2 == null return true
	 * 
	 * @param str1 字符串1
	 * @param str2 字符串2
	 * @return [true|false] 是否相等
	 */
	public static boolean equals(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}
		return str1.equals(str2);
	}

	/**
	 * <p>
	 * Gets the substring before the first occurrence of a separator. The separator is not returned.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty ("") string input will return the empty string. A <code>null</code> separator will return the input string.
	 * </p>
	 * 
	 * <p>
	 * If nothing is found, the string input is returned.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.substringBefore(null, *)      = null
	 * StringUtils.substringBefore(&quot;&quot;, *)        = &quot;&quot;
	 * StringUtils.substringBefore(&quot;abc&quot;, &quot;a&quot;)   = &quot;&quot;
	 * StringUtils.substringBefore(&quot;abcba&quot;, &quot;b&quot;) = &quot;a&quot;
	 * StringUtils.substringBefore(&quot;abc&quot;, &quot;c&quot;)   = &quot;ab&quot;
	 * StringUtils.substringBefore(&quot;abc&quot;, &quot;d&quot;)   = &quot;abc&quot;
	 * StringUtils.substringBefore(&quot;abc&quot;, &quot;&quot;)    = &quot;&quot;
	 * StringUtils.substringBefore(&quot;abc&quot;, null)  = &quot;abc&quot;
	 * </pre>
	 * 
	 * @param str the String to get a substring from, may be null
	 * @param separator the String to search for, may be null
	 * @return the substring before the first occurrence of the separator, <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringBefore(String str, String separator) {
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (separator.length() == 0) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * <p>
	 * Gets the substring after the first occurrence of a separator. The separator is not returned.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty ("") string input will return the empty string. A <code>null</code> separator will return the empty string if
	 * the input string is not <code>null</code>.
	 * </p>
	 * 
	 * <p>
	 * If nothing is found, the empty string is returned.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.substringAfter(null, *)      = null
	 * StringUtils.substringAfter(&quot;&quot;, *)        = &quot;&quot;
	 * StringUtils.substringAfter(*, null)      = &quot;&quot;
	 * StringUtils.substringAfter(&quot;abc&quot;, &quot;a&quot;)   = &quot;bc&quot;
	 * StringUtils.substringAfter(&quot;abcba&quot;, &quot;b&quot;) = &quot;cba&quot;
	 * StringUtils.substringAfter(&quot;abc&quot;, &quot;c&quot;)   = &quot;&quot;
	 * StringUtils.substringAfter(&quot;abc&quot;, &quot;d&quot;)   = &quot;&quot;
	 * StringUtils.substringAfter(&quot;abc&quot;, &quot;&quot;)    = &quot;abc&quot;
	 * </pre>
	 * 
	 * @param str the String to get a substring from, may be null
	 * @param separator the String to search for, may be null
	 * @return the substring after the first occurrence of the separator, <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringAfter(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	// Empty checks
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if a CharSequence is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty(&quot;&quot;)        = true
	 * StringUtils.isEmpty(&quot; &quot;)       = false
	 * StringUtils.isEmpty(&quot;bob&quot;)     = false
	 * StringUtils.isEmpty(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the CharSequence. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param cs the CharSequence to check, may be null
	 * @return <code>true</code> if the CharSequence is empty or null
	 * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
	 */
	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}
}
