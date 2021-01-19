package com.nettyold.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author 周雨农
 * @date 2020-05-17 15:48
 * @description
 */
public class TestServer {

    public static void main(String[] args) throws Exception{
        /**
         * NioEventLoopGroup用于处理I/O操作的多线程事件循环器，Netty提供了许多不同的EventLoopGroup实现用来处理不同的传输。
         * 一般会有2个NioEventLoopGroup会被使用
         * 第一个通常被叫做"boss"，用来接收进来的连接。
         * 第二个经常被叫做"worker",用来处理已经被接受的连接。
         * 一旦"Boss"接受到连接，就会将连接信息注册到"worker"上。能够知道如何有多少个线程被使用。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /**
             * 启动NIO服务的辅助启动类，可以在这个服务中直接使用Channel。
             * 但是这会是一个复杂的处理过程。
             */
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    //定义Channel通道
                    .channel(NioServerSocketChannel.class)
                    //定义子处理器
                    .childHandler(new TestServerInitializer());
            //绑定端口，开始接收传递进来的对象
            ChannelFuture future = serverBootstrap.bind(8899).sync();
            //关闭监听
            future.channel().closeFuture().sync();
        }finally {
            //关闭NioEventLoopGroup
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
