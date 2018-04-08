package com.xiaoyu.lingdian.tool.encrypt;

import java.security.MessageDigest;
import com.xiaoyu.lingdian.tool.StringUtil;

public class MD5Util {

    /**
     * MD5加密(大写) 返回16进制的MDS加密串
     * 
     * @param value
     * @param charset (UTF-8等)
     * @return
     */
	public static String encode(String value, String charset) {
		String resultString = null;
		try {
			resultString = new String(value);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charset == null || "".equals(charset)) {
				resultString = StringUtil.toHexString(md.digest(resultString.getBytes()));
			} else {
				resultString = StringUtil.toHexString(md.digest(resultString.getBytes(charset)));
			}
		} catch (Exception exception) {
		}
		return resultString;
	}
    
	/**
     * MD5加密(小写) 返回16进制的MDS加密串
     * 
     * @param value
     * @return
     */
	public static String md5(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(value.getBytes());
			byte[] b = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				int v = (int) b[i];
				v = v < 0 ? 0x100 + v : v;
				String cc = Integer.toHexString(v);
				if (cc.length() == 1)
					sb.append('0');
				sb.append(cc);
			}
			return sb.toString();
		} catch (Exception e) {
		}
		return "";
	}

}