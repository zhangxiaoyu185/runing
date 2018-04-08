package com.xiaoyu.lingdian.tool;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 货币计算
 */
@SuppressWarnings("static-access")
public class CurrencyCalculator {

	/**
	 * 把非标准的货币值转化成标准货币值
	 * （保留二位小数，末位小数四舍五入）
	 * @param nonStandardVal
	 * @return
	 */
	public static double formatPayStandard(double nonStandardVal){
		BigDecimal bd = new BigDecimal(nonStandardVal);
		BigDecimal bd1 = bd.setScale(2, bd.ROUND_HALF_UP);
		
		return bd1.doubleValue();
	}
	
	/**
	 * 把非标准的货币值转化成标准货币值字符串
	 * （保留二位小数，末位小数四舍五入）
	 * @param nonStandardVal
	 * @return
	 */
	public static String formatPayStandardStr(double nonStandardVal){
		BigDecimal bd = new BigDecimal(nonStandardVal);
		BigDecimal bd1 = bd.setScale(2, bd.ROUND_HALF_UP);
		DecimalFormat format = new DecimalFormat("0.00");
		
		return format.format(bd1.doubleValue());
	}
	
}