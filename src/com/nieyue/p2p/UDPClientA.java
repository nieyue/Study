package com.nieyue.p2p;

import net.sf.json.JSONObject;

import java.net.*;
import java.util.Date;

public class UDPClientA {
    static String ip="43.241.156.188";
    //static String ip="127.0.0.1";
    static Integer port=2008;
    private static String ROOMNAME="roomId";
    private static String CLIENTLISTNAME="clientList";
    private static String CLIENTNAME="clientId";
    private static String MSGNAME="msg";
    private static String STATUSNAME="status";

    //连接伺服服务
    private static void linkServer(Long roomId,Long clientId){
        try {
            // 向server发起请求
            SocketAddress target = new InetSocketAddress(ip, port);
            DatagramSocket client = new DatagramSocket();
            JSONObject msgjson=new JSONObject();
            msgjson.put(ROOMNAME,roomId);
            msgjson.put(CLIENTNAME,clientId);
            msgjson.put(MSGNAME,"register");
            msgjson.put(STATUSNAME,1);//注册
            String message = msgjson.toString();
            byte[] sendbuf = message.getBytes();
            DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
            client.send(pack);
            // 接收请求的回复,可能不是server回复的，有可能来自UPDClientB的请求内
            receive(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //接收请求内容
    private static void receive(DatagramSocket client) {
        try {
            for (;;) {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                client.receive(packet);
                String receiveMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println(receiveMessage);
                System.out.println(packet.getAddress().getHostAddress()+packet.getPort());
                JSONObject jsonobject = JSONObject.fromObject(receiveMessage);
                if(jsonobject.get(STATUSNAME).equals(2)){//注册成功后，等待打洞
                    continue;
                }else if(jsonobject.get(STATUSNAME).equals(3)){//准备打洞
                    String host=jsonobject.getString("host");
                    String port=jsonobject.getString("port");
                    int status=4;//打洞中
                    String msg="打洞中";
                     sendMessage(host, port,status,msg, new DatagramSocket());
                   // receive(client);
                }else if(jsonobject.get(STATUSNAME).equals(4)){//打洞中
                    String host=jsonobject.getString("host");
                    String port=jsonobject.getString("port");
                    int status=5;//发消息
                    String msg="开始发消息了";
                    sendMessage(host, port,status,msg,  new DatagramSocket());
                }else if(jsonobject.get(STATUSNAME).equals(5)){//发消息
                    String host=jsonobject.getString("host");
                    String port=jsonobject.getString("port");
                    int status=5;//发消息
                    String msg="发消息中"+new Date().toLocaleString();
                    Thread.sleep(1000);
                    sendMessage(host, port,status,msg,  new DatagramSocket());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //向UPDClient发起请求
    private static void sendMessage(String host, String port, Integer status,String msg, DatagramSocket client) {
        try {
            SocketAddress target = new InetSocketAddress(host, Integer.parseInt(port));
            JSONObject sendjson=new JSONObject();
            sendjson.put("host",host);
            sendjson.put("port",port);
            sendjson.put(STATUSNAME,status);
            sendjson.put(MSGNAME,msg);
            byte[] sendbuf =sendjson.toString().getBytes();
            DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
            client.send(pack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        linkServer(123l,123l);
       /* new Thread(()->{
            linkServer(123l,123l);
        }).start();
        new Thread(()->{
            linkServer(123l,456l);
        }).start();
        new Thread(()->{
            linkServer(123l,789l);
        }).start();*/

    }
}