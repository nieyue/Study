package com.nieyue.p2p;

import net.sf.json.JSONObject;

import java.net.*;
import java.util.Date;

public class UDPClientB {
    //static String ip="43.241.156.188";
    static String ip="127.0.0.1";
    static Integer port=2008;
    //连接伺服服务
    private static void linkServer(Long roomId,Long clientId){
        try {
            //向server发起请求
            SocketAddress target = new InetSocketAddress(ip, port);
            DatagramSocket client = new DatagramSocket();
            String message = roomId+"roomId"+clientId+"clientIdregister";
            byte[] sendbuf = message.getBytes();
            DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
            client.send(pack);
            //接收server的回复内容
            /*byte[] buf = new byte[1024];
            DatagramPacket recpack = new DatagramPacket(buf, buf.length);
            client.receive(recpack);
            //处理server回复的内容，然后向内容中的地址与端口发起请求（打洞）
            String receiveMessage = new String(recpack.getData(), 0, recpack.getLength());
            String[] params = receiveMessage.split(",");
            String host = params[0].substring(5);
            String port = params[1].substring(5);
            System.out.println(host + ":" + port);
            sendMessage(host, port, client);*/
            receive(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //收到UDPClientA的回复内容，穿透已完成
    private static void receive(DatagramSocket client) {
        try {
            for (;;) {
                Thread.sleep(1000);
                //将接收到的内容打印
                byte[] buf = new byte[1024];
                DatagramPacket recpack = new DatagramPacket(buf, buf.length);
                client.receive(recpack);
                String receiveMessage = new String(recpack.getData(), 0, recpack.getLength());
                if(receiveMessage.equals("1")){
                    //注册成功后，等待打洞
                    continue;
                }else{
                    //打洞
                    JSONObject jsonobject = JSONObject.fromObject(receiveMessage);
                    String host=jsonobject.getString("host");
                    String port=jsonobject.getString("port");
                    sendMessage(host, port,  new DatagramSocket());
                }

               /* System.out.println(receiveMessage);
                int port = packet.getPort();
                InetAddress address = packet.getAddress();
                System.out.println(address.getHostAddress()+port);
                String reportMessage = "123roomId123clientId123请求内容中"+new Date().toLocaleString();
                //String reportMessage = "tks"+new Date().toLocaleString();
                //获取接收到请问内容后并取到地址与端口,然后用获取到地址与端口回复内容
                sendMessaage(reportMessage, port, address, client);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //向UPDClientA发起请求(在NAT上打孔)
    private static void sendMessage(String host, String port, DatagramSocket client) {
        try {
            SocketAddress target = new InetSocketAddress(host, Integer.parseInt(port));
            for (;;) {
                String message = "123roomId456clientId456打孔中"+new Date().toLocaleString();
                //String message = "I am master 192.168.1.114 count test 打孔中"+new Date().toLocaleString();
                byte[] sendbuf = message.getBytes();
                DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
                client.send(pack);
                //接收UDPClientA回复的内容
                //receive(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    private static void sendMessage(String reportMessage, int port, InetAddress address, DatagramSocket client) {
        try {
             
            byte[] sendBuf = reportMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
            client.send(sendPacket);
            System.out.println("send success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    linkServer(123l,456l);
    }
}