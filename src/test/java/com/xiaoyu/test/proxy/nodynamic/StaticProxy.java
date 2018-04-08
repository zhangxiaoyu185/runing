package com.xiaoyu.test.proxy.nodynamic;

public class StaticProxy implements HelloWorldService {    
	   
    public HelloWorldService helloWorld;    
    public StaticProxy(HelloWorldService helloWorld){    
        this.helloWorld = helloWorld;    
    }    
        
    public void print(){    
        System.out.println("Welcome");    
        //相当于回调    
        helloWorld.print();    
    }    
        
    public void say(){    
      //相当于回调    
      helloWorld.say();    
    }
    
    public static void main(String[] args) {
    	StaticProxy a = new StaticProxy(new HelloWorldServiceImpl());
    	a.print();
	}
    
}