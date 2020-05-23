package com.netty.secodexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author 周雨农
 * @date 2020-05-19 21:21
 * @description
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     *  服务端启动后，会执行该方法，向客户端发送数据
     *  发送数据后，会接收到客户端传递过来的数据(一直循环)
     * @param ctx 比较重要，可以获取远程的地址，Channel对象，连接对象
     * @param msg 客户端发送过来的请求对象
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("请求远程地址；" + ctx.channel().remoteAddress() + "," + msg);
        //写出数据并且清除
        ctx.channel().writeAndFlush("from server：" + UUID.randomUUID());
    }

    /**
     * 中途出现异常会执行此方法
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
