package com.nieyue.proxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法（继承）
 */
public class CglibTest {  
    public static void main(String[] args) {  
        CglibProxy cglibProxy = new CglibProxy();
        UserService o = (UserService)cglibProxy.bind(UserServiceImpl.class);
        System.out.println(o.getName(1));
        System.out.println(o.getAge(1));
    }  
}
