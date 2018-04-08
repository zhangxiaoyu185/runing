package com.xiaoyu.lingdian.tool.wx;

import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.xiaoyu.lingdian.entity.response.Article;
import com.xiaoyu.lingdian.entity.response.MusicMessage;
import com.xiaoyu.lingdian.entity.response.NewsMessage;
import com.xiaoyu.lingdian.entity.response.TextMessage;
import com.xiaoyu.lingdian.entity.weixin.ReceiveXmlEntity;

/**
 * 微信xml操作
 */
public class WxXmlOper {

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
	
	/**
	 * 封装文字类的返回消息
	 * 
	 * @param to
	 * @param from
	 * @param content
	 * @return
	 */
	public String formatXmlAnswer(String to, String from, String content) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(content);
		sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
		return sb.toString();
	}

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());
		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}

	/**
	 * 解析微信xml消息
	 * 
	 * @param strXml
	 * @return
	 */
	public ReceiveXmlEntity getMsgEntity(String strXml) throws Exception {
		ReceiveXmlEntity msg = null;
		if (strXml.length() <= 0 || strXml == null)
			return null;
		// 将字符串转化为XML文档对象
		Document document = DocumentHelper.parseText(strXml);
		// 获得文档的根节点
		Element root = document.getRootElement();
		// 遍历根节点下所有子节点
		Iterator<?> iter = root.elementIterator();
		// 遍历所有结点
		msg = new ReceiveXmlEntity();
		// 利用反射机制，调用set方法
		// 获取该实体的元类型
		Class<?> c = Class.forName("com.xiaoyu.lingdian.entity.weixin.ReceiveXmlEntity");
		msg = (ReceiveXmlEntity) ReceiveXmlEntity.class.newInstance();// 创建这个实体的对象
		while (iter.hasNext()) {
			Element ele = (Element) iter.next();
			// 获取set方法中的参数字段（实体类的属性）
			Field field = c.getDeclaredField(ele.getName());
			// 获取set方法，field.getType())获取它的参数数据类型
			Method method = c.getDeclaredMethod("set" + ele.getName(),
					field.getType());
			// 调用set方法
			method.invoke(msg, ele.getText());
		}
		return msg;
	}
	
	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage 音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage 图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
	
	/**
	 * 扩展xstream，使其支持CDATA块
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;
				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

}