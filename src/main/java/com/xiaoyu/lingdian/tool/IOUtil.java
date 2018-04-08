package com.xiaoyu.lingdian.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class IOUtil {

	private static Logger logger = LoggerFactory.getLogger("BASE_LOGGER");
	
	private static final int BUFFER_SIZE = 16 * 1024;
	private static final int BUFFER = 1024;
	
	/**
	 * 读取一批日志文件中有某一特殊内容的行
	 * 
	 * @param fileDirPath
	 * @param logStrPath
	 * @param content
	 * @param date
	 * @return
	 */
	public static List<String> operation(String fileDirPath, String logStrPath, String content, String date) {		
		if(fileDirPath == null || ("").equals(fileDirPath)) {
			return null;
		}
		if(date != null) {
			logStrPath += "." + date;
		}

		List<String> contentList = new ArrayList<String>();
		File[] files = findFileByRegex(new File(fileDirPath), logStrPath);
		for(int i=0; i<files.length; i++){
			try {
				InputStreamReader f = new InputStreamReader(new FileInputStream(files[i]), "GBK");
				BufferedReader in = new BufferedReader(f);
				String line;
				while ((line = in.readLine()) != null) {
					if(line.contains(content)) {
						contentList.add(line);
					}
				}
				in.close();
			} catch (IOException e) {				
				e.printStackTrace();
			} finally {				
			}			
		}
		return contentList;
	}
	
	/**
	 * 查询目录下特殊文件名的文件
	 * 
	 * @param dir
	 * @param pattern
	 * @return
	 */
	public static File[] findFileByRegex(File dir, String pattern) {
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return new File[0]; // 返回空数组表示没有文件
		}
		ArrayList<File> list = new ArrayList<File>();
		for (File file : files) {
			if (file.isFile()) {
				if (file.getName().matches(pattern)) {
					list.add(file);
				} else {
					// 递归在目录file查找
					File[] arr = findFileByRegex(file, pattern);
					Collections.addAll(list, arr);
				}
			}
		}
		return list.toArray(new File[0]);
	}
	
	/**
	 * 获取本地有效盘符(C盘等)
	 * @return
	 */
	public static File[] getRoot() {
		File[] roots = File.listRoots();
		return roots;
	}

	/**
	 * 获取路径下的所有文件(去除了根路径)
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> getFileList(String path) {
		LinkedList<File> list = new LinkedList<File>();
		ArrayList<String> listPath = new ArrayList<String>();
		File dir = new File(path);
		File file[] = dir.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory())
				list.add(file[i]);
			else {
				listPath.add(file[i].getName());
			}
		}
		File tmp;
		while (!list.isEmpty()) {
			tmp = list.removeFirst(); // 移除并返回集合中第一项
			if (tmp.isDirectory()) {
				file = tmp.listFiles();
				if (file == null)
					continue;
				for (int i = 0; i < file.length; i++) {
					if (file[i].isDirectory())
						list.add(file[i]);
					else {
						listPath.add(file[i].getName());
					}

				}
			} else {

			}
		}
		return listPath;
	}
	
	/**
	 * 按过滤器查找目录下文件
	 * 
	 * @param dir
	 * @param filter
	 * @return
	 */
	public static File[] findFileByFilter(File dir, FileFilter filter) {
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return new File[0];
		}
		ArrayList<File> list = new ArrayList<File>();
		for (File file : files) {
			if (file.isFile()) {
				if (filter.accept(file)) { // 用过滤器判断文件是否符合条件
					list.add(file);
				}
			} else {
				File[] arr = findFileByFilter(file, filter);
				Collections.addAll(list, arr);
			}
		}
		return list.toArray(new File[0]);
	}
	
	/**
	 * 文件目录大小
	 * 
	 * @param dir
	 * @return
	 */
	public static long dirLength(File dir) {
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) { // 如果没有权限
			return 0;
		}
		long size = 0;
		for (File file : files) {
			if (file.isFile()) {
				size += file.length();
			} else {
				size += dirLength(file); // 求目录file的大小
			}
		}
		return size;
	}	

	/**
	 * 读文件
	 * 
	 * @param filePathName
	 * @return
	 * @throws IOException
	 */
	public static String read(String filePathName) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File(filePathName)));
		StringBuffer sb = new StringBuffer();
		String line;
		int i = 0;
		while ((line = in.readLine()) != null) {
			if (i > 0) {
				sb.append("\n");
			}
			sb.append(line);
			i++;
		}
		in.close();
		return sb.toString();
	}
	
	/**
	 * 读文件
	 * 
	 * @param filePathName
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> readToMap(String filePathName) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File(
				filePathName)));
		Map<String, String> map = new HashMap<String, String>();

		String line;
		while ((line = in.readLine()) != null) {
			String[] list = line.split("=");
			map.put(list[0], list[1]);
		}
		in.close();
		return map;
	}
	
	/** 
     * 写文件
     *  
     * @param filePath 
     * @param content 
     */  
    public static void write(String filePath, String content) {    	
    	BufferedWriter bw = null;          
        try {
        	File file = new File(filePath);
    		if(!file.exists()){
        		file.createNewFile();
        	}
            // 根据文件路径创建缓冲输出流  
            bw = new BufferedWriter(new FileWriter(filePath));  
            // 将内容写入文件中  
            bw.write(content);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭流  
            if (bw != null) {  
                try {  
                    bw.close();  
                } catch (IOException e) {  
                    bw = null;  
                }  
            }  
        }  
    }
    
    /**
     * 文件字节拷贝
     * 
     * @param from
     * @param path
     * @throws IOException
     */
	public static void copy(String from, String path) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File(from));
		FileOutputStream outputStream = new FileOutputStream(new File(path));
		byte[] buff = new byte[1024];
		int n;
		while ((n = inputStream.read(buff)) != -1) {
			outputStream.write(buff, 0, n);
		}
		inputStream.close();
		outputStream.close();
	}

	/**
	 * 文件字节拷贝
	 *
	 * @param src 源文件
	 * @param dst 目标文件
	 * @throws Exception
	 */
	public static void copy(File src, File dst) throws Exception {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 文件字符拷贝
	 * 
	 * @param from
	 * @param to
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static void copyCharacter(File from, File to) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(from));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(to)));
		String line;
		while ((line = in.readLine()) != null) {
			out.println(line);
		}
		in.close();
		out.close();
	}

	/**
	 * 文件转成字节流
	 * 
	 * @param fileName 文件完整路径
	 * @return
	 */
	public static byte[] getBytes(String fileName) {
        if(!isExist(fileName)){
            return null;
        }
		try {
			BufferedInputStream buf = new BufferedInputStream(
					new FileInputStream(fileName));
			ByteArrayOutputStream outByte = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = buf.read(b, 0, 1024)) != -1) {
				outByte.write(b, 0, i);
			}
			outByte.flush();
			buf.close();
			return outByte.toByteArray();
		} catch (IOException e) {
			logger.error("Read file error! ", e);
			return null;
		}
	}
	
    /** 
     * 字节保存文件
     */  
    public static void writeBytes(String filePath, byte[] data) {
    	File saveFile = new File(filePath);
		if(saveFile.exists()){
			saveFile.delete();
		}
        BufferedOutputStream bufferOutput = null;    
        try {  
            bufferOutput = new BufferedOutputStream(new FileOutputStream(  
                    new File(filePath)), BUFFER); 
            bufferOutput.write(data);  
            bufferOutput.flush();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        } finally {  
            if (bufferOutput != null) {  
                try {  
                    bufferOutput.close();  
                } catch (Exception e) {  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
    }

	/**
	 * 文件夹字节拷贝
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			createFolder(newPath);
			File oldfile = new File(oldPath);
			String[] file = oldfile.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[BUFFER_SIZE];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断文件或文件夹是否存在
	 * 
	 * @param path
	 */
	public static boolean isExist(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 */
	public static void createFile(String path) {
		File file = new File(path); 
		File parent = file.getParentFile(); 
		if(parent != null && !parent.exists()){ 
			parent.mkdirs(); 
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除文件
	 * 
	 * @param pathname 文件名（包括路径）
	 */
	public static void deleteFile(String pathname){
		try {
			File file = new File(pathname);
			if(file.isFile() && file.exists()){
				file.delete();
			} else{
				logger.error("File["+ pathname +"] not exists!");
			}
		} catch (Exception e) {
			logger.error("删除文件["+ pathname +"]失败！");
		}		
	}

	/**
	 * 文件重命名
	 * 
	 * @param oldpath
	 * @param newPath
	 */
	public static void renamePath(String oldpath, String newPath) {
		File file = new File(oldpath);
		File files = new File(newPath);
		if (!files.exists()) {
			file.renameTo(files);
		}
	}
	
	/**
	 * 创建目录
	 * 
	 * @param strPath
	 */
	public static void createFolder(String filePath) {
		try {
			File file = new File(filePath);
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除目录
	 * 
	 * @param file
	 */
	public static void deleteDirs(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteDirs(files[i]);
				}
				file.delete();
			}
		}
	}

	/**
	 * 文件夹路径补‘/’
	 * 
	 * @param folder
	 * @return
	 */
	public static String completeFolder(String folder) {
		if (!File.separator.equals(folder.substring(folder.length() - 1))) {
			folder = folder + File.separator;
		}
		return folder;
	}
	
	/**
	 * 获取文件扩展名
	 *
	 * @param fileName 文件名
	 * @return
	 */
	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}
	
	/**
	 * 单个文件转码拷贝
	 * 
	 * @param from
	 * @param to
	 * @param fromCharset
	 * @param toCharset
	 * @throws Exception
	 */
	public static void turnCode(File from, File to, String fromCharset, String toCharset) throws Exception {
		InputStreamReader in = new InputStreamReader(new FileInputStream(from), fromCharset);
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(to), toCharset);
		char[] buff = new char[1024];
		int n;
		while ((n = in.read(buff)) != -1) {
			String str = new String(buff, 0, n);
			out.write(str);
			System.out.println(str);
		}
		in.close();
		out.close();
	}

	/**
	 * 目录中所有文件进行编码拷贝
	 * 
	 * @param fromDir
	 * @param toDir
	 * @param fromCharset
	 * @param toCharset
	 * @throws Exception
	 */
	public static void dirCopy(File fromDir, File toDir, String fromCharset, String toCharset) throws Exception {
		InputStreamReader in;
		OutputStreamWriter out;
		File[] files = fromDir.listFiles();
		toDir.mkdir();
		if (files == null || files.length == 0) {
			return;
		}
		for (File file : files) {
			if (file.isFile()) {
				in = new InputStreamReader(new FileInputStream(file), fromCharset);
				out = new OutputStreamWriter(new FileOutputStream(new File(toDir + "/" + file.getName())), toCharset);
				char[] buff = new char[1024];
				int n;
				while ((n = in.read(buff)) != -1) {
					String str = new String(buff, 0, n);
					out.write(str);
				}
				in.close();
				out.close();
			} else {
				dirCopy(file, new File(toDir + "/" + file.getName()), fromCharset, toCharset);
			}
		}
	}
	
	/**
	 * 文件编码格式转换
	 * 
	 * @param fileStr
	 * @param fileSave
	 * @param chasrset
	 */
	public static void setEnd(String fileStr, String fileSave, String chasrset) {
		try {
			File file = new File(fileStr);
			FileInputStream fis = new FileInputStream(file);
			byte byt[] = new byte[1024];
			File filea = new File(fileSave);
			filea.createNewFile();
			FileOutputStream fop = new FileOutputStream(filea);
			String str = "";
			int read = -1;
			while ((read = fis.read(byt)) != -1) {
				str = new String(byt, 0, read, chasrset);
				fop.write(str.getBytes());
			}
			fop.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件自定义加密
	 * 
	 * @param from
	 * @param to
	 * @throws IOException
	 */
	public static void encript(File from, File to) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(from));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(to));
		int b;
		while ((b = in.read()) != -1) {
			b = b ^ 0xFF;
			out.write(b);
		}
		in.close();
		out.flush();
		out.close();
	}

	/**
	 * 文件自定义解密
	 * 
	 * @param from
	 * @param to
	 * @throws IOException
	 */
	public static void decript(File from, File to) throws IOException {
		encript(from, to);
	}

	/**
	 * 字节URL加密
	 * 
	 * @param s
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String urlEncode(String s, String charset) throws Exception {
		byte[] arr = s.getBytes(charset);
		StringBuffer sb = new StringBuffer();
		for (byte b : arr) {
			if ((b >= 'a' && b <= 'z') || (b >= 'A' && b <= 'Z')
					|| (b >= '0' && b >= '9') || b == '.' || b == '-'
					|| b == '*' || b == '_') {
				sb.append((char) b);
			} else if (b == ' ') {
				sb.append('+');
			} else {
				String hex = Integer.toHexString(b & 0xFF);
				hex = "%" + (hex.length() == 1 ? "0" : "") + hex;
				sb.append(hex);
			}
		}
		return sb.toString();
	}

	/**
	 * 字节URL解密
	 * 
	 * @param s
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String urlDecode(String s, String charset) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (int i = 0; i < s.length();) {
			char c = s.charAt(i);
			if ('+' == c) {
				out.write(' ');
				i++;
			} else if ('%' == c) {
				StringBuffer sb = new StringBuffer();
				sb.append(s.charAt(++i));
				sb.append(s.charAt(++i));
				int b = Integer.parseInt(sb.toString(), 16);
				out.write(b);
				i++;
			} else {
				out.write(c);
				i++;
			}
		}
		byte[] arr = out.toByteArray(); // 获取生成的字节数组
		String str = new String(arr, charset);
		return str;
	}
	
	/**
	 * 文件拆分
	 * 
	 * @param commFile 源文件
	 * @param untieFile 拆分后的路径
	 * @param filesize 拆分大小，单位KB
	 */
	public static void fenGe(File commFile, File untieFile, int filesize) {
		FileInputStream fis = null;
		int size=1024*1024;
		try {
			if (!untieFile.isDirectory()) {
				untieFile.mkdirs();
			}
			size = size * filesize;
			int length = (int) commFile.length();
			int num = length / size;
			int yu = length % size;
			String newfengeFile = commFile.getAbsolutePath();
			int fileNew = newfengeFile.lastIndexOf(".");
			String strNew = newfengeFile.substring(fileNew, newfengeFile.length());
			fis = new FileInputStream(commFile);
			File[] fl = new File[num + 1];
			int begin = 0;
			for (int i = 0; i < num; i++) {
				fl[i] = new File(untieFile.getAbsolutePath() + "\\" + (i + 1)
						+ strNew + ".tem");
				if (!fl[i].isFile()) {
					fl[i].createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(fl[i]);
				byte[] bl = new byte[size];
				fis.read(bl);
				fos.write(bl);
				begin = begin + size * 1024 * 1024;
				fos.close();
			}
			if (yu != 0) {
				fl[num] = new File(untieFile.getAbsolutePath() + "\\"
						+ (num + 1) + strNew + ".tem");
				if (!fl[num].isFile()) {
					fl[num].createNewFile();
				}
				FileOutputStream fyu = new FileOutputStream(fl[num]);
				byte[] byt = new byte[yu];
				fis.read(byt);
				fyu.write(byt);
				fyu.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * 文件合并
	 * 
	 * @param file:要进行分割的文件数组对象
	 * @param cunDir：合并后文件的保存路径
	 * @param type：合并后的文件名
	 */
	public static void heBing(File[] file, File cunDir, String fileName) {
		try {
			File heBingFile = new File(cunDir.getAbsoluteFile() + "\\"+ fileName);
			if (!heBingFile.isFile()) {
				heBingFile.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(heBingFile);
			for (int i = 0; i < file.length; i++) {
				FileInputStream fis = new FileInputStream(file[i]);
				int len = (int) file[i].length();
				byte[] bRead = new byte[len];
				fis.read(bRead);
				fos.write(bRead);
				fis.close();
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件拆分
	 * 
	 * @param src
	 * @param size
	 * @throws IOException
	 */
	public static void split(File src, int size) throws IOException {
		DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(src)));
		int i = 1;
		File file = new File(src + "." + i);
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(file)));
		int b = 2;
		while (in.available() > 0) {
			System.out.println(in.read());
			out.write(in.read());
			out.flush();
			if (b % size == 0) {
				i++;
				out.close();
				file = new File(src + "." + i);
				out = new DataOutputStream(new BufferedOutputStream(
						new FileOutputStream(file)));
			}
			b += 2;
		}
		out.close();
		in.close();
	}

	/**
	 * 文件合并
	 * 
	 * @param files
	 * @param target
	 * @throws IOException
	 */
	public static void concat(File[] files, File target) throws IOException {
		DataInputStream in = null;
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(target)));
		for (File file : files) {
			in = new DataInputStream(new BufferedInputStream(
					new FileInputStream(file)));
			while (in.available() > 0) {
				out.write(in.read());
			}
		}
		out.close();
		in.close();
	}

	/**
	 * 将文件转成base64 字符串
	 * 
	 * @param path文件路径
	 * @return
	 */
	public static String encodeBase64File(String path) {
		File file = new File(path);
		FileInputStream inputFile;
		try {
			inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return new BASE64Encoder().encode(buffer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "";
	}

	/**
	 * 将base64字符解码保存文件
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void decoderBase64File(String base64Code, String targetPath) {
		byte[] buffer;
		try {
			buffer = new BASE64Decoder().decodeBuffer(base64Code);
			FileOutputStream out = new FileOutputStream(targetPath);
			out.write(buffer);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}