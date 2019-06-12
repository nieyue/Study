package com.nieyue.proxyservice;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String args[])throws Exception{
        ServerSocket server  = new ServerSocket(8090);
        String url="http://www.baidu.com";
        while(true){
            Socket socket = server.accept();
            ActionSocket ap = new ActionSocket(socket,url);
            ap.start();
        }
    }
}
