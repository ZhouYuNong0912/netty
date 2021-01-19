package com.netty.chat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zyn
 * @Create: 2021-01-19 15:28
 * @Description:
 **/
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 表示channel处于就需状态，准备上线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //上线了通知其他客户端
        channelGroup.writeAndFlush("[ 客户端 ]" + channel.remoteAddress() + " 上线了 " + sdf.format(new Date()) +"\n");
        channelGroup.add(channel);
        System.out.println(channel.remoteAddress() + "上线了");

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush("[ 客户端 ]" + ch.remoteAddress() + "发送了信息：" + msg +"\n");
            } else {
                ch.writeAndFlush("[ 自己 ]发送了信息：" + msg + "\n");
            }
        });
    }

    /**
     * 表示Channel处于不活动状态，提示下线
     *
     * @param ctx
         * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将信息推送给其他的客户端
        channelGroup.writeAndFlush("[ 客户端 ]" + channel.remoteAddress() + "下线了");
        System.out.println(channel.remoteAddress() + "下线了");
        System.out.println("ChannelGroup Size=" + channelGroup.size());
    }
}
