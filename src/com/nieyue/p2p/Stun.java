package com.nieyue.p2p;

import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Stun {
    private static Map<String,Boolean> map=new ConcurrentHashMap<>();
    private static int port=3478;

    static{
        map.put("stun.ideasip.com",true);//代表能用
        map.put("stun.schlund.de",true);
        map.put("stun.voiparound.com",true);
        map.put("stun.voipbuster.com",true);
        map.put("stun.voipstunt.com",true);
        map.put("stun.xten.com",true);
/*
        map.put("stun:stun.ideasip.com",true);//代表能用
        map.put("stun:stun.schlund.de",true);
        map.put("stun:stun.voiparound.com",true);
        map.put("stun:stun.voipbuster.com",true);
        map.put("stun:stun.voipstunt.com",true);
        map.put("stun:stun.xten.com",true);
*/
    }

    //连接伺服服务
    private static void linkServer(Long roomId,Long clientId){
        try {
            String key = map.entrySet().stream().filter(e -> e.getValue().equals(true)).findAny().get().getKey();
            // 向server发起请求
            SocketAddress target = new InetSocketAddress(key, port);
            DatagramSocket client = new DatagramSocket();
            String message="1";
            byte[] sendbuf = message.getBytes();
            DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);

            //client.setSoTimeout(1000);
            //client.send(pack);
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
                System.out.println("接受到："+packet.getAddress().getHostAddress()+":"+packet.getPort()+"内容："+receiveMessage);

            }
        } catch (SocketTimeoutException e) {

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        linkServer(null,null);
    }
}
