package com.datang.miou.utils;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class SystemUtil {
	public static final String OSNAME = System.getProperty("os.name");
	public static final String OSARCH = System.getProperty("os.arch");
	public static final String SEPARATOR = System.getProperty("file.separator");
	public static final String USER_DIR = System.getProperty("user.dir");
	public static final String CURR_ENCODING = System
			.getProperty("file.encoding");
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	public static final String CRLF = LINE_SEPARATOR; // Carriage Return/Line
	// Feed
	public static final String CRLF_LINUX = "\n";
	public static final String CHARSET_UTF = "UTF-8";
	public static final String CHARSET_GBK = "GBK";
	public static final String CHARSET_GB2312 = "GB2312";
	public static final String CHARSET_GB18030 = "GB18030";
	public static final String CHARSET_UNICODE = "UNICODE";
	public static final String CHARSET_ISO88591 = "ISO-8859-1";

	public static boolean isWindows = false;
	public static boolean isHP_UX = false;
	public static boolean isSolaris = false;
	public static boolean isOS32bit = true;
	public static final int BUFF_SIZE = 4096;
	public static final int BUFF_SIZE_1024 = 1024;
	public static final String KEY_ALGORITHM_RSA = "RSA";
	public final static String KEY_ALGORITHM_DES = "DES";
	public static final String KEY_ALGORITHM_SHA1withRSA = "SHA1withRSA";
	public static final String KEY_SHA = "SHA";
	public static final String KEY_SHA1 = "SHA-1";
	public static final String KEY_MD5 = "MD5";
	public static final String KEY_HMAC_SHA256 = "HMACSHA256";
	public static final String KEY_HMAC_SHA1 = "HmacSHA1";
	public static final String CERTIFICATEFACTORY_X509 = "X.509";
	public static final char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	public static BigInteger bigInt1 = BigInteger.valueOf(1l);
	public static final String CONTENTTYPE_HTML = "text/html";
	public static final String CONTENTTYPE_JSON = "application/json";
	public static final String CONTENTTYPE_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

	public static final String CONTENTTYPE_OCTET_STREAM = "application/octet-stream";

	public static final String PROCOTOL_HTTP = "http";
	public static final String PROCOTOL_HTTPS = "https";
	public static final String COLON = ":";
	/**
	 * 字符串空格
	 */
	public static final String BLANK = " ";
	public static final String ENGLISH_PERIOD = ".";
	public static final String DIVIDING_LINE = "---------------------------------------";

	public static final int NEGATIVE_ONE = -1;
	public static final String EMPTY = "";
	public static final String SWING_DIALOG_RED = "<font color=\"red\"  style=\"font-weight:bold;\">%s</font>";
	public static final String SWING_DIALOG_BLUE = "<font color=\"blue\"  style=\"font-weight:bold;\">%s</font>";
	public static final String BR_HTML = "<br />";
	public static final String LABEL_BROWSE = "浏览";
	public static final String LABEL_PASTE = "粘贴";
	public static final String LABEL_CLEANUP = "清空";
	public static final String UNDERLINE = "_";
	static {
		if (SystemUtil.OSNAME.toLowerCase().contains("window")) {
			isWindows = true;
		}
		if (SystemUtil.OSNAME.toLowerCase().contains("hp-ux")) {
			isHP_UX = true;
		}
		if (SystemUtil.OSNAME.toLowerCase().contains("Solaris")) {
			isSolaris = true;
		}
		if (SystemUtil.OSARCH.contains("64")) {
			isOS32bit = false;
		}
	}

	/***
	 * the unit of file size
	 */
	public static final String []UNIT_SIZE={"B","KB","MB","GB"};
	private SystemUtil() {
		throw new Error("Don't let anyone instantiate this class.");
	}

	public static void copyFile(String resourceFileName, String targetFileName)
			throws IOException {
		File resourceFile = new File(resourceFileName);
		File targetFile = new File(targetFileName);
		if (!resourceFile.exists()) {
			System.out.println("[copyFile ]: resource file has not been found:"
					+ resourceFileName);
		}
		if (!resourceFile.isFile()) {
			System.out.println("[copyFile ]: directory can not be copyed:"
					+ resourceFileName);
		}

		if (targetFile.isDirectory()) {
			targetFile = new File(targetFile, resourceFile.getName());
		}

		FileInputStream resource = null;
		FileOutputStream target = null;
		try {
			resource = new FileInputStream(resourceFile);
			target = new FileOutputStream(targetFile);
			copyFile(resourceFile, targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resource != null) {
				resource.close();
			}
			if (target != null) {
				target.close();
			}
		}
	}

	/**
	 * 
	 * @param srcFile
	 *            :must be regular file,can not be folder;
	 * @param targetFile
	 *            :must be regular file,can not be folder;
	 * @throws java.io.IOException
	 */
	public static void copyFile(File srcFile, File targetFile)
			throws IOException {
		FileInputStream in = new FileInputStream(srcFile);

		FileOutputStream out = new FileOutputStream(targetFile);
		copyFile(in, out);

	}

	public static void copyFile(InputStream in, FileOutputStream target)
			throws IOException {
		// File targetFile = new File(targetFileName);
		// FileOutputStream target = null;
		// if (targetFile.isDirectory())
		// {
		// targetFile = new File(targetFile, simpleName);
		// }
		try {
			// target = new FileOutputStream(targetFile);
			byte[] buffer = new byte[BUFF_SIZE];
			int byteNum;

			while ((byteNum = in.read(buffer)) != NEGATIVE_ONE) {
				target.write(buffer, 0, byteNum);

			}
			System.out.println("[SystemUtil:copyFile]:file copy successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
			if (target != null) {
				target.close();
				target = null;
			}
		}
	}


	/**
	 * 
	 * @param fullpath
	 *            :/a/b/c/d
	 * @return d
	 */
	public static String getFileSimpleName(String fullpath) {
		// String parentDir = null;
		if (null == fullpath) {
			System.out.println("The first argument can not be null");
			return null;
		}
		// if (fullpath.contains("/")) {
		// parentDir = fullpath.substring(fullpath.lastIndexOf("/") + 1);
		// }
		return new File(fullpath).getName();
	}

	// public static void main(String[] args) throws IOException
	// {
	// copyFile("/home/whuang2/study/linux/study/c/main.exe", "/home/whuang2");
	// }
	public static String convertUTF2ISO(String oldName) {
		if (oldName == null) {
			return oldName;
		}
		try {
			return new String(oldName.getBytes(CHARSET_UTF), CHARSET_ISO88591);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String convertGBK2ISO(String input) {
		if (input == null) {
			return input;
		}
		try {
			return new String(input.getBytes(CHARSET_GBK), CHARSET_ISO88591);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * convert GBK to UTF-8
	 * 
	 * @param input
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static byte[] convertGBK2UTF(byte[] input)
			throws UnsupportedEncodingException {
		return new String(input, SystemUtil.CHARSET_GBK)
				.getBytes(SystemUtil.CHARSET_UTF);
	}

	/***
	 * convert from GBK to UTF-8
	 * 
	 * @param input
	 * @return
	 */
	public static String convertGBK2UTF(String input) {
		if (input == null) {
			return input;
		}
		try {
			return new String(input.getBytes(CHARSET_GBK), CHARSET_UTF);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] convertFromeGBK(byte[] input, String toCharset)
			throws UnsupportedEncodingException {
		return new String(input, SystemUtil.CHARSET_GBK).getBytes(toCharset);
	}

	/***
	 * convert utf-8 to gbk
	 * 
	 * @param input
	 * @return
	 */
	public static String convertUTF2GBK(String input) {
		if (input == null) {
			return input;
		}
		try {
			return new String(input.getBytes(CHARSET_UTF), CHARSET_GBK);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String convertISO2UTF(String oldName) {
		if (oldName == null) {
			return oldName;
		}
		try {
			return new String(oldName.getBytes(CHARSET_ISO88591), CHARSET_UTF);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String convertISO2GBK(String input) {
		if (input == null) {
			return input;
		}
		try {
			return new String(input.getBytes(CHARSET_ISO88591), CHARSET_GBK);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void printFilesSimpleName(File[] files) {
		for (File file : files) {
			System.out.println(file.getName());
		}
	}

	public static void printFilesFilePath(File[] files) {
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
		}
	}

	public static void printFilesFilePath(List<File> files) {
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
		}
	}

	/***
	 * 
	 * @param srcfile
	 *            : source file
	 * @param targfile
	 *            : target file
	 * @param inputCharset
	 *            : from charset
	 * @param outputCharset
	 *            : to charset
	 */
	public static void convertEncoding(File srcfile, File targfile,
			String inputCharset, String outputCharset) {
		FileInputStream fin = null;
		FileOutputStream fout = null;
		char[] cbuf = new char[BUFF_SIZE];
		int size_char;
		try {
			fin = new FileInputStream(srcfile);
			fout = new FileOutputStream(targfile);
			InputStreamReader isr = null;
			OutputStreamWriter osw = null;
			try {
				isr = new InputStreamReader(fin, inputCharset);
				osw = new OutputStreamWriter(fout, outputCharset);
				while ((size_char = isr.read(cbuf)) != NEGATIVE_ONE) {
					osw.write(cbuf, 0, size_char);
				}
				//
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					isr.close();
					osw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
				if (fout != null) {
					fout.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * delete the same one
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> guolv(List<String> list) {
		List<String> newlist = new ArrayList<String>();
		if (list != null && list.size() > 0) {
			newlist.add(list.get(0));
			for (int i = 1; i < list.size(); i++) {
				if (!newlist.contains(list.get(i))) {
					newlist.add(list.get(i));
				}
			}
		}
		return newlist;
	}


	/***
	 * Delete all spaces
	 * 
	 * @param input
	 * @return
	 */
	public static String deleteAllCRLF(String input) {
		return input.replaceAll("((\r\n)|\n)[\\s\t ]*", "").replaceAll(
				"^((\r\n)|\n)", "");
	}


	/***
	 * Use uniqueness of collection
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> guolv2(List<String> list) {
		Set<String> set = new HashSet<String>(list);
		return new ArrayList<String>(set);
	}

	/**
	 * delete the same one
	 * 
	 * @param list
	 * @return
	 */
	public static List<Integer> guolvInteger(List<Integer> list) {
		List<Integer> newlist = new ArrayList<Integer>();
		if (list != null && list.size() > 0) {
			newlist.add(list.get(0));
			for (int i = 1; i < list.size(); i++) {
				if (!newlist.contains(list.get(i))) {
					newlist.add(list.get(i));
				}
			}
		}
		return newlist;
	}

	public static List<Integer> guolvInteger2(List<Integer> list) {
		Set<Integer> set = new HashSet<Integer>(list);
		return new ArrayList<Integer>(set);
	}

	/**
	 * 字节数大于1，则返回true
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		return String.valueOf(c).getBytes().length > 1;
	}

	/**
	 * 判断str 中的所有字符是否全部是中文字符（包括中文的标点符号）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAllChinese(String str) {
		char[] cs = null;
		if (str.length() == 1) {
			cs = new char[1];
			cs[0] = str.charAt(0);
		} else {
			cs = str.toCharArray();
		}
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (!isChinese(c)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isHasChinses(String str) {
		String encodeName = "UTF-8";
		for (int i = 0; i < str.length(); i++) {
			try {
				String singleStr = str.substring(i, i + 1);
				int leng = getEncodeLength(singleStr, encodeName);
				// System.out.println(singleStr + "\t" + leng);
				if (leng == 9)// 表示是中文字符
				{
					// System.out.println("有中文");
					return true;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean isHasChinses2(String str) {
		String encodeName = "UTF-8";
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			try {
				char c = chars[i];
				int leng = getEncodeLength(c, encodeName);
				// System.out.println(singleStr + "\t" + leng);
				if (leng == 9)// 表示是中文字符
				{
					// System.out.println("有中文");
					return true;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static int getEncodeLength(String str, String encodeName)
			throws Exception, UnsupportedEncodingException {// 返回值为9 的话，则说明有中文。
		if (str.length() != 1) {
			throw new Exception("超过一个字符");
		}
		String encod = URLEncoder.encode(str, "UTF-8");
		return encod.length();
	}

	public static int getEncodeLength(char c, String encodeName)
			throws  UnsupportedEncodingException {// 返回值为9
		// 的话，则说明有中文。
		String encod = URLEncoder.encode(String.valueOf(c), "UTF-8");
		return encod.length();
	}

	/**
	 * 删除input字符串中的html格式
	 * 
	 * @param input
	 * @param length
	 *            显示的字符的个数
	 * @return
	 */
	public static String splitAndFilterString(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}


	public static boolean contains(List<Object> list, Object value) {
		if (list == null || list.size() == 0) {
			return false;
		} else {
			for (int i = 0; i < list.size(); i++) {
				String valueStr;
				if (value instanceof File) {
					valueStr = ((File) value).getName();
				} else {
					valueStr = value.toString();
				}
				Object obj = list.get(i);
				if (obj instanceof File) {
					if (list.contains(valueStr)
							|| ((File) obj).getName().toString()
									.equals(valueStr)) {
						return true;
					}
				} else {
					if (list.contains(valueStr)
							|| list.get(i).toString().equals(valueStr)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * minus Set
	 * 
	 * @param oldList
	 * @param list
	 * @return
	 */
	public static List<Object> getMinusSet(List oldList, List list) {
		List selectedList = null;
		if (oldList != null) {
			selectedList = new ArrayList<Object>();
			int leng = oldList.size();
			if (leng != 0) {
				for (int i = 0; i < leng; i++) {
					Object obj = oldList.get(i);
					if (!contains(list, obj)) {
						selectedList.add(obj);
					}
				}
			}
		}
		return selectedList;
	}

	public static List<File> getMinusSetFile(List oldList, List list) {
		List selectedList = null;
		if (oldList != null) {
			selectedList = new ArrayList<File>();
			int leng = oldList.size();
			if (leng != 0) {
				for (int i = 0; i < leng; i++) {
					Object obj = oldList.get(i);
					if (!contains(list, obj)) {
						selectedList.add(obj);
					}
				}
			}
		}
		return selectedList;
	}

	public static List<String> getMinusSetStr(List oldList, List list) {
		List selectedList = null;
		if (oldList != null) {
			selectedList = new ArrayList<Object>();
			int leng = oldList.size();
			if (leng != 0) {
				for (int i = 0; i < leng; i++) {
					Object obj = oldList.get(i);
					if (!contains(list, obj)) {
						selectedList.add(obj);
					}
				}
			}
		}
		return selectedList;
	}

	/**
	 * Get MD5 of one file:hex string,test OK!
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != NEGATIVE_ONE) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	/***
	 * Get MD5 of one file！test ok!
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getFileMD5(String filepath) {
		File file = new File(filepath);
		return getFileMD5(file);
	}

	/**
	 * MD5 encrypt,test ok
	 * 
	 * @param data
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(SystemUtil.KEY_MD5);
		md5.update(data);
		return md5.digest();
	}

	public static byte[] encryptMD5(String data) throws Exception {
		return encryptMD5(data.getBytes(SystemUtil.CHARSET_ISO88591));
	}

	/***
	 * compare two file by Md5
	 * 
	 * @param file1
	 * @param file2
	 * @return
	 */
	public static boolean isSameMd5(File file1, File file2) {
		String md5_1 = SystemUtil.getFileMD5(file1);
		String md5_2 = SystemUtil.getFileMD5(file2);
		return md5_1.equals(md5_2);
	}

	/***
	 * compare two file by Md5
	 * 
	 * @param filepath1
	 * @param filepath2
	 * @return
	 */
	public static boolean isSameMd5(String filepath1, String filepath2) {
		File file1 = new File(filepath1);
		File file2 = new File(filepath2);
		return isSameMd5(file1, file2);
	}

	/***
	 * the times target occur in <code>int[] ints</code>
	 * 
	 * @param ints
	 * @param target
	 * @return
	 */
	public static int count(int[] ints, int target) {
		int count = 0;
		for (int i = 0; i < ints.length; i++) {
			if (ints[i] == target) {
				count++;
			}
		}
		return count;
	}

	/***
	 * Ignore Case
	 * 
	 * @param strs
	 * @param target
	 * @return
	 */
	public static int count(String[] strs, String target) {
		int count = 0;
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equalsIgnoreCase(target)) {
				count++;
			}
		}
		return count;
	}

	/***
	 * Ignore Case
	 * 
	 * @param list
	 * @param target
	 * @return
	 */
	public static int count(List<String> list, String target) {
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equalsIgnoreCase(target)) {
				count++;
			}
		}
		return count;
	}

	// public static void printSet(Set<Integer>set ){
	// for(Iterator<Integer> it=set.iterator();it.hasNext();){
	// Integer age=it.next();
	// System.out.println(age);
	// }
	// }

	/***
	 * 
	 * @param list
	 */
	public static void printList(List<?> list, boolean isNewline,
			String delimiter) {
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if (isNewline) {
				System.out.println(obj);
			} else {
				System.out.print(obj + delimiter);
			}
		}
	}

	public static void printList(List<?> list, String delimiter) {
		printList(list, true, delimiter);
	}

	public static void printStrList(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	public static void printSet(Set<Object> set) {
		for (Iterator<Object> it = set.iterator(); it.hasNext();) {
			Object age = it.next();
			System.out.println(age);
		}
	}

	public static <T extends Serializable> T clone2(T obj) {
		T clonedObj = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.close();
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			clonedObj = (T) ois.readObject();
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clonedObj;
	}

	/***
	 * convert byte array to public key algorithm : RSA
	 * 
	 * @param keyBytes
	 *            byte[]
	 * @return RSAPublicKey
	 * @throws Exception
	 */
	public static PublicKey convert2PublicKey(byte[] keyBytes) throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory
				.getInstance(SystemUtil.KEY_ALGORITHM_RSA);// RSA
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		return publicKey;
	}

	/***
	 * 
	 * @param keyHexStr
	 *            : hex(16) string
	 * @return PublicKey
	 * @throws Exception
	 */
	public static PublicKey convert2PublicKey(String keyHexStr)
			throws Exception {
		byte[] keyBytes = hexStrToBytes(keyHexStr);
		return convert2PublicKey(keyBytes);
	}

	/**
	 * convert public key to hex(16) bit string
	 * 
	 * @param publicKey
	 * @return hex(16) bit string
	 */
	public static String convert4PublicKey(PublicKey publicKey) {
		return toHexString(publicKey.getEncoded());
	}

	public static PublicKey getPublicKey(InputStream in)
			throws CertificateException {
		CertificateFactory cf = CertificateFactory
				.getInstance(SystemUtil.CERTIFICATEFACTORY_X509);
		X509Certificate oCertServer = (X509Certificate) cf
				.generateCertificate(in);
		PublicKey pubKey = oCertServer.getPublicKey();
		return pubKey;
	}

	/***
	 * 
	 * @param file
	 * @return
	 * @throws java.security.cert.CertificateException
	 * @throws java.io.FileNotFoundException
	 */
	public static PublicKey getPublicKey(File file)
			throws CertificateException, FileNotFoundException {
		InputStream in = new FileInputStream(file);
		return getPublicKey(in);
	}

	/***
	 * 
	 * @param hex
	 *            :hex(16) bit string
	 * @return PublicKey
	 * @throws java.security.cert.CertificateException
	 */
	public static PublicKey getPublicKey(String hex)
			throws CertificateException {
		InputStream in = FileUtils.getByteArrayInputSream2hexString(hex);
		return getPublicKey(in);
	}

	/***
	 * 
	 * @param modulus
	 *            :N
	 * @param publicExponent
	 *            :E
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String modulus, String publicExponent)
			throws Exception {
		BigInteger m = new BigInteger(modulus);

		BigInteger e = new BigInteger(publicExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
		KeyFactory keyFactory = KeyFactory
				.getInstance(SystemUtil.KEY_ALGORITHM_RSA);

		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	// public static PublicKey getPublicKey(BigInteger m, BigInteger e){
	// RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
	// KeyFactory keyFactory = KeyFactory
	// .getInstance(SystemUtil.KEY_ALGORITHM_RSA);
	//
	// PublicKey publicKey = keyFactory.generatePublic(keySpec);
	// return publicKey;
	// }
	/***
	 * 
	 * @param modulus
	 * @param ePublicExponent
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(BigInteger modulus,
			BigInteger ePublicExponent) throws Exception {
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus,
				ePublicExponent);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		return publicKey;

	}

	/***
	 * 
	 * @param m
	 * @param publicExponent
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(BigInteger m, byte[] publicExponent)
			throws Exception {
		BigInteger e = new BigInteger(publicExponent);
		return getPublicKey(m, e);
	}

	/**
	 * convert byte array to private key algorithm : RSA
	 * 
	 * @param keyBytes
	 *            byte[]
	 * @return RSAPrivateKey
	 * @throws Exception
	 */
	public static PrivateKey convert2PrivateKey(byte[] keyBytes,
			String algorithm) throws Exception {
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		return privateKey;
	}

	/***
	 * 
	 * @param keyString
	 *            : hex(16) string
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey convert2PrivateKey(String keyString,
			String algorithm) throws Exception {
		byte[] keyBytes = hexStrToBytes(keyString);
		return convert2PrivateKey(keyBytes, algorithm);
	}

	/***
	 * convert private key to hex bit string
	 * 
	 * @param privateKey
	 * @return keyString : hex(16) string
	 */
	public static String convert4PrivateKey(PrivateKey privateKey) {
		return toHexString(privateKey.getEncoded());
	}

	/**
	 * decrypt,key can be a public key， can also be a private key algorithm :
	 * RSA
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] message, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance(SystemUtil.KEY_ALGORITHM_RSA);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(message);
	}

	/**
	 * decrypt,key can be a public key， can also be a private key
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(String message, Key key) throws Exception {
		return SystemUtil.decrypt(SystemUtil.hexStrToBytes(message), key);
	}

	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @param publicKeyStr
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String publicKeyStr,
			String algorithm) throws Exception {
		// 对密钥解密
		byte[] keyBytes = SystemUtil.hexStrToBytes(publicKeyStr);

		// 取得公钥
		PublicKey publicKey = SystemUtil.convert2PublicKey(keyBytes);

		return SystemUtil.decrypt(data, publicKey);
	}

	/**
	 * decrypt use private key to decrypt http://www.5a520.cn
	 * http://www.feng123.com
	 * 
	 * @param data
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String privateKeyStr,
			String algorithm) throws Exception {
		byte[] keyBytes = SystemUtil.hexStrToBytes(privateKeyStr);
		return decryptByPrivateKey(data, keyBytes, algorithm);
	}

	public static byte[] decryptByPrivateKey(byte[] data, byte[] keyBytes,
			String algorithm) throws Exception {
		PrivateKey privateKey = SystemUtil.convert2PrivateKey(keyBytes,
				algorithm);
		return SystemUtil.decrypt(data, privateKey);
	}

	/***
	 * 
	 * @param data
	 * @param N
	 *            :modulus
	 * @param D
	 *            :private exponent
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] N, byte[] D)
			throws Exception {
		PrivateKey privateKey = getPrivateKey(N, D);
		return decrypt(data, privateKey);
	}

	/***
	 * 
	 * @param dataHex
	 *            :hex bit string
	 * @param privateKeyStr
	 * @param privateKeyStr
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(String dataHex,
			String privateKeyStr, String algorithm)
			throws UnsupportedEncodingException, Exception {
		return decryptByPrivateKey(SystemUtil.hexStrToBytes(dataHex),
				privateKeyStr, algorithm);
	}

	/**
	 * DES
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptDES(byte[] data, byte[] key) throws Exception {
		// Generate a random number generator which can be trusted
		SecureRandom sr = new SecureRandom();

		DESKeySpec dks = new DESKeySpec(key);

		// Create a key factory, and then use it to convert DESKeySpec to
		// SecretKey
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(SystemUtil.KEY_ALGORITHM_DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(SystemUtil.KEY_ALGORITHM_DES);

		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * DES
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws java.io.IOException
	 * @throws Exception
	 */
	public static String decryptDES(String data, String key)
			throws IOException, Exception {
		if (data == null)
			return null;
		byte[] buf = SystemUtil.decodeBase64(data);
		byte[] bt = SystemUtil.decryptDES(buf,
				key.getBytes(SystemUtil.CHARSET_UTF));
		return new String(bt, SystemUtil.CHARSET_UTF);
	}

	/**
	 * encrypt,key can be a public key，can also be a private key algorithm : RSA
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] message, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance(SystemUtil.KEY_ALGORITHM_RSA);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(message);
	}

	/**
	 * encrypt,key can be a public key，can also be a private key
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String message, Key key) throws Exception {
		return SystemUtil.encrypt(
				message.getBytes(SystemUtil.CHARSET_ISO88591), key);
	}

	/**
	 * encrypt use public key
	 * 
	 * @param data
	 * @param publicKeyStr
	 *            : hex bit string
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKeyStr)
			throws Exception {
		byte[] keyBytes = hexStrToBytes(publicKeyStr);
		// get public key
		PublicKey publicKey = SystemUtil.convert2PublicKey(keyBytes);
		return SystemUtil.encrypt(data, publicKey);
	}

	/***
	 * 
	 * @param data
	 * @param publicKeyStr
	 *            : hex bit string
	 * @param charSet
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(String data, String publicKeyStr,
			String charSet) throws UnsupportedEncodingException, Exception {
		return encryptByPublicKey(data.getBytes(charSet), publicKeyStr);
	}

	/**
	 * encrypt use private key
	 * 
	 * @param data
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKeyStr,
			String algorithm) throws Exception {
		byte[] keyBytes = hexStrToBytes(privateKeyStr);
		// get private key
		Key privateKey = SystemUtil.convert2PrivateKey(keyBytes, algorithm);
		return SystemUtil.encrypt(data, privateKey);
	}

	/***
	 * 
	 * @param data
	 * @param privateKeyStr
	 *            : hex bit string
	 * @param charSet
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(String data, String privateKeyStr,
			String charSet, String algorithm)
			throws UnsupportedEncodingException, Exception {
		return encryptByPrivateKey(data.getBytes(charSet), privateKeyStr,
				algorithm);
	}

	/**
	 * DES
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptDES(byte[] data, byte[] key) throws Exception {
		// Generate a random number generator which can be trusted
		SecureRandom sr = new SecureRandom();

		DESKeySpec dks = new DESKeySpec(key);
		// Create a key factory, and then use it to convert DESKeySpec to
		// SecretKey
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(SystemUtil.KEY_ALGORITHM_DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance(SystemUtil.KEY_ALGORITHM_DES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * DES
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptDES(String data, String key) throws Exception {
		byte[] bt = SystemUtil.encryptDES(
				data.getBytes(SystemUtil.CHARSET_UTF),
				key.getBytes(SystemUtil.CHARSET_UTF));
		String strs = SystemUtil.encodeBase64(bt);
		return strs;
	}




	/***
	 * convert hex(16) bit string to byte array
	 * 
	 * @param sHex
	 *            : hex(16) bit string
	 * @return byte[]
	 */
	public static final byte[] hexStrToBytes(String sHex) {
		int length = sHex.length();
		if (length % 2 != 0) {
			String message = "Hex  bit string length must be even";
			System.err.println(message);
			throw new RuntimeException(message);
		}
		byte[] bytes;
		bytes = new byte[sHex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(
					sHex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

	/***
	 * convert byte array to hex(16) bit string
	 * 
	 * @param b
	 * @return hex(16) bit string
	 */
	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEXCHAR[(b[i] & 0xf0) >>> 4]);
			sb.append(HEXCHAR[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param b
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static String toString(byte[] b) throws UnsupportedEncodingException {
		return new String(b, CHARSET_ISO88591);
	}

	/**
	 * SHA encrypt
	 * 
	 * @param data
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(SystemUtil.KEY_SHA);
		sha.update(data);
		return sha.digest();

	}

	/***
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(String data) throws Exception {
		return encryptSHA(data.getBytes(SystemUtil.CHARSET_ISO88591));
	}

	/***
	 * sha-1
	 * 
	 * @param data
	 *            byte[]
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA1(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(SystemUtil.KEY_SHA1);
		sha.update(data);
		return sha.digest();

	}

	/***
	 * sha-1
	 * 
	 * @param data
	 *            :String
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA1(String data) throws Exception {
		return encryptSHA1(data.getBytes(SystemUtil.CHARSET_ISO88591));
	}

	/***
	 * 
	 * @param secretKey
	 * @param input
	 * @param algorithm
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] getHMAC(byte[] secretKey, byte[] input,
			String algorithm) throws Exception {
		Mac mac = Mac.getInstance(algorithm);
		// get the bytes of the hmac key and data string
		SecretKey secret = new SecretKeySpec(secretKey, algorithm);
		mac.init(secret);
		// 对input 进行HMAC 加密
		byte[] bytesF1 = mac.doFinal(input);
		return bytesF1;
	}

	/***
	 * HMACSHA256
	 * 
	 * @param secretKey
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static byte[] getHMAC_SHA256(byte[] secretKey, byte[] input)
			throws Exception {
		return getHMAC(secretKey, input, SystemUtil.KEY_HMAC_SHA256);
	}

	/***
	 * HmacSHA1
	 * 
	 * @param secretKey
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static byte[] getHMAC_SHA1(byte[] secretKey, byte[] input)
			throws Exception {
		return getHMAC(secretKey, input, SystemUtil.KEY_HMAC_SHA1);
	}

	/***
	 * 
	 * @param secretKey
	 *            : hex bit string
	 * @param input
	 *            : hex bit string
	 * @return byte array
	 * @throws Exception
	 */
	public static byte[] getHMAC_SHA1(String secretKey, String input)
			throws Exception {
		return getHMAC_SHA1(SystemUtil.hexStrToBytes(secretKey),
				SystemUtil.hexStrToBytes(input));
	}

	/***
	 * 
	 * @param keyInfo
	 * @return
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static KeyPair getKeyPair(String keyInfo, String algorithm)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return getKeyPair(keyInfo.getBytes(SystemUtil.CHARSET_ISO88591),
				algorithm);
	}

	/***
	 * 
	 * @param keyInfo
	 * @param algorithm
	 *            :算法，如RSA
	 * @return
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static KeyPair getKeyPair(byte[] keyInfo, String algorithm)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance(algorithm);
		SecureRandom random = new SecureRandom();
		random.setSeed(keyInfo);
		// 初始加密，长度为512，必须是大于512才可以的
		keygen.initialize(512, random);
		// 取得密钥对
		KeyPair kp = keygen.generateKeyPair();
		return kp;
	}

	/***
	 * RSA .
	 * 
	 * @param keyInfo
	 * @return
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static KeyPair getRSAKeyPair(byte[] keyInfo)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return getKeyPair(keyInfo, SystemUtil.KEY_ALGORITHM_RSA);
	}

	/***
	 * 
	 * @param modulus
	 *            :N
	 * @param privateExponent
	 *            :D
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String modulus,
			String privateExponent) throws Exception {

		BigInteger m = new BigInteger(modulus);

		BigInteger D = new BigInteger(privateExponent);

		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, D);

		KeyFactory keyFactory = KeyFactory
				.getInstance(SystemUtil.KEY_ALGORITHM_RSA);

		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		return privateKey;

	}

	/***
	 * 
	 * @param m
	 *            : modulus
	 * @param d
	 *            :private exponent
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(BigInteger m, BigInteger d)
			throws Exception {
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, d);

		KeyFactory keyFactory = KeyFactory
				.getInstance(SystemUtil.KEY_ALGORITHM_RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		return privateKey;

	}

	// public static PrivateKey getPrivateKey(byte[] N_hex, byte[] D_hex){
	// return SystemUtil.getPrivateKey(new BigInteger(N_hex), new
	// BigInteger(D_hex));
	// }
	/***
	 * 
	 * @param m
	 * @param privateExponent
	 *            :D
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(BigInteger m, byte[] privateExponent)// TODO
			throws Exception {
		BigInteger d = new BigInteger(privateExponent);
		return getPrivateKey(m, d.negate());
	}

	public static PrivateKey getPrivateKey(byte[] m, byte[] privateExponent)
			throws Exception {
		return getPrivateKey(SystemUtil.getBigIntegerByByteArr(m),
				SystemUtil.getBigIntegerByByteArr(privateExponent));
	}

	/***
	 * OK
	 * 
	 * @param publicKey
	 * @param priKey
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyPrivPubKey(PublicKey publicKey,
			PrivateKey priKey) throws Exception {
		String message = "32";
		RSAPublicKey rsaPublKey = (RSAPublicKey) publicKey;
		RSAPrivateKey rsaPriKey = (RSAPrivateKey) priKey;
		byte[] encryptBytes = SystemUtil.encrypt(message, publicKey);
		byte[] decryptBytes = SystemUtil.decrypt(encryptBytes, priKey);
		return new String(decryptBytes, SystemUtil.CHARSET_ISO88591)
				.equals(message)
				&& rsaPriKey.getModulus().equals(rsaPublKey.getModulus());
	}

	/***
	 * OK
	 * 
	 * @param modulus
	 *            :modulus
	 * @param publicExponent
	 *            :public key publicExponent
	 * @param privateExponent
	 *            :private exponent
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyPrivPubKey(BigInteger modulus,
			BigInteger publicExponent, BigInteger privateExponent)
			throws Exception {
		PublicKey pubKey = getPublicKey(modulus, publicExponent);
		PrivateKey priKey = getPrivateKey(modulus, privateExponent);
		return SystemUtil.verifyPrivPubKey(pubKey, priKey);
	}

	public static boolean verifyPrivPubKey(String modulus_decimal,
			String publicExponent_decimal, String privateExponent_decimal)
			throws Exception {
		BigInteger modulus = new BigInteger(modulus_decimal);
		BigInteger publicExponent = new BigInteger(publicExponent_decimal);
		BigInteger privateExponent = new BigInteger(privateExponent_decimal);
		return verifyPrivPubKey(modulus, publicExponent, privateExponent);
	}

	/***
	 * convert byte array to BigInteger
	 * 
	 * @param bytes
	 * @return
	 */
	public static BigInteger getBigIntegerByByteArr(byte[] bytes) {
		return new BigInteger(SystemUtil.toHexString(bytes), 16);
	}

	/***
	 * convert BigInteger to byte array
	 * 
	 * @param bi
	 * @return
	 */
	public static byte[] getBytesByBigInteger(BigInteger bi) {
		String hexString = bi.toString(16);
		return SystemUtil.hexStrToBytes(hexString);
	}

	/***
	 * get prime number
	 * 
	 * @param base
	 * @return
	 */
	// public static int generatePrime(int base) {
	// for (int i = base;; i++) {
	// if (isPrime(i)) {
	// return i;
	// }
	// }
	// }
	public static BigInteger generatePrime(int base) {
		return generatePrime(BigInteger.valueOf(base));
	}

	/***
	 * get prime number which >=base
	 * 
	 * @param base
	 * @return BigInteger
	 */
	public static BigInteger generatePrime(BigInteger base) {
		BigInteger bigInt1 = BigInteger.valueOf(1l);
		for (BigInteger i = base;; i = i.add(bigInt1)) {
			if (isPrime(i)) {
				return i;
			}
		}
	}

	/***
	 * whether is a prime number
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isPrime(int num) {
		return isPrime(BigInteger.valueOf(num));
		// boolean isPrime = true;
		// for (int i = 2; i <= num / 2; i++) {
		// if (num % i == 0) {
		// isPrime = false;
		// break;
		// }
		// }
		// return isPrime;
	}

	/***
	 * whether is a prime number
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isPrime(BigInteger num) {
		boolean isPrime = true;
		BigInteger bigIntTwo = BigInteger.valueOf(2l);
		BigInteger bigIntOne = BigInteger.valueOf(1l);
		for (BigInteger i = bigIntTwo; num.divide(bigIntTwo).compareTo(i) >= 0; i = i
				.add(bigIntOne)) {
			if (num.mod(i).intValue() == 0) {
				isPrime = false;
				break;
			}
		}
		return isPrime;
	}

	/***
	 * 
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static BigInteger mod(BigInteger arg1, BigInteger arg2) {
		return arg1.mod(arg2);
	}


	/***
	 * convert int to hex string
	 * 
	 * @param bigInt
	 * @return
	 */
	public static String toHexString(BigInteger bigInt) {
		return bigInt.toString(16);
	}

	/***
	 * encode by Base64
	 */
	public static String encodeBase64(byte[] input) throws Exception {
		Class clazz = Class
				.forName("com.sun.org.apache.xerces.internal.impl.dv.com.datang.business.util.Base64");
		Method mainMethod = clazz.getMethod("encode", byte[].class);
		mainMethod.setAccessible(true);
		Object retObj = mainMethod.invoke(null, new Object[] { input });
		return (String) retObj;
	}

	/***
	 * decode by Base64
	 */
	public static byte[] decodeBase64(String input) throws Exception {
		Class clazz = Class
				.forName("com.sun.org.apache.xerces.internal.impl.dv.com.datang.business.util.Base64");
		Method mainMethod = clazz.getMethod("decode", String.class);
		mainMethod.setAccessible(true);
		Object retObj = mainMethod.invoke(null, input);
		return (byte[]) retObj;
	}

	/***
	 * 获取实际的子类的class
	 * 
	 * @param clz
	 * @return
	 */
	public static <T> Class<T> getGenricClassType(
			@SuppressWarnings("rawtypes") Class clz) {
		Type type = clz.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			Type[] types = pt.getActualTypeArguments();
			if (types.length > 0 && types[0] instanceof Class) {
//				System.out.println("class:"+types[0]);
				return (Class) types[0];
			}
		}
		
		return (Class) Object.class;
	}

	// public static byte[] decodeBufferBASE64Decoder(String data) {
	//
	// try {
	// Class clazz = Class.forName("sun.misc.BASE64Decoder");
	// Method mainMethod;
	// mainMethod = clazz.getMethod("decodeBuffer", String.class);
	// mainMethod.setAccessible(true);
	// Object retObj = mainMethod.invoke(clazz.newInstance(), data);
	// return (byte[]) retObj;
	// } catch (SecurityException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InstantiationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	//
	// }
	//
	// public static String encodeBASE64Encoder(byte[] bt) {
	//
	// try {
	// Class clazz = Class.forName("sun.misc.BASE64Decoder");
	// Method mainMethod;
	// mainMethod = clazz.getMethod("encode", byte[].class);
	// mainMethod.setAccessible(true);
	// Object retObj = mainMethod.invoke(clazz.newInstance(), bt);
	// return (String) retObj;
	// } catch (SecurityException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InstantiationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	//
	// }
	/***
	 * print byte array
	 * 
	 * @param bytes
	 * @param isNeedPlus
	 *            : Whether to add a plus sign
	 * @return such as
	 *         "[ 52, 116, -18, 34, 70, -43,  56, -60, 17, -67, -52, -97 ] ;length:16"
	 */
	public static String printBytes(byte[] bytes, boolean isNeedPlus) {
		StringBuffer sb = new StringBuffer("[ ");
		for (int i = 0; i < bytes.length; i++) {

			if (bytes[i] > 0 && isNeedPlus) {
				sb.append("+" + String.valueOf(bytes[i]));
			} else {
				sb.append(bytes[i]);
			}
			if (i < bytes.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(" ]").append(" ;length:" + bytes.length);
		return sb.toString();
	}

	/***
	 * Format a byte array
	 * 
	 * @param bytes
	 * @return
	 */
	public static String formatBytes(byte[] bytes) {
		return printBytes(bytes, false);
	}

	/***
	 * Format a byte array
	 * 
	 * @param hex
	 * @return
	 */
	public static String formatBytes(String hex) {
		return formatBytes(SystemUtil.hexStrToBytes(hex));
	}

	/***
	 * 
	 * @param bytes
	 */
	public static void printBytes(byte[] bytes) {
		System.out.println(formatBytes(bytes));
	}

	/***
	 * 
	 * @param hex
	 */
	public static void printBytes(String hex) {
		System.out.println(formatBytes(hex));
	}

	/***
	 * 合并字节数组
	 * 
	 * @param a
	 * @return
	 */
	public static byte[] mergeArray(byte[]... a) {
		// 合并完之后数组的总长度
		int index = 0;
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum = sum + a[i].length;
		}
		byte[] result = new byte[sum];
		for (int i = 0; i < a.length; i++) {
			int lengthOne = a[i].length;
			if (lengthOne == 0) {
				continue;
			}
			// 拷贝数组
			System.arraycopy(a[i], 0, result, index, lengthOne);
			index = index + lengthOne;
		}
		return result;
	}

	/***
	 * 合并字符数组
	 * 
	 * @param a
	 * @return
	 */
	public static char[] mergeArray(char[]... a) {
		// 合并完之后数组的总长度
		int index = 0;
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum = sum + a[i].length;
		}
		char[] result = new char[sum];
		for (int i = 0; i < a.length; i++) {
			int lengthOne = a[i].length;
			if (lengthOne == 0) {
				continue;
			}
			// 拷贝数组
			System.arraycopy(a[i], 0, result, index, lengthOne);
			index = index + lengthOne;
		}
		return result;
	}

	
	/***
	 * append a byte.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte[] appandByte(byte[] a, byte b) {
		int length = a.length;
		byte[] resultBytes = new byte[length + 1];
		System.arraycopy(a, 0, resultBytes, 0, length);
		resultBytes[length] = b;
		return resultBytes;
	}

	/***
	 * merge two int array to a string
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String merge(int[] a, int[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i]);
			sb.append(",");
		}
		for (int i = 0; i < b.length; i++) {
			sb.append(b[i]);
			sb.append(",");
		}
		int leng_str = sb.toString().length();
		return sb.substring(0, leng_str - 1);
	}

	/***
	 * Get top <code>frontNum</code> bytes
	 * 
	 * @param source
	 * @param frontNum
	 * @return
	 */
	public static byte[] getFrontBytes(byte[] source, int frontNum) {
		byte[] frontBytes = new byte[frontNum];
		System.arraycopy(source, 0, frontBytes, 0, frontNum);
		return frontBytes;
	}

	public static byte[] getAfterBytes(byte[] source, int afterNum) {
		int length = source.length;
		byte[] afterBytes = new byte[afterNum];
		System.arraycopy(source, length - afterNum, afterBytes, 0, afterNum);
		return afterBytes;
	}

	/***
	 * 
	 * @param frontNum
	 * @param source
	 * @return
	 */
	public static byte[] filterFrontBytes(int frontNum, byte[] source) {
		return copyByte(frontNum, source.length - frontNum, source);
	}

	public static byte[] copyByte(int start, int length, byte[] source) {
		byte[] des = new byte[length];
		System.arraycopy(source, start, des, 0, length);
		return des;
	}

	/***
	 * Compare two byte arrays whether are the same.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean arrayIsEqual(byte[] a, byte[] b) {
		if (a == null && b == null) {
			return true;
		}

		if (a != null && b != null) {
			if (a.length != b.length) {
				return false;
			} else {
				for (int i = 0; i < a.length; i++) {
					if (a[i] != b[i]) {
						return false;
					}
				}
			}
		} else {// one is null, the other is not null
			return false;
		}
		return true;
	}
	/***
	 * Compare two int arrays whether are the same.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean arrayIsEqual(int[] a, int[] b) {
		if (a == null && b == null) {
			return true;
		}

		if (a != null && b != null) {
			if (a.length != b.length) {
				return false;
			} else {
				for (int i = 0; i < a.length; i++) {
					if (a[i] != b[i]) {
						return false;
					}
				}
			}
		} else {// one is null, the other is not null
			return false;
		}
		return true;
	}

	/***
	 * Delete the slash which is in front of input
	 * 
	 * @param input
	 * @return
	 */
	public static String deleteFrontSlash(String input) {
		String result = input.replaceAll("^/", "");
		return result;
	}

	/***
	 * Delete the brackets
	 * 
	 * @param input
	 * @return
	 */
	public static String deletebrackets(String input) {
		input = input.replaceAll("\\[?(.*)\\]?", "$1");
		return input;
	}

	/***
	 * Delete the curly braces ({ })
	 * 
	 * @param input
	 * @return
	 */
	public static String deleteCurlyBraces(String input) {
		input = input.replaceAll("\\{?(.*)\\}", "$1");
		return input;
	}

	public static String deleteSingleQuotes(String input) {
		input = input.replaceAll("'?(.*)'", "$1");
		return input;
	}

	/***
	 * 以斜杠和?分割，获取最后一个 / ? input
	 * :http://localhost:8081/SSLServer/addUser.security?a=b
	 * result:addUser.security
	 */
	public static String getSerlvetNameByQuestionMark(String url) {
		String input = null;
		input = url.replaceAll(".*/([\\w\\.]*)(\\?.*)?$", "$1");
		return input;
	}

	/***
	 * input :http://localhost:8081/SSLServer/addUser.security?a=b
	 * result:addUser
	 */
	public static String getSerlvetName(String url) {
		String input = null;
		input = url.replaceAll(".*/([\\w\\.]*)(\\..*)$", "$1");
		if (input.contains("?")) {
			input = getSerlvetNameByQuestionMark(input);
		}
		return input;
	}

	/***
	 * get port
	 * 
	 * @param url
	 *            such as http://localhost:8081/SSLServer/addUser.A?a=b
	 * @return 8081
	 * 
	 */
	public static String getHttpPort(String url) {
		String input = url.replaceAll("^.+:([\\d]+)/.*$", "$1");
		return input;
	}

	/***
	 * 
	 * 
	 * @param url
	 *            such as localhost/SSLServer/addUser.A?a=b
	 * @return SSLServer
	 */
	public static String getProjectName(String url) {
		String input = url.replaceAll("^.+(:[\\d]+)?/(.*)/.*$", "$2");
		return input;
	}

	/***
	 * get Http request ip
	 * 
	 * @param url
	 * @return
	 */
	public static String getHttpIp(String url) {
		String input = url.replaceAll("^(.*://)?([^/:]*)(:[\\d]+)?/.*$", "$2");
		return input;
	}

	/***
	 * be similar to grep in linux os
	 * 
	 * @param keyWord
	 * @param input
	 *            :List
	 * @return
	 */
	public static List<String> grepStr(String keyWord, String input) {
		String regex = ".*" + keyWord + ".*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		List<String> grepResult = new ArrayList<String>();
		if (m.find()) {
			grepResult.add(m.group());
		}
		return grepResult;
	}

	/****
	 * old:java.lang.String ; result: String
	 * 
	 * @param input
	 * @return
	 */
	public static String getLastNameByPeriod(String input) {
		input = input.replaceAll("^.*\\.([\\w]+)", "$1");
		return input;
	}

	/***
	 * 
	 * @param input
	 *            :2013-06-15
	 * @return
	 */
	public static boolean isDate(String input) {
		String regex = "[\\d]{4}-[\\d]{1,2}-[\\d]{1,2}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		return m.matches();
	}

	public static String grepSimple(String keyWord, String input) {
		List<String> grepResult = grepStr(keyWord, input);
		if (grepResult.size() > 0) {
			return grepResult.get(0);
		} else {
			return null;
		}
	}

	/***
	 * times byte occure int byte[]
	 * 
	 * @param hexStr
	 * @param keyWord
	 * @return
	 */
	public static int indexOf(String hexStr, String keyWord) {
		return hexStr.indexOf(keyWord.toLowerCase()) / 2;
	}

	public static int indexOf(byte[] bytes, String keyWord) {
		return indexOf(SystemUtil.toHexString(bytes), keyWord.toLowerCase());
	}

	public static int indexOf(byte[] bytes, byte[] keyWord) {
		return indexOf(SystemUtil.toHexString(bytes),
				SystemUtil.toHexString(keyWord).toLowerCase());
	}

	/***
	 * 获取keyword 在srcText出现的次数
	 * 
	 * @param srcText
	 * @param keyword
	 * @return
	 */
	public static int count(String srcText, String keyword) {
		return findStr1(srcText, keyword);
	}

	/**
	 * 
	 * The number of occurrences of find keyword in srcText
	 * 
	 * @param srcText
	 * @param keyword
	 * @return
	 */
	public static int findStr1(String srcText, String keyword) {
		int count = 0;
		int leng = srcText.length();
		int j = 0;
		for (int i = 0; i < leng; i++) {
			if (srcText.charAt(i) == keyword.charAt(j)) {
				j++;
				if (j == keyword.length()) {
					count++;
					j = 0;
				}
			} else {
				i = i - j;// should rollback when not match
				j = 0;
			}
		}

		return count;
	}

	/***
	 * The number of occurrences of find keyword in srcText
	 * 
	 * @param srcText
	 * @param keyword
	 * @return
	 */
	public static int findStr2(String srcText, String keyword) {
		int count = 0;
		Pattern p = Pattern.compile(keyword);
		Matcher m = p.matcher(srcText);
		while (m.find()) {
			count++;
		}
		return count;
	}

	public static int findStr3(String srcText, String keyword) {
		return findStr(srcText, keyword, 0);
	}

	/***
	 * The number of occurrences of find keyword in srcText
	 * 
	 * @param srcText
	 * @param keyWord
	 * @param pos
	 * @return
	 */
	public static int findStr(String srcText, String keyWord, int pos) {
		int i, j, k = 0;
		i = pos;
		j = 0;
		while (i < srcText.length() && j < keyWord.length()) {
			if (srcText.charAt(i) == keyWord.charAt(j)) {
				++i;
				++j;
				if (j == keyWord.length()) {
					k = k + 1;// k++
					j = 0;
				}
			} else {
				i = i - j + 1;
				j = 0;
			}
		}
		return k;
	}

	/***
	 * 
	 * @param source
	 * @param findTarget
	 *            :key word
	 * @param pos
	 *            :where start from
	 * @return index
	 */
	public static int findBytes(byte[] source, byte[] findTarget, int pos) {
		int i, j, k = 0;
		i = pos;
		j = 0;
		while (i < source.length && j < findTarget.length) {
			if (source[i] == findTarget[j]) {
				++i;
				++j;
				if (j == findTarget.length) {
					k = k + 1;// k++
					break;
					// j = 0;
				}
			} else {
				i = i - j + 1;
				j = 0;
			}
		}
		return k == 0 ? -1 : i - j;
	}

	/***
	 * start from 0
	 * 
	 * @param source
	 * @param findTarget
	 * @return
	 */
	public static int findBytes(byte[] source, byte[] findTarget) {
		return findBytes(source, findTarget, 0);
	}

	// / <summary>
	// / 判断两个byte[]包含的值是否相等
	// / </summary>
	// / <param name="bytes1">byte[] 1</param>
	// / <param name="bytes2">byte[] 2</param>
	// / <returns>相等返回True,反之False</returns>
	public static boolean isEqualBytes(byte[] a, byte[] b) {
		return arrayIsEqual(a, b);
	}

	/***
	 * compare tow byte[]
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEquals(byte[] a, byte[] b) {
		return arrayIsEqual(a, b);
	}

	/***
	 * is equals to
	 * <code>public static boolean isEquals(byte[] bytes1, byte[] bytes2)</code>
	 * 
	 * @param bytes1
	 * @param bytes2
	 * @return
	 */
	public static boolean isSame(byte[] bytes1, byte[] bytes2) {
		return isEqualBytes(bytes1, bytes2);
	}

	public static boolean isSame(int[] bytes1, int[] bytes2) {
		return arrayIsEqual(bytes1, bytes2);
	}
	public static boolean isEqualChars(char[] chars1, char[] chars2) {
		// 比较长度是否一样
		if (chars1.length != chars2.length) {
			return false;
		}
		// 比较成员是否对应相等
		for (int i = 0; i < chars1.length; i++) {
			if (chars1[i] != chars2[i]) {
				return false;
			}
		}
		return true;
	}
	public static boolean isSame(char[] bytes1, char[] bytes2) {
		return isEqualChars(bytes1, bytes2);
	}
	/***
	 * //
	 * D:\xxx\eclipse\workspace\.metadata\.plugins\org.eclipse.wst.server.core
	 * \tmp0\wtpwebapps\shop_goods\images //
	 * D:\xxx\eclipse\workspace\shop_goods\ upload
	 * 
	 * @param realPath2
	 * @param projectName
	 * @return
	 */
	public static String getRealPath(String realPath2, String projectName) {
		String realpath = realPath2.replaceAll(".metadata.*(" + projectName
				+ ")", "$1");
		return realpath;
	}

	/***
	 * convert List to String[]
	 * 
	 * @param list
	 * @return
	 */
	public static String[] list2Array(List<String> list) {
		return list.toArray(new String[list.size()]);
	}

	/***
	 * print per
	 * 
	 * @param strs
	 */
	public static void printArray(Object[] strs, boolean isNewLine,
			String delimiter) {
		List<Object> list = Arrays.asList(strs);
		printList(list, isNewLine, delimiter);
		// for(int i=0;i<strs.length;i++){
		// System.out.println(strs[i]);
		// }
	}

	public static void printArray(int[] ints) {
		for (int i = 0; i < ints.length; i++) {
			System.out.print(ints[i]);
			if (i < ints.length - 1) {
				System.out.print(" ,");
			}
		}
		System.out.println();
	}

	/***
	 * Print two-dimensional array 0_0 0_1 0_2 0_3 0_4 1_0 1_1 1_2 1_3 1_4
	 * 
	 * @param arrays
	 */
	public static void printArrays(Object[][] arrays, String delimiter) {
		for (int i = 0; i < arrays.length; i++) {
			Object[] objs = arrays[i];
			if (objs != null && objs.length > 0) {
				printArray(objs, false, delimiter);
				System.out.println();
			}
		}
	}

	/***
	 * convert boolean to string
	 * 
	 * @param bool
	 * @return
	 */
	public static String getBooleanString(boolean bool) {
		return String.valueOf(bool);
	}


	public static int parseObj(Object obj) {
		if (null == obj) {
			return NEGATIVE_ONE;
		}
		return Integer.parseInt(obj.toString());
	}


	/**
	 * 产生无重复的随机数 sumInt:总样本 (0....sumInt-1) resultSum： 产生的随机数个数
	 * 
	 * @return
	 */
	public static int[] randoms(int sumInt, int resultSum) {
		// Total sample
		int send[] = new int[sumInt];// 0....(sumInt-1)
		for (int i = 0; i < sumInt; i++) {
			send[i] = i;
		}
		return randoms(send, resultSum);
	}

	/***
	 * 
	 * @param totalSample
	 *            :total sample
	 * @param resultSum
	 *            :Specified number
	 * @return
	 */
	public static int[] randoms(int[] totalSample, int resultSum) {
		int temp1, temp2;
		Random r = new Random();
		int len = totalSample.length;// The length of the total sample
		int returnValue[] = new int[resultSum];// Random number to return
		for (int i = 0; i < resultSum; i++) {
			// temp1 = Math.abs(r.nextInt()) % len;
			temp1 = r.nextInt(len);// between 0 (inclusive) and the specified
									// value (exclusive)
			temp2 = totalSample[temp1];
			returnValue[i] = temp2;
			if (temp1 != len - 1) {
				totalSample[temp1] = totalSample[len - 1];
				totalSample[len - 1] = temp2;
			}
			len--;
		}
		return returnValue;
	}

	/***
	 * whether j is involved in <code>int[] intArray</code>
	 * 
	 * @param intArray
	 * @param j
	 * @return
	 */
	public static boolean isContains(int[] intArray, int j) {
		boolean initBool = false;
		for (int i : intArray) {
			if (i == j) {
				initBool = true;
				break;
			}
		}
		return initBool;
	}

	public static boolean isContains2(int[] intArray, int j) {
		return isContains(intArray, j, 2);
	}

	/***
	 * Match the specified number(times)
	 * 
	 * @param intArray
	 * @param j
	 * @param time
	 * @return
	 */
	public static boolean isContains(int[] intArray, int j, int time) {
		boolean initBool = false;
		int count = 0;
		for (int i : intArray) {
			if (i == j) {
				count++;
			}
		}
		if (count == time) {
			return true;
		}
		return initBool;
	}

	/***
	 * Filter out the elements of the same
	 * 
	 * @param intArray
	 * @return
	 */
	public static boolean uniqueInt(int[] intArray) {
		boolean initBool = true;
		for (int j : intArray) {
			if (!isContains(intArray, j, 1)) {
				initBool = false;
				break;
			}
		}
		return initBool;
	}

	public static boolean isEquals(List<String> aa, List<String> bb) {
		if (null == aa || null == bb)
			return false;
		boolean isEqual = true;
		if (aa.size() == bb.size()) {
			for (int i = 0; i < aa.size(); i++) {
				String aaStr = aa.get(i);
				String bbStr = bb.get(i);
				if (!aaStr.equals(bbStr)) {
					isEqual = false;
					break;
				}
			}
		} else {
			return false;
		}

		return isEqual;
	}

	/***
	 * 
	 * @param input
	 *            ： 0 or 1 or false or true
	 * @return true:1,true ; false:0,false
	 */
	public static boolean parse33(String input) {
		boolean result = false;
		if (input.equals("0") || input.equals("1")) {
			int resultint = Integer.parseInt(input);
			result = (resultint == 1);
		} else {
			result = Boolean.parseBoolean(input);
		}
		return result;
	}

	/***
	 * reverse map Note : value in oldMap must be unique. rever
	 * 
	 * @param oldMap
	 * @return
	 */
	public static Map reverseMap(Map oldMap) {
		Map newMap = new HashMap<Object, Object>();
		for (Iterator it = oldMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<Object, String> entry = (Map.Entry<Object, String>) it
					.next();
			newMap.put(entry.getValue(), entry.getKey());
		}
		return newMap;
	}

	/***
	 * 
	 * @param arrays
	 * @param columnIndex
	 *            : start from one
	 * @return
	 */
	public static Object[] getProjection(Object[][] arrays, int columnIndex) {
		int length = arrays.length;
		Object[] objs = new Object[length];
		for (int i = 0; i < length; i++) {
			if (arrays[i] == null) {
				objs[i] = null;
			} else {
				objs[i] = arrays[i][columnIndex - 1];
			}
		}
		return objs;
	}

	/***
	 * convert request query string to map
	 * 
	 * @param queryString
	 * @return
	 */
	public static Map<String, Object> parseQueryString(String queryString) {
		Map<String, Object> argMap = new HashMap<String, Object>();
		String[] queryArr = queryString.split("&");
		for (int i = 0; i < queryArr.length; i++) {
			String string = queryArr[i];
			String keyAndValue[] = string.split("=");
			if (keyAndValue.length != 2) {
				argMap.put(keyAndValue[0], EMPTY);
			} else {
				argMap.put(keyAndValue[0], keyAndValue[1]);
			}
		}
		return argMap;
	}

	/***
	 * convert Map<String, Object> to Map<String, String>
	 * 
	 * @param oldMap
	 * @return
	 */
	public static Map<String, String> convertMap(Map<String, Object> oldMap) {
		Map<String, String> newMap = new HashMap<String, String>();
		for (Iterator it = oldMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			newMap.put(entry.getKey(), (String) entry.getValue());
		}
		return newMap;
	}

	public static String getFilecharset(File sourceFile) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(sourceFile));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset; // 文件编码为 ANSI
			} else if (first3Bytes[0] == (byte) 0xFF
					&& first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE"; // 文件编码为 Unicode
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE"; // 文件编码为 Unicode big endian
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8"; // 文件编码为 UTF-8
				checked = true;
			}
			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80
							// - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
			}
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charset;
	}

	/***
	 * convert String[] to ArrayList<String>
	 * 
	 * @param strs
	 * @return
	 */
	public static ArrayList<String> asArrayList(String[] strs) {
		ArrayList<String> arrs = new ArrayList<String>();
		for (int i = 0; i < strs.length; i++) {
			arrs.add(strs[i]);
		}
		return arrs;
	}

	public static ArrayList<Object> asArrayList(Object[] strs) {
		ArrayList<Object> arrs = new ArrayList<Object>();
		for (int i = 0; i < strs.length; i++) {
			arrs.add(strs[i]);
		}
		return arrs;
	}

	/***
	 * open Browser
	 * 
	 * @param url
	 * @return
	 */
	public static boolean openURL(String url) {
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Mac OS")) {
				// mac
				Class fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL",
						new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
			} else if (osName.startsWith("Windows")) {
				// Windows
				Runtime.getRuntime().exec(
						"rundll32 url.dll,FileProtocolHandler " + url);
			} else {
				// assume Unix or Linux
				String[] browsers = { "firefox", "opera", "konqueror",
						"epiphany", "mozilla", "netscape" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++) {
					if (Runtime.getRuntime()
							.exec(new String[] { "which", browsers[count] })
							.waitFor() == 0) {
						browser = browsers[count];
					}
				}
				if (browser != null) {
					Runtime.getRuntime().exec(new String[] { browser, url });
				}
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 获得KeyStore
	 * 
	 * @param keyStorePath
	 *            密钥库路径
	 * @param password
	 *            密码
	 * @return KeyStore 密钥库
	 */
	public static KeyStore getKeyStore(String keyStorePath, String password)
			throws Exception {
		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		// 获得密钥库文件流
		FileInputStream is = new FileInputStream(keyStorePath);
		// 加载密钥库
		ks.load(is, password.toCharArray());
		// 关闭密钥库文件流
		is.close();
		return ks;
	}

	/***
	 * 
	 * @param keyStorePath
	 *            : create by keytool,suffix is ".keystore"
	 * @param password
	 *            : specified by keytool
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyByKeystore(String keyStorePath,
			String password, String alias) throws Exception {
		// 获得密钥库
		KeyStore ks = getKeyStore(keyStorePath, password);
		// 获得私钥
		PrivateKey privateKey = (PrivateKey) ks.getKey(alias,
				password.toCharArray());
		return privateKey;
	}

	/***
	 * Whether the files is in the specified directory.
	 * 
	 * @param filepath
	 * @param folder
	 *            : directory
	 * @return
	 */
	public static boolean isDirIncludeFile(String filepath, String folder) {
		// folder:D:\eclipse\workspace\shop_goods\ upload
		if (folder.contains("\\")) {
			folder = folder.replaceAll("\\\\", "/");
		}
		boolean isContain = filepath.contains(folder);
		if (!isContain) {
			isContain = filepath.replaceAll("//", "/").contains(
					folder.replaceAll("//", "/"));
		}
		return isContain;
	}
}
