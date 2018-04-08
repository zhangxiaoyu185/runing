package com.xiaoyu.test.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler{        
    
    private Object object; 
    //Proxy.newProxyInstance的第三个参数是表明这些被拦截的方法执行时需要执行哪个InvocationHandler的invoke方法    
    public Object bindRelation(Object object){         
        this.object = object;        
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);         
    }         
    //拦截关联的这个实现类的方法被调用时将被执行        
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {         
        System.out.println("Welcome");        
        Object result = method.invoke(object, args);         
        return result;        
    }        
       
}