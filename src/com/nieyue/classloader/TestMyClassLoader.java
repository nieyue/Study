package com.nieyue.classloader;

public class TestMyClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader mcl=new MyClassLoader(ClassLoader.getSystemClassLoader().getParent() );
        Class<?> c1=Class.forName("com.nieyue.classloader.Test",true,mcl);
        Object obj=c1.newInstance();
        System.out.println(obj);
        System.out.println(obj.getClass().getClassLoader());
    }
}
