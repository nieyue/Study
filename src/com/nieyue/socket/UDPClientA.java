package com.nieyue.socket;

import java.net.*;
import java.util.Date;

public class UDPClientA {
 
    public static void main(String[] args) {
        try {
            // 向server发起请求
            SocketAddress target = new InetSocketAddress("43.241.156.188", 2008);
            DatagramSocket client = new DatagramSocket();
            String message = "I am UPDClientA 192.168.1.114";
            byte[] sendbuf = message.getBytes();
            DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
            client.send(pack);
            // 接收请求的回复,可能不是server回复的，有可能来自UPDClientB的请求内
            receive(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //向UPDClientB发起请求(在NAT上打孔)
    private static void sendMessage(String host, String port, DatagramSocket client) {
        try {
            SocketAddress target = new InetSocketAddress(host, Integer.parseInt(port));
            for (;;) {
                String message = "I am master 192.168.1.114 count test 打孔中"+new Date().toLocaleString();
                byte[] sendbuf = message.getBytes();
                DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
                client.send(pack);
                //接收UDPClientB回复的内容
                receive(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //接收请求内容
    private static void receive(DatagramSocket client) {
        try {
            for (;;) {
                Thread.sleep(1000);
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                client.receive(packet);
                String receiveMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println(receiveMessage);
                int port = packet.getPort();
                InetAddress address = packet.getAddress();
                String reportMessage = "tks"+new Date().toLocaleString();
                //获取接收到请问内容后并取到地址与端口,然后用获取到地址与端口回复内容
                sendMessaage(reportMessage, port, address, client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //回复内容
    private static void sendMessaage(String reportMessage, int port, InetAddress address, DatagramSocket client) {
        try {
            byte[] sendBuf = reportMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
            client.send(sendPacket);
            System.out.println("消息发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}