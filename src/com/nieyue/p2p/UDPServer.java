package com.nieyue.p2p;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class UDPServer {

    private static JSONArray roomJsonArray =new JSONArray();
    private static String ROOMNAME="roomId";
    private static String CLIENTLISTNAME="clientList";
    private static String CLIENTNAME="clientId";
    private static String MSGNAME="msg";
    private static String STATUSNAME="status";
    static int port=2008;
    //jsonArray 获取jsonObject
    public static JSONObject getJSONObject(JSONArray jsonArray,String key,Long value){
        ListIterator listiter = jsonArray.listIterator();
        JSONObject json=null;
        while (listiter.hasNext()){
            JSONObject tjson = (JSONObject) listiter.next();
            if(tjson.getLong(key)==value){
                json=tjson;
                break;
            }
        }
        return json;
    }


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
                JSONObject receiveMessageObject=JSONObject.fromObject(receiveMessage);

                port = packet.getPort();
                address = packet.getAddress();


                Long roomId=receiveMessageObject.getLong(ROOMNAME);
                Long clientId=receiveMessageObject.getLong(CLIENTNAME);
                msg=receiveMessageObject.getString(MSGNAME);
                if(roomId!=null){

                    /**
                     * [
                     *  {
                     *  "roomId":1,
                     *  "status":1,
                     *  "clientList":[]
                     *  },
                     *  {
                     *  "roomId":123,
                     *  "status":1,
                     *  "clientList":[
                     *      {
                     *          "clientId":123,
                     *           "host":"127.0.0.1",
                     *           "port":15566,
                     *           "msg":"123123",
                     *      },
                     *  ]}
                     * ]
                     *
                     *
                     */

                    JSONObject roomJsonObject=new JSONObject();
                    JSONObject tempRoomJsonObject = getJSONObject(roomJsonArray, ROOMNAME, roomId);
                    if(tempRoomJsonObject!=null&&!"".equals(tempRoomJsonObject)){
                        roomJsonObject=tempRoomJsonObject;
                    }
                    JSONArray clientJsonArray=new JSONArray();
                    JSONArray tempClientJsonArray = null;
                    try{
                        tempClientJsonArray = roomJsonObject.getJSONArray(CLIENTLISTNAME);
                    }catch (JSONException e){

                    }
                    
                    if(tempClientJsonArray!=null&&!"".equals(tempClientJsonArray)){
                        clientJsonArray=tempClientJsonArray;
                    }

                    JSONObject clientJsonObject=new JSONObject();
                    JSONObject tempClientJsonObject = getJSONObject(clientJsonArray,CLIENTNAME,clientId);
                    if(tempClientJsonObject!=null&&!"".equals(tempClientJsonObject)){
                        clientJsonObject=tempClientJsonObject;
                    }
                    clientJsonObject.put("host",address.getHostAddress());
                    clientJsonObject.put("port",port);
                    clientJsonObject.put("msg",msg);

                    if(tempClientJsonObject==null){
                        clientJsonObject.put(CLIENTNAME,clientId);
                        clientJsonArray.add(clientJsonObject);
                    }

                   // if(tempClientJsonArray==null){
                        roomJsonObject.put(CLIENTLISTNAME,clientJsonArray);
                    //}
                    if(tempRoomJsonObject==null){
                        roomJsonObject.put(ROOMNAME,roomId);
                        roomJsonArray.add(roomJsonObject);
                    }
                    int status = receiveMessageObject.getInt(STATUSNAME);
                    //1注册，2注册成功,3准备打孔，4打孔中,5发消息
                    if(status==1){
                        JSONObject registerjson=new JSONObject();
                        registerjson.put(STATUSNAME,2);
                        byte[] registerbytes = registerjson.toString().getBytes();
                        //注册
                        DatagramPacket sendPacket = new DatagramPacket(registerbytes, registerbytes.length, address, port);
                        server.send(sendPacket);
                    }
                    //两个都接收到后分别A、B址地交换互发
                    if (clientJsonArray.size()>=2) {
                       // sendA(sendMessageB, portA, addressA, server);
                        //sendB(sendMessageA, portB, addressB, server);

                        ListIterator clientiterator = clientJsonArray.listIterator();
                        while (clientiterator.hasNext()){
                            JSONObject next =(JSONObject) clientiterator.next();
                            Long cid =  next.getLong(CLIENTNAME);
                            String teampHost=  next.getString("host");
                            int tempPort=  next.getInt("port");
                            InetAddress inetAddress =InetAddress.getByName(teampHost);

                            ListIterator clientiterator2  = clientJsonArray.listIterator();
                            while (clientiterator2.hasNext()){
                                JSONObject next2 =(JSONObject) clientiterator2.next();
                                Long cid2 =  next2.getLong(CLIENTNAME);
                                String teampHost2=  next2.getString("host");
                                int tempPort2=  next2.getInt("port");


                                if(!cid.equals(cid2)){
                                    JSONObject newmsgjson=new JSONObject();
                                    newmsgjson.put("sourceHost",teampHost);
                                    newmsgjson.put("sourcePort",tempPort);
                                    newmsgjson.put("sourceClientId",next.get("clientId"));
                                    newmsgjson.put("targetHost",teampHost2);
                                    newmsgjson.put("targetPort",tempPort2);
                                    newmsgjson.put("targetClientId",next2.get("clientId"));
                                    newmsgjson.put(STATUSNAME,3);//准备打孔
                                    newmsgjson.put("msg",next.get("msg"));
                                    send(newmsgjson.toString(), tempPort, inetAddress, server);
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