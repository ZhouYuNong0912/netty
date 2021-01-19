package com.io.nio;

import io.netty.channel.ServerChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: zyn
 * @Create: 2021-01-16 22:45
 * @Description:
 **/
public class NioServer {

    //保护客户端连接，一并处理
    static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //创建NIO ServerSocketChannel
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));
        //设置ServerSocketChannel为非阻塞
        serverSocket.configureBlocking(false);
        System.out.println("服务启动成功");

        while (true) {
            //非阻塞accept方法不会阻塞。如果设置为true则会进行阻塞
            //NIO的非阻塞由操作系统内部进行实现，底层调用了Linux内容的accept函数
            SocketChannel socketChannel = serverSocket.accept();
            if (socketChannel != null) {
                System.out.println("连接成功");
                //设置SocketChannel为非阻塞
                socketChannel.configureBlocking(false);
                //保存客户端连接在List中
                channelList.add(socketChannel);
            }
            //遍历连接进行数据读取
            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                //非阻塞模式read方法不会阻塞
                int len = sc.read(byteBuffer);
                //有数据则进行打印
                if (len > 0) {
                    System.out.println("接受到数据：" + new String(byteBuffer.array()));
                } else if (len == -1) {
                    iterator.remove();
                    System.out.println("客户端断开连接。");
                }
            }

        }


    }


}
