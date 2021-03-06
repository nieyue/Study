package com.nieyue.p2p;

import net.sf.json.JSONObject;

import java.net.*;
import java.util.Date;

public class UDPClientB {
    static String ip="43.241.156.188";
    //static String ip="127.0.0.1";
    static Integer port=2008;
    private static String ROOMNAME="roomId";
    private static String CLIENTLISTNAME="clientList";
    private static String CLIENTNAME="clientId";
    private static String MSGNAME="msg";
    private static String STATUSNAME="status";
    private static ThreadLocal<Integer> retryNum=new ThreadLocal<>();//重试次数
    private static int retryMaxNum=3;//重试最大次数
    //连接伺服服务
    private static void linkServer(Long roomId,Long clientId){
        try {
            //向server发起请求
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
            receive(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //收到UDPClientA的回复内容，穿透已完成
    private static void receive(DatagramSocket client) {
        try {
            for (;;) {

                //将接收到的内容打印
                byte[] buf = new byte[1024];
                DatagramPacket recpack = new DatagramPacket(buf, buf.length);
                client.receive(recpack);
                String receiveMessage = new String(recpack.getData(), 0, recpack.getLength());
                JSONObject jsonobject = JSONObject.fromObject(receiveMessage);
                System.out.println("接受到："+recpack.getAddress().getHostAddress()+":"+recpack.getPort()+"内容："+jsonobject);
                if(jsonobject.get(STATUSNAME).equals(2)){//注册成功后，等待打洞
                    continue;
                }else if(jsonobject.get(STATUSNAME).equals(3)){//准备打洞
                    jsonobject.put(STATUSNAME,4);//打洞中
                    jsonobject.put(MSGNAME,"打洞中");
                    sendMessage(jsonobject, client);
                }else if(jsonobject.get(STATUSNAME).equals(4)){//打洞中
                    jsonobject=exchange(jsonobject);
                    jsonobject.put(STATUSNAME,5);//发消息
                    jsonobject.put(MSGNAME,"开始发消息了");
                    sendMessage(jsonobject, client);
                }else if(jsonobject.get(STATUSNAME).equals(5)){//发消息
                    Thread.sleep(1000);
                    jsonobject=exchange(jsonobject);
                    jsonobject.put(STATUSNAME,5);//发消息
                    jsonobject.put(MSGNAME,"发消息中"+new Date().toLocaleString());
                    sendMessage(jsonobject, client);
                }
            }
        }catch (SocketTimeoutException e) {
            Integer rn = retryNum.get();
            if(rn!=null&&rn>=retryMaxNum){
                System.out.println("重试超过最大次数"+rn);
            }else {
                int temprn=rn==null?1:rn+1;
                System.out.println("第"+temprn+"次重试");
                if(rn==null){
                    retryNum.set(1);
                }else if(rn<retryMaxNum){
                    retryNum.set(rn+1);
                }
                linkServer(123l,123l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //向UPDClient发起请求
    private static void sendMessage(JSONObject jsonObject, DatagramSocket client) {
        try {
            String  sourceHost = jsonObject.getString("sourceHost");
            int  sourcePort = jsonObject.getInt("sourcePort");
            String  targetHost = jsonObject.getString("targetHost");
            int  targetPort = jsonObject.getInt("targetPort");
            String  msg = jsonObject.getString("msg");
            SocketAddress target = new InetSocketAddress(targetHost, targetPort);
            byte[] sendbuf =jsonObject.toString().getBytes();
            System.out.println("从"+sourceHost+":"+sourcePort+"发送到："+targetHost+":"+targetPort+"发送内容："+msg);
            DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
            client.send(pack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 交换id,地址和端口
     * @return
     */
    private static JSONObject exchange(JSONObject jsonObject){
        String  sourceHost = jsonObject.getString("sourceHost");
        int  sourcePort = jsonObject.getInt("sourcePort");
        Long  sourceClientId = jsonObject.getLong("sourceClientId");
        String  targetHost = jsonObject.getString("targetHost");
        int  targetPort = jsonObject.getInt("targetPort");
        Long  targetClientId = jsonObject.getLong("targetClientId");

        jsonObject.put("sourceHost",targetHost);
        jsonObject.put("sourcePort",targetPort);
        jsonObject.put("sourceClientId",targetClientId);
        jsonObject.put("targetHost",sourceHost);
        jsonObject.put("targetPort",sourcePort);
        jsonObject.put("targetClientId",sourceClientId);
        return jsonObject;

    }
    public static void main(String[] args) {
    linkServer(123l,456l);
    }
}