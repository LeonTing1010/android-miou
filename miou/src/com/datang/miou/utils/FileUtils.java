package com.datang.miou.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
	public static final int BUFFSIZE_1024 = 1024;

	private FileUtils() {
		throw new Error("Don't let anyone instantiate this class.");
	}

	public static BufferedReader getBufferReaderFromFile(String filename,
			String charset) throws FileNotFoundException {
		InputStream ss = new FileInputStream(filename);
		InputStreamReader ireader = null;
		try {
			ireader = new InputStreamReader(ss, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(ireader);
		return reader;
	}

	public static BufferedReader getBufferReaderFromFile(File file,
			String charset) throws FileNotFoundException {
		InputStream ss = new FileInputStream(file);
		InputStreamReader ireader;
		BufferedReader reader = null;
		try {
			if (charset == null) {
				ireader = new InputStreamReader(ss, SystemUtil.CHARSET_ISO88591);
			} else {
				ireader = new InputStreamReader(ss, charset);
			}
			reader = new BufferedReader(ireader);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return reader;
	}

	public static BufferedInputStream getBufferedInputStreamFromFile(
			String filename) throws FileNotFoundException {
		InputStream ss = new FileInputStream(filename);
		BufferedInputStream bff = new BufferedInputStream(ss);
		return bff;
	}

	/***
	 * convert byte[] to ByteArrayInputStream
	 * 
	 * @param bytes
	 * @return ByteArrayInputStream
	 */
	public static ByteArrayInputStream getByteArrayInputSreamFromByteArr(byte[] bytes) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		return inputStream;
	}

	/***
	 * convert hex(16) bit string to ByteArrayInputStream
	 * 
	 * @param hex
	 * @return ByteArrayInputStream
	 */
	public static ByteArrayInputStream getByteArrayInputSream2hexString(String hex) {
		return getByteArrayInputSreamFromByteArr(SystemUtil.hexStrToBytes(hex));
	}

	public static int isFileContains(File file, String regex, String charset) {
		BufferedReader reader;
		try {
			reader = getBufferReaderFromFile(file, charset);
			return isFileContains(reader, regex);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/***
	 * Note : reader has not close.
	 * 
	 * @param file
	 * @param regex
	 * @param charset
	 * @return
	 */
	public static int isFileContains(String file, String regex, String charset) {
		BufferedReader reader;
		try {
			reader = getBufferReaderFromFile(file, charset);
			return isFileContains(reader, regex);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @param reader
	 *            : has closed
	 * 
	 * @param regex
	 * @return
	 */
	public static int isFileContains(Reader reader, String regex) {
		String readedLine = null;
		BufferedReader br = null;
		try {
			int changedRow = 0;
			br = new BufferedReader(reader);
			while ((readedLine = br.readLine()) != null) {
				changedRow++;
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(readedLine);
				if (m.find()) {
					return changedRow;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * have closed bfreader,but not close reader
	 * 
	 * @param reader
	 * @return
	 */
	@Deprecated
	public static String getFullContent(StringReader reader) {
		BufferedReader bfreader = new BufferedReader(reader);
		String content = getFullContent(bfreader);
		try {
			if (bfreader != null)
				bfreader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/***
	 * 
	 * @param is
	 * @return
	 */
	@Deprecated
	public static String getFullContent(InputStream is) {
		InputStreamReader inputReader = new InputStreamReader(is);
		BufferedReader bureader = new BufferedReader(inputReader);
		String content = getFullContent(bureader);

		return content;
	}

	@Deprecated
	public static String getFullContent(InputStreamReader inputReader) {
		BufferedReader bur = new BufferedReader(inputReader);
		return getFullContent(bur);
	}

	/***
	 * 
	 * @param reader
	 * @return the content of reader
	 */
	@Deprecated
	public static String getFullContent(Reader reader) {
		BufferedReader br = new BufferedReader(reader);
		return getFullContent(br);
	}

	/**
	 * 
	 * @param reader
	 * @return
	 */
	@Deprecated
	public static String getFullContent(CharArrayReader reader) {
		BufferedReader bfreader = new BufferedReader(reader);
		return getFullContent(bfreader);
	}

	public static String getByteBufferContent4array(ByteBuffer byteb) {
		if (null == byteb) {
			return null;
		}
		byteb.flip();
		return new String(byteb.array());
	}

	/***
	 * read file ,convert file to byte array
	 * 
	 * @param file
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] readBytes4file(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		return readBytes3(in);

	}

	/***
	 * Has been tested
	 * 
	 * @param in
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] readBytes3(InputStream in) throws IOException {
		BufferedInputStream bufin = new BufferedInputStream(in);
		int buffSize = BUFFSIZE_1024;
		ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);

		// System.out.println("Available bytes:" + in.available());

		byte[] temp = new byte[buffSize];
		int size = 0;
		while ((size = bufin.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		bufin.close();
		in.close();
		byte[] content = out.toByteArray();
		return content;
	}

	/***
	 * get byte[] from <code>InputStream</code> Low efficiency
	 * 
	 * @param in
	 * @return
	 * @throws java.io.IOException
	 */
	@Deprecated
	public static byte[] readBytes2(InputStream in) throws IOException {
		byte[] temp = new byte[BUFFSIZE_1024];
		byte[] result = new byte[0];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			byte[] readBytes = new byte[size];
			System.arraycopy(temp, 0, readBytes, 0, size);
			result = SystemUtil.mergeArray(result, readBytes);
		}
		return result;
	}

	/***
	 * Has been tested
	 * 
	 * @param in
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] readBytes(InputStream in) throws IOException {
		byte[] temp = new byte[in.available()];
		byte[] result = new byte[0];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			byte[] readBytes = new byte[size];
			System.arraycopy(temp, 0, readBytes, 0, size);
			result = SystemUtil.mergeArray(result, readBytes);
		}
		return result;
	}

	/***
	 * 
	 * @param filepath
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] readBytes4file(String filepath) throws IOException {
		File file = new File(filepath);
		return readBytes4file(file);
	}

	/***
	 * get byte[] from file
	 * 
	 * @param filepath
	 *            : String
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] getBytes4File(String filepath) throws IOException {
		return readBytes4file(filepath);
	}

	/***
	 * get byte[] from file
	 * 
	 * @param filepath
	 *            : java.io.File
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] getBytes4File(File filepath) throws IOException {
		return readBytes4file(filepath);
	}

	public static String getByteBufferContent(ByteBuffer bytebuffer) {
		bytebuffer.flip();
		byte[] content = new byte[bytebuffer.limit()];
		bytebuffer.get(content);
		return (new String(content));
	}

	/**
	 * have closed reader
	 * 
	 * @param reader
	 * @return
	 */
	@Deprecated
	public static String getFullContent(BufferedReader reader) {
		StringBuilder sb = new StringBuilder();
		String readedLine = null;
		try {
			while ((readedLine = reader.readLine()) != null) {
				sb.append(readedLine);
				sb.append(SystemUtil.CRLF);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String content = sb.toString();
		int length_CRLF = SystemUtil.CRLF.length();
		if (content.length() <= length_CRLF) {
			return content;
		}
		return content.substring(0, content.length() - length_CRLF);//
	}

	public static String getFullContent2(File file, String charset)
			throws IOException {
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			return getFullContent2(fis, charset);
		} else {
			return null;
		}
	}

	public static String getFullContent2(InputStream in, String charset)
			throws IOException {
		int step = BUFFSIZE_1024;
		BufferedInputStream bis = new BufferedInputStream(in);

		// Data's byte array
		byte[] receData = new byte[step];

		// data length read from the stream
		int readLength = 0;

		// data Array offset
		int offset = 0;

		// Data array length
		int byteLength = step;

		while ((readLength = bis.read(receData, offset, byteLength - offset)) != -1) {
			// Calculate the current length of the data
			offset += readLength;
			// Determine whether you need to copy data , when the remaining
			// space is less than step / 2, copy the data
			if (byteLength - offset <= step / 2) {
				byte[] tempData = new byte[receData.length + step];
				System.arraycopy(receData, 0, tempData, 0, offset);
				receData = tempData;
				byteLength = receData.length;
			}
		}

		return new String(receData, 0, offset, charset);
	}

	/***
	 * 
	 * @param fileName
	 * @param charset
	 * @return
	 */
	public static String getFullContent(String fileName, String charset) {
		BufferedReader reader = null;
		try {
			File file = new File(fileName);

			return charset == null ? getFullContent(file) : getFullContent(
					file, charset);
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/***
	 * Note: use <code>SystemUtil.CHARSET_ISO88591</code>
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFullContent(String fileName) {
		return getFullContent(fileName, SystemUtil.CHARSET_ISO88591);
	}

	public static String getFullContent(File file) {
		return getFullContent(file, null);
	}

	public static String getFullContent(File file, String charset) {
		BufferedReader reader = null;
		if (!file.exists()) {
			System.out.println("getFullContent: file(" + file.getAbsolutePath()
					+ ") does not exist.");
			return null;
		}
		if (charset == null) {
			charset = SystemUtil.CHARSET_ISO88591;
		}
		try {
			reader = getBufferReaderFromFile(file, charset);
			return getFullContent(reader);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static BufferedWriter getBufferedWriter(String fileName) {
		FileWriter fileWrite;
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("getFullContent: file(" + file.getAbsolutePath()
					+ ") does not exist.");
			return null;
		}
		try {
			fileWrite = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWrite);
			return bufferedWriter;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedWriter getBufferedWriter(File file) {
		if (!file.exists()) {
			System.out.println("getFullContent: file(" + file.getAbsolutePath()
					+ ") does not exist.");
			return null;
		}
		FileWriter fileWrite;
		try {
			fileWrite = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWrite);
			return bufferedWriter;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeToFile(String fileName, String fileContent,boolean willCreateIfNotExist) {
		File file=new File(fileName);
		writeToFile(file, fileContent, willCreateIfNotExist);
	}
		
	/**
	 * first clear up this file ,and then write to it.
	 * 
	 * @param fileContent
	 * @param willCreateIfNotExist
	 */
	public static void writeToFile(File file, String fileContent,boolean willCreateIfNotExist) {
		if((!file.exists())&&willCreateIfNotExist){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedWriter bufferedWriter = getBufferedWriter(file);
		if (bufferedWriter == null) {
			System.out.println("writeToFile : bufferedWriter is null");
			return;
		}
		try {
			bufferedWriter.write(fileContent);
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void writeToFile(File fileName, String fileContent) {
		writeToFile(fileName, fileContent, true);
	}
	public static void writeToFile(String fileName, String fileContent) {
		writeToFile(fileName, fileContent, true);
	}
	public static void writeToFile(File file, StringBuffer stringbuf,
			String charset) {
		if (!file.exists()) {
			System.out.println("getFullContent: file(" + file.getAbsolutePath()
					+ ") does not exist.");
			return;
		}
		writeToFile(file, stringbuf.toString(), charset);
	}

	public static void writeToFile(String fileName, StringBuffer fileContent) {
		writeToFile(fileName, fileContent.toString());
	}

	public static void writeToFile(File file, String fileContent, String charset) {
		if (!file.exists()) {
			System.out.println("getFullContent: file(" + file.getAbsolutePath()
					+ ") does not exist.");
			return;
		}
		OutputStreamWriter osw = null;
		try {
			OutputStream os = new FileOutputStream(file);
			osw = new OutputStreamWriter(os, charset);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		BufferedWriter bufferedWriter = new BufferedWriter(osw);
		try {
			bufferedWriter.write(fileContent);
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * write inputstream into outputStream ,haven't close stream.
	 * 
	 * @param ins
	 * @param outs
	 */
	public static void writeIn2Output(InputStream ins, OutputStream outs,
			boolean isCloseOut, boolean isCloseInput) {
		try {
			int resultInt = -1;
			byte[] bytes = null;
			bytes = new byte[SystemUtil.BUFF_SIZE];

			try {
				while ((resultInt = ins.read(bytes)) != -1) {
					outs.write(bytes, 0, resultInt);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (isCloseOut) {
					try {
						outs.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (isCloseInput) {
					try {
						ins.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} finally {

		}
	}

	public static void writeIn2Output(InputStream ins, OutputStream outs) {
		writeIn2Output(ins, outs, true, false);
	}

	/***
	 * write inputstream into outputStream ,haven't close stream. slowly
	 * 
	 * @param ins
	 * @param outs
	 */
	public static void writeIn2Output2(InputStream ins, OutputStream outs) {
		try {
			int resultInt = -1;
			try {
				while ((resultInt = ins.read()) != -1) {
					outs.write(resultInt);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					outs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} finally {

		}
	}


	/***
	 * haven't close stream.
	 * 
	 * @param fileObj
	 * @param outs
	 * @throws java.io.FileNotFoundException
	 */
	public static InputStream writeFile2OutputStream(Object fileObj,
			OutputStream outs,
			boolean isCloseOut) throws FileNotFoundException {
		FileInputStream ins = null;
		if (fileObj instanceof String) {
			ins = new FileInputStream((String) fileObj);
		} else {
			ins = new FileInputStream((File) fileObj);
		}
		writeIn2Output(ins, outs,isCloseOut,false);
		return ins;
	}

	public static OutputStream writeToFile(File file, InputStream ins)
			throws FileNotFoundException {
		if (!file.exists()) {
			System.out.println("getFullContent: file(" + file.getAbsolutePath()
					+ ") does not exist.");
			return null;
		}
		FileOutputStream fileouts = new FileOutputStream(file);
		writeIn2Output(ins, fileouts);
		return fileouts;
	}

	public static void writeToFile(File file, InputStreamReader insr) {
		if (!file.exists()) {
			System.out.println("getFullContent: file(" + file.getAbsolutePath()
					+ ") does not exist.");
			return;
		}
		try {
			FileOutputStream fileouts = new FileOutputStream(file);
			int resultInt = -1;
			try {
				while ((resultInt = insr.read()) != -1) {
					fileouts.write(resultInt);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fileouts.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {

		}
	}

	/***
	 * write byte[] to file
	 * 
	 * @param bytes
	 * @param destFile
	 * @throws java.io.IOException
	 */
	public static void writeBytesToFile(byte[] bytes, File destFile)
			throws IOException {
		FileOutputStream out = new FileOutputStream(destFile);
		write2File(bytes, out);
	}

	/**
	 * Has been tested
	 * 
	 * @param bytes
	 * @param destFilepath
	 * @throws java.io.IOException
	 */
	public static void writeBytesToFile(byte[] bytes, String destFilepath)
			throws IOException {
		File destFile = new File(destFilepath);
		writeBytesToFile(bytes, destFile);
	}

	/***
	 * 
	 * @param bytes
	 * @param out
	 * @throws java.io.IOException
	 */
	public static void write2File(byte[] bytes, FileOutputStream out)
			throws IOException {
		out.write(bytes);
		out.close();
	}

	/**
	 * save Object to file
	 * 
	 * @param obj
	 *            Object to save
	 * @param fileName
	 * @throws Exception
	 */
	public static void writeObjectToFile(String fileName, Object obj)
			throws Exception {
		ObjectOutputStream output = new ObjectOutputStream(
				new FileOutputStream(fileName));
		output.writeObject(obj);
		output.close();
	}

	public static void writeObjectToOutputStream(OutputStream outs, Object obj)
			throws IOException {
		ObjectOutputStream output = new ObjectOutputStream(outs);
		output.writeObject(obj);
	}

	/**
	 * read object from file
	 * 
	 * @param fileName
	 * @return Object
	 * @throws Exception
	 */
	public static Object readFromFile(String fileName) throws Exception {
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(
				fileName));
		Object obj = input.readObject();
		input.close();
		return obj;
	}

	/**
	 * whether this file exist.
	 * @param filePath
	 * @return
	 */
	public static boolean isFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param files
	 */
	public static void printFileList(ArrayList<File> files) {
		if (null == files || files.size() == 0) {
			System.out.println(" ");
		}
		for (int i = 0; i < files.size(); i++) {
			String fileName = files.get(i).getName();
			System.out.println(fileName);
		}
	}



	/***
	 * Get size of file,byte
	 * 
	 * @param file
	 * @return
	 */
	public static long getFileSize2(File file) {
		return file.length();
	}

	/**
	 * 
	 * @return
	 * @throws java.io.IOException
	 */
	public static String getStringKeyboard() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}

	public static int getIntKeyboard() {
		Scanner sca = new Scanner(System.in);
		return sca.nextInt();
	}

	// public static String getWebAppAbPath_win(HttpSession session){
	// String appName = session.getServletContext().getServletContextName();
	// String contextPath = session.getServletContext().getRealPath("");
	// int startIndex = contextPath.indexOf(".");
	// int endIndex = contextPath.indexOf(appName);
	// StringBuffer sb = new StringBuffer();
	// sb.append(contextPath.substring(0, startIndex));
	// sb.append(contextPath.substring(endIndex));
	// return sb.toString();
	// }
	// public static String getWebAppAbPath_linux(HttpSession session){
	// String filePath=getWebAppAbPath_win(session);
	// return PathUtils.linuxPath(filePath);
	// }
	/**
	 * make directory recursely
	 * 
	 * @param file
	 * @return
	 */
	public static File mkdirRecurse(File file) {
		int count = countChar(file.getAbsolutePath(), File.separatorChar);
		for (int i = 0; i < count - 1; i++) {
			File parentFile = getParentFile(file, (count - i - 1));
			if (!parentFile.isDirectory()) {
				parentFile.mkdir();
			}
		}
		return file;
	}

	/**
	 * count times the given char appear in string
	 * 
	 * @param s
	 * @param c
	 * @return
	 */
	public static int countChar(String s, char c) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c) {
				count++;
			}
		}
		return count;
	}

	public static File getParentFile(File leafFile, int deep) {
		File parentFile = leafFile;
		for (int i = 0; i < deep; i++) {
			parentFile = parentFile.getParentFile();
		}
		return parentFile;
	}


	/***
	 * 获取指定目录下的所有的文件（不包括文件夹），采用了递归
	 * 
	 * @param obj
	 * @return
	 */
	public static ArrayList<File> getListFiles(Object obj) {
		File directory = null;
		if (obj instanceof File) {
			directory = (File) obj;
		} else {
			directory = new File(obj.toString());
		}
		ArrayList<File> files = new ArrayList<File>();
		if (directory.isFile()) {
			files.add(directory);
			return files;
		} else if (directory.isDirectory()) {
			File[] fileArr = directory.listFiles();
			for (int i = 0; i < fileArr.length; i++) {
				File fileOne = fileArr[i];
				files.addAll(getListFiles(fileOne));
			}
		}
		return files;
	}

	/***
	 * delete a directory/folder
	 * 
	 * @param someFile
	 *            :directory
	 */
	public static boolean deleteDir(File someFile) {
		if (!someFile.exists()) {
			System.out.println("[deleteDir]File " + someFile.getAbsolutePath()
					+ " does not exist.");
			return false;
		}
		if (someFile.isDirectory()) {// is a folder
			File[] files = someFile.listFiles();
			for (File subFile : files) {
				boolean isSuccess = deleteDir(subFile);
				if (!isSuccess) {
					return isSuccess;
				}
			}
		} else {// is a regular file
			boolean isSuccess = someFile.delete();
			if (!isSuccess) {
				return isSuccess;
			}
		}
		if (someFile.isDirectory()) {
			return someFile.delete();
		} else {
			return true;
		}
	}

	/***
	 * Formatted file size
	 * 
	 * @param size
	 * @return
	 */
	public static String formatFileSize(long size) {
		DecimalFormat df1 = (DecimalFormat) DecimalFormat.getInstance();
		df1.setGroupingSize(3);
		return df1.format(size);

	}


	/***
	 * 深拷贝对象
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T clone(T obj) {
		// 拷贝产生的对象
		T cloneObj = null;

		// 读取对象字节数据
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out
					.println("[FileUtils.clone]create ObjectOutputStream failed.");
		}
		try {
			oos.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[FileUtils.clone]writeObject " + obj
					+ " failed.");
		}
		try {
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 生成新对象
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bais);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			cloneObj = (T) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cloneObj;

	}

}
