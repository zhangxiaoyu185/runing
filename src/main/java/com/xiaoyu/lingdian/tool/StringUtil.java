package com.xiaoyu.lingdian.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoyu.lingdian.tool.DateUtil;

public class StringUtil {

	private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 字符串转换成double 数
	 * 
	 * @param value
	 * @return
	 */
	public static double toDouble(String value) {
		if (value == null || value.trim().length() == 0) {
			return 0d;
		}
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			return 0d;
		}
	}

	/**
	 * 字符串转Integer
	 * 
	 * @param value
	 * @return
	 */
	public static int toInt(String value) {
		if (value == null || value.trim().length() == 0) {
			return 0;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 把指定的数据转化为16进制格式的字符串
	 * 
	 * @param data 待转化的数据
	 * @return 16进制表示的数据
	 */
	public static String toHexString(byte[] data) {
		return toHexString(data, 0, data.length);
	}

	/**
	 * 把指定的数据转化为16进制格式的字符串， 如toHexString({8,9,12,4},1,3) = "090C"
	 * 
	 * @param data 待转化的数据
	 * @param beginIndex 需转化的数据的起始索引
	 * @param endIndex 需转化的数据的结束索引
	 * @return 16进制表示的数据
	 */
	public static String toHexString(byte[] data, int beginIndex, int endIndex) {
		if (data == null || beginIndex < 0)
			return null;
		StringBuilder strBuilder = new StringBuilder();
		for (int i = beginIndex; i < endIndex; i++) {
			if (data[i] < 0) {
				data[i] += 256;
            }
			strBuilder.append(hexDigits[data[i] >>> 4 & 0xf]);
			strBuilder.append(hexDigits[data[i] & 0xf]);
		}
		return strBuilder.toString();
	}

	/**
	 * 使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param message 要格式化的字符串
	 * @param params 参数表
	 * @return 格式化的字符串，如果message为<code>null</code>，则返回<code>null</code>
	 */
	public static String formatMessage(String message, Object... params) {
		if ((message == null) || (params == null) || (params.length == 0)) {
			return message;
		}

		return MessageFormat.format(message, params);
	}

	/**
	 * 使用java正则表达式去掉多余的.与0
	 * 
	 * @param value
	 * @return
	 */
	public static String subZeroAndDot(String value) {
		if (value.indexOf(".") > 0) {
			value = value.replaceAll("0+?$", "");// 去掉多余的0
			value = value.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return value;
	}

	/**
	 * 隐藏用户的名字(张**)
	 * 
	 * @param fullName
	 * @return
	 */
	public static String maskUserName(String fullName) {
		if (StringUtil.isEmpty(fullName)) {
			return "";
		}
		char firstName = fullName.charAt(0);

		return firstName + "**";
	}

	/**
	 * 返回规定数字的文字内容，多余的补“...”
	 * 
	 * @param longContent
	 * @param showNum
	 * @return
	 */
	public static String maskLongString(String longContent, int showNum) {
		if (longContent == null)
			return "";
		if (longContent.length() < showNum)
			return longContent;
		String result = longContent.substring(0, showNum) + "...";
		return result;
	}

	/**
	 * 用有效地身份证号，获得年龄的字符串
	 * 
	 * @param idCard
	 * @return
	 */
	public static String idCardToAgeStr(String idCard) {
		int age = 0;
		String yearStr = null;
		int nowYear = DateUtil.getYear(new Date());
		if (idCard != null && idCard.trim() != "") {
			yearStr = idCard.trim().substring(6, 10);
			if (isNumber(yearStr)) {
				age = nowYear - Integer.parseInt(yearStr);
			}
		}
		return String.valueOf(age);
	}

	/**
	 * 字符串转HTML格式
	 * 
	 * @param value
	 * @return
	 */
	public static String escapeHTML(String value) {
		if (value == null || value.length() == 0) {
			return value;
		}
		StringBuffer result = null;
		String filtered = null;
		for (int i = 0; i < value.length(); i++) {
			filtered = null;
			switch (value.charAt(i)) {
			case '<':
				filtered = "&lt;";
				break;
			case '>':
				filtered = "&gt;";
				break;
			case '&':
				filtered = "&amp;";
				break;
			case '"':
				filtered = "&quot;";
				break;
			case '\'':
				filtered = "&#39;";
				break;
			}
			if (result == null) {
				if (filtered != null) {
					result = new StringBuffer(value.length() + 50);
					if (i > 0) {
						result.append(value.substring(0, i));
					}
					result.append(filtered);
				}
			} else {
				if (filtered == null) {
					result.append(value.charAt(i));
				} else {
					result.append(filtered);
				}
			}
		}

		return result == null ? value : result.toString();
	}

	/**
	 * 去除包含HTML标签的字符串，如果文本超过length个字符，多余的将用replaceStr替换显示
	 * 
	 * @param sourceStr
	 * @param length
	 * @param replaceStr
	 * @return Object
	 * 
	 */
	public static String abbreviate(String sourceStr, int length, String replaceStr) {
		if (sourceStr == null || "".equals(sourceStr)) {
			return "";
		}
		String destStr = Html2Text(sourceStr);
		if (destStr.length() > length) {
			destStr = destStr.substring(0, length - 1) + replaceStr;
		}
		return destStr;
	}

	/**
	 * 去除包含HTML标签的字符串
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String Html2Text(String htmlStr) {
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			String regEx_html = "<[^>]+>";
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签
			htmlStr = htmlStr.replaceAll("&nbsp;", "");
			textStr = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		textStr = textStr.replaceAll("&amp;", "&");
		textStr = textStr.replaceAll("&lt;", "<");
		textStr = textStr.replaceAll("&gt;", ">");
		textStr = textStr.replaceAll("&quot;", "\"");
		return textStr;
	}

	/**
	 * 两个字符串金额相加
	 * 
	 * @param firstStr
	 * @param secStr
	 * @return
	 */
	public static String addAsDouble(String firstStr, String secStr) {
		double resultDoubleVal = 0.0;
		double firstDoubleVal = 0.0;
		double secDoubleVal = 0.0;
		String result = null;
		if (firstStr != null && !"".equals(firstStr)) {
			firstDoubleVal = Double.parseDouble(firstStr);
		}
		if (secStr != null && !"".equals(secStr)) {
			secDoubleVal = Double.parseDouble(secStr);
		}
		resultDoubleVal = firstDoubleVal + secDoubleVal;
		result = String.valueOf(resultDoubleVal);
		result = getUniversalFeeFormat(result);
		return result;
	}

	/**
	 * 字符串形式的金额统一显示2位小数点
	 * 
	 * @param value
	 * @return
	 */
	public static String getUniversalFeeFormat(String value) {
		String resultStr = "";
		if (StringUtil.isEmpty(value))
			return "";
		int dotLoc = value.indexOf(".");
		int length = value.length();
		if (dotLoc == -1) {
			// 没有小数点，则补充2位
			resultStr = value + ".00";
		} else if (dotLoc == 0) {
			// 没有小数点，如.2756或.1
			resultStr = "0" + value;
			resultStr = getUniversalFeeFormat(resultStr);
		} else if (length - dotLoc == 2) {
			// 如3.4，将它转化为3.40
			resultStr = value + "0";
		} else if (length - dotLoc == 3) {
			// 如3.40，不需要改变
			resultStr = value;
		} else if (length - dotLoc > 3) {
			// 如3.14159265
			resultStr = value.substring(0, dotLoc + 3);
		} else if (length - dotLoc == 1) {
			// 如312. 较少见，补充00
			resultStr = value + "00";
		}

		return resultStr;
	}

	/**
	 * 字符串解析成数组
	 * 根据分隔符来分割数字串 example:"1-9",解析后结构就是[1,2,3,4,5,6,7,8,9], 
	 * "1;2;4;5"分割后结果就是[1,2,4,5], 暂时只支持这两种格式的解析
	 * 
	 * @param value
	 * @return
	 */
	public static String[] parseNumArea(String value) {
		String[] noArray;
		if (value.indexOf("-") > 0) {
			String[] nums = value.split("-");
			int first = Integer.parseInt(nums[0]);
			int last = Integer.parseInt(nums[1]);
			noArray = new String[last - first + 1];
			for (int i = 0, j = first; j <= last; i++, j++) {
				noArray[i] = j + "";
			}
		} else {
			noArray = value.split(";");
		}

		return noArray;
	}

	/**
	 * URL编码(UTF-8)
	 * 
	 * @param source
	 * @return String
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据类型判断文件扩展名 
	 * 
	 * @param contentType 内容类型 
	 * @return String
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType)) {
			fileExt = ".jpg";
		}
		else if ("audio/mpeg".equals(contentType)) {
			fileExt = ".mp3";
		}
		else if ("audio/amr".equals(contentType)) {
			fileExt = ".amr";
		}
		else if ("video/mp4".equals(contentType)) {
			fileExt = ".mp4";
		}
		else if ("video/mpeg4".equals(contentType)) {
			fileExt = ".mp4";
		}
		return fileExt;
	}

	/**
	 * 验证邮箱
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmail(String value) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(value);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 当判断数组是为null或长度为0是返回 <code>true</code>
	 * 
	 * @param args
	 * @return
	 */
	public static boolean isEmpty(Object[] args) {
		return args == null || args.length == 0;
	}

	/**
	 * 字符串是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}
	
	/**
	 * 是否是浮点数
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isFloat(String value) {
		if (isEmpty(value)) {
			return false;
		}

		for (int i = 0; i < value.length(); i++) {
			if ((value.charAt(i) > '9' || value.charAt(i) < '0')
					&& value.charAt(i) != '.' && value.charAt(i) != '-') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否是数字
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNumber(String value) {
		if (isEmpty(value)) {
			return false;
		}

		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) > '9' || value.charAt(i) < '0') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否是浮点数
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isDoubleString(String value) {
		try {
			Double.valueOf(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 是否是在范围内的数字
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean isNumber(String value, int min, int max) {
		if (!isNumber(value)) {
			return false;
		}

		int number = Integer.parseInt(value);
		return number >= min && number <= max;
	}

	/**
	 * 是否在长度内的字符串
	 * 
	 * @param value
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean isString(String value, int minLength, int maxLength) {
		if (value == null) {
			return false;
		}

		if (minLength < 0) {
			return value.length() <= maxLength;
		}
		else if (maxLength < 0) {
			return value.length() >= minLength;
		}
		else {
			return value.length() >= minLength && value.length() <= maxLength;
		}
	}

	/**
	 * 是否是中文
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}

		return false;
	}
	
	/**
	 * 是否是时分秒格式(10:10:10)
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isTime(String value) {
		if (isEmpty(value) || value.length() > 8) {
			return false;
		}

		String[] items = value.split(":");

		if (items.length != 2 && items.length != 3) {
			return false;
		}

		for (int i = 0; i < items.length; i++) {
			if (items[i].length() != 2 && items[i].length() != 1) {
				return false;
			}
		}

		return !(!isNumber(items[0], 0, 23) || !isNumber(items[1], 0, 59) || (items.length == 3 && !isNumber(items[2], 0, 59)));
	}

	/**
	 * 手机号码验证
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isMobile(String value) {
		/**^[1][3,4,5,8][0-9]{9}$**/
		boolean flag = false;
		try {
			Pattern regex = Pattern.compile("^(((13[0-9])|(17[0-9])|(15([0-3]|[5-9]))|(18[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(value);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 电话号码验证(座机,区号可带可不带)
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isPhone(String value) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (value.length() > 9) {
			m = p1.matcher(value);
			b = m.matches();
		} else {
			m = p2.matcher(value);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 获取随机字符串
	 */
	public static String create_nonce_str() {
		//MD5Util.MD5Encode()
	    return UUID.randomUUID().toString();
	}
	
	/**
	 * 获取随机字符串
	 */
	public static String getNonceStr() {
		// 随机数
		String currTime = DateUtil.getToday(DateUtil.NOCHAR_PATTERN);
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = RandomUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}
	
	/**
	 * 元转换成分
	 * 
	 * @param money
	 * @return
	 */
	public static String getMoney(String amount) {
		if (amount == null) {
			return "";
		}
		// 金额转化为分为单位
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含 ￥  或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}

	/**
	 * 获取时间串
	 */
	public static String create_timestamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
	/**
	 * 获取编码字符集
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getCharacterEncoding(HttpServletRequest request,
			HttpServletResponse response) {	
		if(null == request || null == response) {
			return "gbk";
		}		
		String enc = request.getCharacterEncoding();
		if(null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}		
		if(null == enc || "".equals(enc)) {
			enc = "gbk";
		}		
		return enc;
	}
	
	/**
	 * 替换乱码的字符
	 * 
	 * @param strName
	 * @return
	 */
	public static String replaceMessy(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					ch[i] = '?';
				}
			}
		}
		return new String(ch);
	}

	/**
	 * 生成账号
	 * 
	 * @param acount
	 * @return
	 */
	public static String nextAcounnt(String acount) {
		String newAcc = "";
		if (Integer.parseInt(acount) < 10000) {
			Integer newAc = Integer.parseInt(acount) + 1;
			if (newAc < 1000) {
				int count = String.valueOf(newAc).length();
				if (count == 1) {
					newAcc = "000" + newAc;
				} else if (count == 2) {
					newAcc = "00" + newAc;
				} else if (count == 3) {
					newAcc = "0" + newAc;
				}
			} else {
				newAcc = String.valueOf(newAc);
			}
		} else {
			newAcc = acount;
		}
		return newAcc;
	}

	/**
	 * 是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str != null && !"".equals(str) && str.length() <= 9) {
			Pattern pattern = Pattern.compile("[0-9]*");
			return pattern.matcher(str).matches();
		} else {
			return false;
		}
	}

	/**
	 * 生成流水号
	 * 
	 * @param t 流水号位数
	 * @return 流水号
	 */
	public static String getSequenceNumber(int t) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.NOCHAR_PATTERN);
		String str = sdf.format(d);
		String haomiao = String.valueOf(System.nanoTime());
		str = str + haomiao.substring(haomiao.length() - 6, haomiao.length());
		return str.substring(str.length() - t, str.length());
	}

	/**
	 * 字节转字符
	 * @param hash
	 * @return
	 */
	public static String byteToHex(final byte[] hash) {
	    Formatter formatter = new Formatter();
	    for (byte b : hash) {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}

	/**
	 * 获取最后一个Regex后的字符,例如/
	 * 
	 * @param strPool
	 * @param length
	 * @return
	 */
	public static String getRegexAfterString(String value, String regex) {
		int i = value.lastIndexOf(regex);
		if (i > 0) {
			return value.substring(i);
		}
		return "";
	}

}