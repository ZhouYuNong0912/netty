package com.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @Author: zyn
 * @Create: 2021-01-19 15:38
 * @Description:
 **/
public class ChatClient {

    public static void main(String[] args) {
        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动对象
            Bootstrap bootstrap = new Bootstrap();
            //设置相关参数
            bootstrap.group(group)  //设置线程组
                    .channel(NioSocketChannel.class)       //使用NioSocketChannel作为客户端的通道实现
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //加入处理器
                            socketChannel.pipeline()
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(new ChatClientHandler()
                                    );
                        }
                    });
            System.out.println("NettyClient Start...");
            //启动客户端去连接服务器
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            //得到Channel
            Channel channel = channelFuture.channel();
            System.out.println("======" + channel.remoteAddress() + "======");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                //通过channel将信息发送给服务器端
                channel.writeAndFlush(msg);
            }
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }


}
