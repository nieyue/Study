package com.nieyue.classloader;

public class TestMyClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> c1 = MyClassLoader.init("com.nieyue.classloader.Test");
        Object obj=c1.newInstance();
        System.out.println(obj);
        System.out.println(obj.getClass().getClassLoader());
    }
}
