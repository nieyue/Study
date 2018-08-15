package com.nieyue.customprotocol;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 *自定义协议的处理器工厂，负责针对每种自定义的协议而返回它们各自对应的协议处理器
 * 如果要用上述的查找规则来安装协议处理器时，则需要用到此类
 */
public class CustomProtocolFactory implements URLStreamHandlerFactory {
    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if("nieyue".equalsIgnoreCase(protocol))
            return new NieyueHandler();
        return null;
    }
}
