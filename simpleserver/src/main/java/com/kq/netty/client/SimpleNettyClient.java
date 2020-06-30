package com.kq.netty.client;

import com.kq.netty.server.SimpleNettyServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * NettyClient
 *
 * @author kq
 * @date 2019-01-08
 */
public class SimpleNettyClient implements Runnable {

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                    pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                    pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                    pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                    pipeline.addLast("handler", new HelloClient());
                }
            });
            for (int i = 0; i < 100000; i++) {
                ChannelFuture f = b.connect("127.0.0.1", SimpleNettyServer.PORT).sync();
                f.channel().writeAndFlush("hello Service!" + Thread.currentThread().getName() + ":--->:" + i);
                Thread.sleep(10000);
                f.channel().closeFuture().sync();
            }


        } catch (Exception e) {
            System.out.println("客户端报异常:"+e);
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1; i++) {
            new Thread(new SimpleNettyClient(), ">>>this thread " + i).start();
        }
    }
}
