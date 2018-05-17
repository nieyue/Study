package com.nieyue.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * @author 聂跃
 * @date 2018年5月17日
 */
public class MyInvocationHandler  implements InvocationHandler {
	private Object target;
	/**
	 * 绑定委托对象并返回一个  代理占位
	 * @param target 真实对象
	 * @param interfaces 接口
	 * @return  代理对象  占位
	 */
	@SuppressWarnings("rawtypes")
	public Object bind(Object target,Class[] interfaces) {
		this.target=target;
		return Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), this);

	}
	/** 
     * 同过代理对象调用方法首先进入这个方法. 
     * @param proxy --代理对象 
     * @param method -- 方法,被调用方法. 
     * @param args -- 方法的参数 
     */  
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		  if("getName".equals(method.getName())){  
	            System.out.println("++++++before " + method.getName() + "++++++");  
	            Object result = method.invoke(target, args);  
	            System.out.println("++++++after " + method.getName() + "++++++");  
	            return result;  
	        }else{  
	            Object result = method.invoke(target, args);  
	            return result;  
	        } 
	}

}
