package com.nieyue.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UDPServer {
    private static Map<Long, Map<Long,Map<String,Object>>> map =new ConcurrentHashMap<>();
    private static String idname1="roomId";
    private static String idname2="clientId";
    static int port=2008;
    public static void init(){
        try {
            DatagramSocket server = new DatagramSocket(port);
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            String msg = "";
            int port = 0;
            InetAddress address = null;
            for (;;) {
                server.receive(packet);

                String receiveMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println(receiveMessage);
                //{"roomId":roomId}
                if(receiveMessage.indexOf(idname1)<=-1){
                    continue;
                }
                Long roomId=Long.valueOf(receiveMessage.substring(0, receiveMessage.indexOf(idname1)));
                Long clientId=Long.valueOf(receiveMessage.substring(receiveMessage.indexOf(idname1)+idname1.length(),receiveMessage.indexOf(idname2)));
                msg=receiveMessage.substring(receiveMessage.indexOf(idname2));
                if(roomId!=null){
                    port = packet.getPort();
                    address = packet.getAddress();
                    Map<Long ,Map<String,Object>> roommap=new HashMap<>();
                    Map<String ,Object> clientmap=new HashMap<>();
                    if(map.containsKey(roomId)){
                        roommap=map.get(roomId);
                        if(roommap.containsKey(clientId)){
                            clientmap=roommap.get(clientId);
                        }
                        clientmap.put("host",address.getHostAddress());
                        clientmap.put("port",port);
                        clientmap.put("msg",msg);
                    }
                    roommap.put(clientId,clientmap);
                    map.put(roomId,roommap);
                    //两个都接收到后分别A、B址地交换互发
                    if (roommap.size()>=2) {
                       // sendA(sendMessageB, portA, addressA, server);
                        //sendB(sendMessageA, portB, addressB, server);

                        Iterator<Map.Entry<Long, Map<String, Object>>> roomiterator = roommap.entrySet().iterator();
                        Iterator<Map.Entry<Long, Map<String, Object>>> roomiterator2 = roommap.entrySet().iterator();
                        while (roomiterator.hasNext()){
                            Long cid = roomiterator.next().getKey();
                            Map<String, Object> cmap = roomiterator.next().getValue();
                            InetAddress address1 =InetAddress.getByName(cmap.get("host").toString());
                            Integer port1 = Integer.valueOf(cmap.get("port").toString());
                            while (roomiterator2.hasNext()){
                                Long cid2 = roomiterator.next().getKey();
                                if(!cid.equals(cid2)){
                                    send(msg, port1, address1, server);
                                }
                            }
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void send(String msg, int port, InetAddress address, DatagramSocket server) {
        try {
            byte[] sendBuf = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
            server.send(sendPacket);
            System.out.println("消息发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 


    public static void main(String[] args) {
        init();
        /*String receiveMessage="123456roomId2342clientId234f";
        Long roomId=Long.valueOf(receiveMessage.substring(0,receiveMessage.indexOf(idname1)));
        Long clientId=Long.valueOf(receiveMessage.substring(receiveMessage.indexOf(idname1)+idname1.length(),receiveMessage.indexOf(idname2)));

        System.out.println(roomId);
        System.out.println(clientId);*/


    }
}