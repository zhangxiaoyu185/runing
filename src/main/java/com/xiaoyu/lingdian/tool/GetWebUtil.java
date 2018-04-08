package com.xiaoyu.lingdian.tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;

public class GetWebUtil {

	//抓取网页源代码
	public static void catchHtml(String urlPath) throws Exception {
		URL url = new URL(urlPath);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
		BufferedReader bufReader = new BufferedReader(input);
		String line = "";
		StringBuilder contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line+"\n");
		}
		String buf = contentBuf.toString();
		System.out.println(buf);
	}

	//抓取网页部分信息
	public static NodeList getNodeList(String url) {
		Parser parser = null;
		HtmlPage visitor = null;
		try {
			parser = new Parser(url);
			parser.setEncoding("UTF-8");
			visitor = new HtmlPage(parser);
			parser.visitAllNodesWith(visitor);
		} catch (ParserException e) {
			try {
				parser = new Parser(url);
				parser.setEncoding("gb2312");
				visitor = new HtmlPage(parser);
				parser.visitAllNodesWith(visitor);
			} catch (Exception e2) {
			}			
		}
		NodeList nodeList = visitor.getBody();
		return nodeList;
	}

	public static void main(String[] args) throws Exception {
		NodeList rt = GetWebUtil.getNodeList("http://wbx.adwebai.com/v-U703KV7226");
		String strHtml = rt.toHtml();
				//.replaceAll("&nbsp;", "").replaceAll("<br/>", "\r\n").replaceAll("<br>", "\r\n");
		System.out.println(strHtml);		
//		Pattern jzpattern = Pattern.compile("endTime :[\\s\\S]*?//截止时间");	
//		Matcher jzmatcher = jzpattern.matcher(strHtml); 	
//		if (jzmatcher.find()) {			
//			String jzstr = jzmatcher.group();
//			System.out.println(jzstr);
//			
//		}		
//		Pattern pattern = Pattern.compile("<tr data-vs=\"[\\s\\S]*?</tr>");	
//		Matcher matcher = pattern.matcher(strHtml); 		
//		while (matcher.find()) {			
//			String str = matcher.group();
//			//System.out.println(str);
//			Pattern pattern1 = Pattern.compile("<tr data-vs=\"[\\s\\S]*?\">");	
//			Matcher matcher1 = pattern1.matcher(str); 		
//			if (matcher1.find()) {			
//				String str1 = matcher1.group();
//				System.out.println(str1);
//				
//			}
//			Pattern pattern2 = Pattern.compile("target=\"_blank\">[\\s\\S]*?</a></td>");	
//			Matcher matcher2 = pattern2.matcher(str); 		
//			if (matcher2.find()) {			
//				String str2 = matcher2.group();
//				System.out.println(str2);
//				
//			}
//			Pattern pattern3 = Pattern.compile("<td><span class=\"eng\">[\\s\\S]*?</span></td>");	
//			Matcher matcher3 = pattern3.matcher(str); 		
//			if (matcher3.find()) {			
//				String str3 = matcher3.group();
//				System.out.println(str3);
//				
//			}
//			Pattern pattern4 = Pattern.compile("<span class=\"sp_w38 eng\">[\\s\\S]*?</span>");	
//			Matcher matcher4 = pattern4.matcher(str);
//			int i = 0;
//			while (matcher4.find()) {
//				i++;
//				if (i > 3) {
//					break;
//				}
//				String str4 = matcher4.group();
//				System.out.println(str4);
//				
//			}
//		}		
	}
	
}