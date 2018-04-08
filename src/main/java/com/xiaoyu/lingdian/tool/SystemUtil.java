package com.xiaoyu.lingdian.tool;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class SystemUtil {

	//解析域名
	public static String getHostName(String strPath) throws Exception {
		InetAddress address = null;
		if("".equals(strPath)) //表示本机
			address = InetAddress.getLocalHost();
		else
			address = InetAddress.getByName(strPath);
		return address.getHostAddress(); 
	}
	
	//获取系统信息
	public static void getSystemInfo(){
		Properties properties = System.getProperties();
		for (Object obj : properties.keySet()) {
			String value1 = (String)properties.get(obj);
			System.out.println(obj+"="+value1);
		}
	}
	
	//获取子网掩码
	public static String getSubnetMask() {
		int prefix = 0;
		int[] ipSplit = new int[4];
		String subnetMask = null;
		InetAddress localMachine = null;
		try {
			localMachine = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		NetworkInterface netCard = null;
		try {
			netCard = NetworkInterface.getByInetAddress(localMachine);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		List<InterfaceAddress> localInterface = null;
		localInterface = netCard.getInterfaceAddresses();
		Iterator<InterfaceAddress> iterator = null;
		iterator = localInterface.iterator();
		while (iterator.hasNext()) {
			InterfaceAddress temp = null;
			temp = iterator.next();
			prefix = temp.getNetworkPrefixLength();
		}
		int index = 0;
		int split = 0;
		int remainder = 0;
		split = prefix / 8;
		remainder = prefix % 8;
		while (index < split) {
			ipSplit[index] = 255;
			index++;
		}
		if (remainder == 1)
			ipSplit[index] = 128;
		if (remainder == 2)
			ipSplit[index] = 192;
		if (remainder == 3)
			ipSplit[index] = 224;
		if (remainder == 4)
			ipSplit[index] = 240;
		if (remainder == 5)
			ipSplit[index] = 248;
		if (remainder == 6)
			ipSplit[index] = 252;
		if (remainder == 7)
			ipSplit[index] = 254;
		index++;
		while (index < remainder) {
			ipSplit[index] = 0;
			index++;
		}
		subnetMask = String.valueOf(ipSplit[0]) + "."
				+ String.valueOf(ipSplit[1]) + "." + String.valueOf(ipSplit[2])
				+ "." + String.valueOf(ipSplit[3]);
		return subnetMask;
	}
	
	//获取IP
	@SuppressWarnings("static-access")
	public static String getIPInfo() {
//		StringBuilder sb = new StringBuilder();
//		try {
//			Enumeration<NetworkInterface> interfaces = NetworkInterface
//					.getNetworkInterfaces();
//			while (interfaces.hasMoreElements()) {
//				NetworkInterface ni = interfaces.nextElement();
//				sb.append("Interface " + ni.getName() + ":\r\n");
//				Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
//				while (inetAddresses.hasMoreElements()) {
//					InetAddress address = inetAddresses.nextElement();
//					sb.append("Address");
//					if (address instanceof Inet4Address) {
//						sb.append("(v4)");
//					} else {
//						sb.append("(v6)");
//					}
//					sb.append(":address=" + address.getHostAddress() + " name="
//							+ address.getHostName() + "\r\n");
//				}
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return sb.toString();
		
		InetAddress ia = null;
		String localip = "121.41.46.75";
		try {
			ia = ia.getLocalHost();
			localip = ia.getHostAddress();
		} catch (Exception e) {
			return localip;
		}
		return localip;
	}

	// 获取当前操作系统名称
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}
		
}