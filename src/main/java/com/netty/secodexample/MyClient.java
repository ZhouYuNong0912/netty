package com.netty.secodexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author 周雨农
 * @date 2020-05-19 21:39
 * @description 客户端
 */
public class MyClient {
    public static void main(String[] args) throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            //客户端定义为BootStrap
            Bootstrap bootstrap = new Bootstrap();
            //channel中为NioSocketChannel.class
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();
         }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
