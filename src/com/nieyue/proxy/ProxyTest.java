package com.nieyue.proxy;
/**
 * JDK动态代理测试
 * @author 聂跃
 * @date 2018年5月17日
 */
public class ProxyTest  {  
 
    public static void main(String[] args) {   
    	  UserService userService = new UserServiceImpl();  
    	  MyInvocationHandler invocationHandler = new  MyInvocationHandler(); 
          UserService userServiceProxy = (UserService)invocationHandler.bind(userService,new Class[]{UserService.class});
          System.out.println(userServiceProxy.getName(1));  
          System.out.println(userServiceProxy.getAge(1)); 
    }  
}

