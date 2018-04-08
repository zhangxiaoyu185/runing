package com.xiaoyu.lingdian.tool;

import java.util.Date;
import java.util.Random;

public class RandomUtil {

	public static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBER_CHAR = "0123456789";
	
	/**
	 * 返回一个定长的随机字符串(包含大小写字母、数字)
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		if (length > 12) {
			String strDate = DateUtil.getToday(DateUtil.NOCHAR_PATTERN);
			sb.append(strDate.substring(2));			
			int syLength = length - 12;
			for (int i = 0; i < syLength; i++) {
				sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
			}
		} else {
			for (int i = 0; i < length; i++) {
				sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
			}
		}
		
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机字符串(包含大小写字母、数字)
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateStringOther(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		if (length > 12) {
			String strDate = DateUtil.formatDate(DateUtil.NOCHAR_PATTERN, DateUtil.addMinute(new Date(), -5));
			sb.append(strDate.substring(2));
			int syLength = length - 12;
			for (int i = 0; i < syLength; i++) {
				sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
			}
		} else {
			for (int i = 0; i < length; i++) {
				sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(LETTER_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯数字
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixNum(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
		}
		return sb.toString();
	}
	
	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 * @param length 字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/** 
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * @param num 数字
	 * @param fixdlenth 字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
					+ "的字符串发生异常!");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * @param num 数字
	 * @param fixdlenth 字符串长度
	 * @return 定长的字符串
	 */

	public static String toFixdLengthString(int num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
					+ "的字符串发生异常!");
		}
		sb.append(strNum);
		return sb.toString();
	}
	
    //生产一个指定长度的随机字符串（字母和数字相间隔）
    public static String getCharacterNumber(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)){// 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) {// 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
    
	/**
	 * 根据输入的字符串范围，生成指定长度的随机字符串
	 * 
	 * @param strPool
	 * @param length
	 * @return
	 */
	public static String randomString(String strPool, int length) {
		if (strPool == null || length < 1) {
			return null;
		}

		Random randGen = new Random();
		char[] numbersAndLetters = (strPool).toCharArray();

		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(strPool.length())];
		}
		return new String(randBuffer);
	}

	/**
	 * 生成特定前缀的唯一字符串
	 * 
	 * @param prefix
	 * @return
	 */
	public static String generateUniqueSequence(String prefix) {
		return prefix + System.nanoTime();
	}

	/**
	 * 取出一个指定长度大小的随机正整数
	 * 
	 * @param length int 设定所取出随机数的长度,length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

    public static void main(String[] args) {
    	for (int i = 0; i < 10; i++) {
    		System.out.println(generateString(16));
		}
	}
 
}