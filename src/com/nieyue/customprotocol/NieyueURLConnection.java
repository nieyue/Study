package com.nieyue.customprotocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * 自定义nieyue协议连接器
 */
public class NieyueURLConnection extends URLConnection {
    /**
     * nieyue协议的默认端口号
     */
    public static final int DEFAULT_PORT=9527;

    private Socket connection=null;
    /**
     * Constructs a URL connection to the specified URL. A connection to
     * the object referenced by the URL is not created.
     *
     * @param url the specified URL.
     */
    protected NieyueURLConnection(URL url) {
        super(url);
    }

    /**
     * 由于父类URLConnection中的getInputStream()方法不提供输入流的获取实现逻辑，
     * 因此这里需要重写此方法
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        if(!connected){
            this.connect();
        }
        return connection.getInputStream();
    }

    /**
     * nieyue协议连接操作
     * @throws IOException
     */
    @Override
    public void connect() throws IOException {
            if(!connected){
                int port=url.getPort();
                if(port<1 || port>655535){
                    port=DEFAULT_PORT;

                }
                this.connection=new Socket(url.getHost(),port);
                connected=true;
                //连接后立即发送请求
                sendRequest(url);
            }
    }

    /**
     * 发送nieyue协议请求
     * @param url
     */
    private void sendRequest(URL url) throws IOException {
        OutputStream outputStream=this.connection.getOutputStream();
        String queryString= url.getQuery();
        outputStream.write(StringUtils.isNotNullOrBlank(queryString)
                ? queryString.getBytes() : "?".getBytes());
        outputStream.flush();
    }
}
