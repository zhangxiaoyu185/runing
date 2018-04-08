package com.xiaoyu.test.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

public class MyCglibProxy implements MethodInterceptor {

	public Enhancer enhancer = new Enhancer();

	/**
	 * 根据class对象创建该对象的代理对象 1、设置父类；2、设置回调 本质：动态创建了一个class对象的子类
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object getDaoBean(Class cls) {
		enhancer.setSuperclass(cls);
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
	@SuppressWarnings("rawtypes")
	public Object getDaoBeanByFilter(Class cls) {
		enhancer.setSuperclass(cls);
		//enhancer.setCallback(this);
		enhancer.setCallbacks(new Callback[]{this, NoOp.INSTANCE}); 
		enhancer.setCallbackFilter(new MyProxyFilter());
		return enhancer.create();
	}	
	
	@Override
	public Object intercept(Object object, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		System.out.println("Welcome");
		//通过代理类调用父类中的方法
		Object result = methodProxy.invokeSuper(object, args);
		return result;
	}
	 
}