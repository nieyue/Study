package com.nieyue.proxyservice;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class ActionSocket extends  Thread{
    private Socket socket = null ;
    private String content="";
    String url;
    public ActionSocket(Socket s,String url){
        this.socket = s ;
        this.url=url;
    }
    public void run(){
        try{

           this.action(url);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void  action(String url) throws Exception {
        if (this.socket == null){
            return ;
        }
        InputStream cis = this.socket.getInputStream();

        URL url2 = new URL(url);
        HttpURLConnection action = (HttpURLConnection) url2.openConnection();
        InputStream sis =action.getInputStream();
        OutputStream cos = socket.getOutputStream();
        int length;
        byte bytes[] = new byte[1024];

        while(true){
            try {
                if ((length = sis.read(bytes)) > 0) {
                    cos.write(bytes, 0, length);//将http请求头写到目标主机
                    System.out.println(new String(bytes));
                    OutputStream ops = this.socket.getOutputStream();
                    PrintWriter pw=new PrintWriter(ops);
                    pw.write(new String(bytes));
                    cos.flush();
                } else if (length < 0) {
                    break;
                }
            } catch (Exception e) {
            }
        }
        socket.close();
        cis.close();
    }

}
