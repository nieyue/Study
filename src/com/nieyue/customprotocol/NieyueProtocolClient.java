package com.nieyue.customprotocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *    nieyue协议请求的客户端，主要功能分为：
 *    1.在创建第一次创建URL对象之前，添加对自定义协议的支持
 *    2.发送请求
 *    3.展示响应数据
 */
public class NieyueProtocolClient {

    public static void main(String[] args) {
        BufferedReader reader=null;
        try {
            // 配置协议处理器查找规则一
            if(StringUtils.isNullOrBlank(System.getProperty("java.protocol.handler.pkgs"))){
                //设置各个协议包所在的父包路径
                System.setProperty("java.protocol.handler.pkgs", "com.nieyue.customprotocol");
            }

            /*
             * 配置协议处理器查找规则二
             * 这种方式在整个应用范围之内只能被执行一次。
             * 如果多于一次则会出现"java.lang.Error: factory already defined"这样的错误。
             * 但不会受规则一的限制.
             */
            URL.setURLStreamHandlerFactory(new CustomProtocolFactory());
            URL url = null;
                url = new URL("nieyue://localhost:9527?pro=2311");
            reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            String result = "";
            while ((result = reader.readLine()) != null)
                System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
