package com.xiaoyu.test.proxy.dynamic;

import com.xiaoyu.test.proxy.nodynamic.HelloWorldService;
import com.xiaoyu.test.proxy.nodynamic.HelloWorldServiceImpl;

public class TestDynamicProxy {
	
    public static void main(String[] args){        
        HelloWorldService helloWorld = new HelloWorldServiceImpl();        
        DynamicProxy dp = new DynamicProxy();        
        //在这里绑定的是HelloWorld,也就是HelloWorld是被代理接口。所以绑定关系时，需要传递一个HelloWorld的实现类的实例化对象。        
        HelloWorldService helloWorld1 = (HelloWorldService)dp.bindRelation(helloWorld);         
        helloWorld1.print();         
        helloWorld1.say();         
    }
    
}