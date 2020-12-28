package com.kq.simple.protocol.demo1.client;

import com.kq.simple.protocol.demo1.codec.Decoder;
import com.kq.simple.protocol.demo1.codec.Encoder;
import com.kq.simple.protocol.demo1.handler.SimpleProtocolDemo1EchoClientHandler;
import com.kq.simple.protocol.demo1.server.SimpleProtocolDemo1Server;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author kq
 * @date 2020-12-28 15:19
 * @since 2020-0630
 */
public class SimpleProtocolDemo1Client {

    static final String HOST = "127.0.0.1";

    static final int PORT = SimpleProtocolDemo1Server.PORT;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new Encoder());
                            p.addLast(new Decoder());
                            p.addLast(new SimpleProtocolDemo1EchoClientHandler());
                        }
                    });


            ChannelFuture f = b.connect(HOST,PORT).sync();

            f.channel().closeFuture().sync();

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
