package com.nieyue.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 主要的方法拦截类，它是Callback接口的子接口
 * 
 * @author 聂跃
 * @date 2018年5月17日
 */
public class CglibProxy implements MethodInterceptor { 
	/**
	 * @param o 第一个参数是代理对像
	 * @param method 是拦截的方法
	 * @param args 方法的参数
	 * @param MethodProxy 是JDK的java.lang.reflect.Method类的代理类，可以方便的实现对源对象方法的调用
	 */
    @Override  
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {  
        System.out.println("++++++before " + methodProxy.getSuperName() + "++++++");  
        System.out.println(method.getName());  
        Object o1 = methodProxy.invokeSuper(o, args);  
        System.out.println("++++++before " + methodProxy.getSuperName() + "++++++");  
        return o1;  
    }  
}  