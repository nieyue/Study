package com.nieyue.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;
    Socket socket;
    int port=4000;
    //直接创建serversocket
    public  Server createServer() throws IOException {
         serverSocket=new ServerSocket(port);
        return this;
    }
    //用服务器的accept（）进行阻塞（程序会在这等待），当有申请连接时会打开阻塞并返回一个socket
    public  Server accept() throws IOException {
         socket = serverSocket.accept();
        return this;
    }
    //创建三个流，系统输入流BufferedReader systemIn，socket输入流BufferedReader socketIn，socket输出流PrintWriter socketOut;
    public  void test() throws IOException {
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        String readline = null;
        String inTemp = null;
        String turnLine = "\n";
        final String client = "Client:";
        final String server = "Server:";
        while(readline==null||!readline .equals( "bye")){

            inTemp = socketIn.readLine();
            System.out.println(client + turnLine + inTemp);
            System.out.println(server);

            readline = systemIn.readLine();

            socketOut.println(readline);
            socketOut.flush();    //赶快刷新使Client收到，也可以换成socketOut.println(readline, ture)

            //outTemp = readline;

            //System.out.println(server);

        }
        systemIn.close();
        socketIn.close();
        socketOut.close();
        socket.close();
        serverSocket.close();
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.createServer().accept().test();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
