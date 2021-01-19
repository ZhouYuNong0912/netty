package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: zyn
 * @Create: 2021-01-19 15:10
 * @Description:
 **/
public class NettyServer {

    public static void main(String[] args) {
        //创建两个线程组bossGroup和workerGroup,含有的子线程NioEventLoopGroup的个数默认为CPU核数的两倍
        //bossGroup只处理连接请求，真正和客户端进行业务处理会交由workerGroup完成
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器的启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来配置参数
            bootstrap.group(bossGroup,workerGroup)      //设置两个线程组
                    .channel(NioServerSocketChannel.class)  //使用NioServerSocketChannel作为服务器的通道实现
                    //初始化服务器连接队列大小，服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接
                    //多个客户端同时来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理
                    .option(ChannelOption.SO_BACKLOG,1024)
                    //创建通道初始化对象，设置初始化参数
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("Netty Server Start...");
            ChannelFuture channelFuture = bootstrap.bind(9000).sync();
            //给cf注册监听事件，监听我们关心的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("监听端口号9000成功");
                    } else {
                        System.out.println("监听端口号9000失败");
                    }
                }
            });
            //对通道关闭进行监听，closeFuture是异步操作，监听通道关闭
            //通道sync方法同步等待通道关闭处理完毕，此处会阻塞等待通道关闭完成
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
