package com.kq.echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by qikong on 2019/6/22.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter{

    private final ByteBuf firstMessage;

    public EchoClientHandler(){
        firstMessage = Unpooled.buffer(256);

        for(int i=0;i<firstMessage.capacity();i++) {
            firstMessage.writeByte((byte)i);
        }


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("给服务器发送数据:"+firstMessage);
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) {
        System.out.println("收到服务器数据，还给服务器:"+msg);
    }

    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
