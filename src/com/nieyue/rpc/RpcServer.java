package com.nieyue.rpc;

public class RpcServer {
    //发布服务
    public static void main(String[] args) {
        HelloService helloService=new HelloServiceImpl();
        RpcProxyServer server = new RpcProxyServer();
        server.publisherServer(helloService,8001);
    }
}