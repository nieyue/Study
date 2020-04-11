package com.nieyue.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    InetAddress ipAddress;
    Socket socket;
    int port=4000;
    byte ipAddressTemp[] = {127, 0, 0, 1};
    //直接创建serversocket
    public Client createServer() throws IOException {
         ipAddress = InetAddress.getByAddress(ipAddressTemp);
        return this;
    }
    //首先直接创建socket,端口号1~1023为系统保存，一般设在1023之外
    public Client accept() throws IOException {
         socket = new Socket(ipAddress, port);
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

            System.out.println(client);
            readline = systemIn.readLine();
            //System.out.println(readline);

            socketOut.println(readline);
            socketOut.flush();    //赶快刷新使Server收到，也可以换成socketOut.println(readline, ture)

            //outTemp = readline;
            inTemp = socketIn.readLine();

            //System.out.println(client + outTemp);
            System.out.println(server + turnLine + inTemp);

        }

            systemIn.close();
            socketIn.close();
            socketOut.close();
            socket.close();
    }

    public static void main(String[] args) {
        Client server = new Client();
        try {
            server.createServer().accept().test();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
