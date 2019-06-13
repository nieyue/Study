package com.nieyue.rpc;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String info) {
        String result = "hello : " + info;
        System.out.println(result);
        return result;
    }
}
