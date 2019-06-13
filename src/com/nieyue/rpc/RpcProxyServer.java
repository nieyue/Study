package com.nieyue.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * rpc 服务端代理
 */
public class RpcProxyServer {
    private HelloService hello = new HelloServiceImpl();

    /**
     *
     * @param service 服务实现
     * @param port 服务端口
     */
    public void publisherServer(Object service,int port) {
        if (service == null)
            throw new IllegalArgumentException("service instance == null");
        if (port <= 0 || port > 65535)
            throw new IllegalArgumentException("Invalid port " + port);
        System.out.println("Export service " + service.getClass().getName() + " on port " + port);
        try (ServerSocket ss = new ServerSocket(port)) {
            while (true) {
                final Socket socket = ss.accept();
                try {
                    try {
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        try {
                            String methodName = input.readUTF();
                            Class<?>[] parameterTypes = (Class<?>[])input.readObject();
                            Object[] arguments = (Object[])input.readObject();
                            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                            try {
                                Method method = service.getClass().getMethod(methodName, parameterTypes);
                                Object result = method.invoke(service, arguments);
                                output.writeObject(result);
                            } catch (Throwable t) {
                                output.writeObject(t);
                            } finally {
                                output.close();
                            }
                        } finally {
                            input.close();
                        }
                    } finally {
                        socket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}