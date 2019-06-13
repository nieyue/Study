package com.nieyue.rpc;

public class RpcClient {
    // 调用服务
    public static void main(String[] args) {
        RpcProxyClient<HelloService> rpcClient = new RpcProxyClient();
        HelloService hello = rpcClient.jdkProxyClient(HelloService.class,"localhost",8001);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String s = hello.sayHello("第"+(i+1)+"次说hello");
            System.out.println(s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}