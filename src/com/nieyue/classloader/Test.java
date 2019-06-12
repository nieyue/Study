package com.nieyue.classloader;

public class Test {
    static {
        System.out.println("static start");
    }
    public Test(){
        System.out.println("constructor第三方");
    }
    public static void main(String[] args) {
        System.out.println(123);
    }
}
