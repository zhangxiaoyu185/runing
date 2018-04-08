package com.xiaoyu.test.proxy.nodynamic;

public class HelloWorldServiceImpl implements HelloWorldService{    
	   
    public void print(){    
        System.out.println("HelloWorld");    
    }
    
    public void say(){    
    	System.out.println("Say Hello!");    
  	}
  
}