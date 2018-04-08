package com.xiaoyu.test;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	}

}