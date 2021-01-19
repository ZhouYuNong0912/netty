package com.io.bio;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: zyn
 * @Create: 2021-01-16 22:25
 * @Description:
 **/
public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("等待连接");
            //阻塞方法，如果当前没有客户端连接，则会一直阻塞在此位置
            Socket socket = serverSocket.accept();
            System.out.println("有客户端连接了。。。");
            //handler(socket);
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    handler(socket);
                }
            }).start();
        }
    }

    public static void handler(Socket clientSocket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备read...");
        //接受客户端的数据,阻塞方法,当没有数据可读就阻塞在此处
        int read = clientSocket.getInputStream().read(bytes);
        System.out.println("read读取完毕");
        if (read != -1) {
            System.out.println("接受到客户端的数据：" + new String(bytes, 0, read));
        }
        clientSocket.getOutputStream().write("HelloClient".getBytes());
        clientSocket.getOutputStream().flush();
    }

}
