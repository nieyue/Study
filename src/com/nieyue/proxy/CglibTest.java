package com.nieyue.proxy;

import net.sf.cglib.proxy.Enhancer;

public class CglibTest {  
    public static void main(String[] args) {  
        CglibProxy cglibProxy = new CglibProxy();  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(UserServiceImpl.class);  
        enhancer.setCallback(cglibProxy);  
        UserService o = (UserService)enhancer.create();  
        o.getName(1);  
        o.getAge(1);  
    }  
}
