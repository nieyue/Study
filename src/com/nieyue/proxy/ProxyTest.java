package com.nieyue.proxy;
/**
 * JDK动态代理测试
 * JDK动态代理只能对实现了接口的类生成代理，而不能针对类
 * @author 聂跃
 * @date 2018年5月17日
 */
public class ProxyTest  {  
 
    public static void main(String[] args) {   
    	  UserService userService = new UserServiceImpl();  
    	  MyInvocationHandler invocationHandler = new  MyInvocationHandler(); 
          UserService userServiceProxy = (UserService)invocationHandler.bind(userService);
          System.out.println(userServiceProxy.getName(1));  
          System.out.println(userServiceProxy.getAge(1)); 
    }  
}

