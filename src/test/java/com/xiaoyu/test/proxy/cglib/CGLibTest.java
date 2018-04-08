package com.xiaoyu.test.proxy.cglib;

public class CGLibTest {

	public static void main(String[] args) {
		MyCglibProxy proxy = new MyCglibProxy();
		// 通过生成子类的方式创建代理类
//		HelloWorldOper word = (HelloWorldOper) proxy
//				.getDaoBean(HelloWorldOper.class);
//		word.print();
		
		HelloWorldOper word = (HelloWorldOper) proxy
		.getDaoBeanByFilter(HelloWorldOper.class);
		word.print();
		word.say();
	}

}