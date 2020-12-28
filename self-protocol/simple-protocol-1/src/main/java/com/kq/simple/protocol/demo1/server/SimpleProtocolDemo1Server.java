package com.kq.simple.protocol.demo1.server;

import com.kq.simple.protocol.demo1.codec.Decoder;
import com.kq.simple.protocol.demo1.codec.Encoder;
import com.kq.simple.protocol.demo1.handler.SimpleProtocolDemo1ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author kq
 * @date 2020-12-28 15:17
 * @since 2020-0630
 */
public class SimpleProtocolDemo1Server {

    public static  final int PORT = 8007;

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final SimpleProtocolDemo1ServerHandler serverHandler = new SimpleProtocolDemo1ServerHandler();

        try{

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new Encoder());
                            p.addLast(new Decoder());
                            p.addLast(serverHandler);
                        }
                    });

            // 启动器启动
            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("Server is start ! port="+PORT);
            // 等待服务器关闭
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


}

