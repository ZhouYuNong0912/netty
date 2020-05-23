package com.netty.secodexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @author 周雨农
 * @date 2020-05-19 21:44
 * @description
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 客户端启动后，会执行该方法，获取服务端传递过来的数据
     * 获取到数据后，会向服务端发送数据(一直循环)
     * @param ctx 比较重要，可以获取远程的地址，Channel对象，连接对象
     * @param msg 服务端发送过来的请求对象
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client output：" + msg);
        ctx.writeAndFlush("from client" + LocalDateTime.now());
    }

    /**
     * Channel通道连接会执行此方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自于客户端的问候!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
