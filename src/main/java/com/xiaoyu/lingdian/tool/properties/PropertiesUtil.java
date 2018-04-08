package com.xiaoyu.lingdian.tool.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	private static Map<String, String> propertiesMap;
	private static final String FILE = "config.properties";

	protected void init() {
		Properties props = new Properties();
		
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE);
			Reader reader = new InputStreamReader(in, "UTF-8");
			props.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}

		propertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			propertiesMap.put(keyStr, props.getProperty(keyStr));
		}
	}

	public static String getProperty(String name) {
		return propertiesMap.get(name);
	}

	/**
	 * 保存properties 文件
	 * 
	 * @param savedProp 需要保存的properties文件
	 * @param targetFile  保存文件路径
	 */
	public static void writeProperties(Properties savedProp, String targetFile) {
		writeProperties(savedProp, null, targetFile);
	}

	/**
	 * 保存properties文件
	 * 
	 * @param savedProp 需要保存的properties文件
	 * @param comments  properties文件注释
	 * @param targetFile 保存文件路径
	 */
	public static void writeProperties(Properties savedProp, String comments, String targetFile) {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(targetFile);
			savedProp.store(fout, comments);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fout != null) {
					fout.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取properties文件
	 * 
	 * @param fileName 文件路径
	 * @return
	 */
	public static Properties getProperties(String fileName) {
		Properties result = new Properties();// 属性集合对象
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			result.load(fis);// 将属性文件流装载到Properties对象中
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}