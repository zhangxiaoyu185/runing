package com.xiaoyu.test;

import com.xiaoyu.lingdian.tool.DateUtil;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Test {

	public static String transferLongToDate(String dateFormat,Long millSec){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date= new Date(millSec);
		return sdf.format(date);
    }

	public static void main(String[] args) {		
		System.out.println(transferLongToDate("yyyy-MM-dd HH:mm:ss", 1429459200000l));
		System.out.println(transferLongToDate("yyyy-MM-dd HH:mm:ss", 1429545600000l));
		System.out.println(transferLongToDate("yyyy-MM-dd HH:mm:ss", 1429459200000l));

		String valueday = new SimpleDateFormat(DateUtil.DEFAULT_PATTERN, Locale.CHINA).format(new Date(1523548800l*1000));
		System.out.println(valueday);

		System.out.println(TimeStamp2Date("1523548800", DateUtil.DEFAULT_PATTERN));
	}

	/**
	 * Java将Unix时间戳转换成指定格式日期字符串
	 * @param timestampString 时间戳 如："1473048265";
	 * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
	 *
	 * @return 返回结果 如："2016-09-05 16:06:42";
	 */
	public static String TimeStamp2Date(String timestampString, String formats) {
		Long timestamp = 1523548800l*1000;
		String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
		return date;
	}
}