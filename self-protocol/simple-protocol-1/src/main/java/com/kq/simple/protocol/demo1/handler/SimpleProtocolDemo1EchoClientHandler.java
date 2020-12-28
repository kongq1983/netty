package com.kq.simple.protocol.demo1.handler;

import com.kq.simple.protocol.demo1.dto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qikong on 2019/6/22.
 */
public class SimpleProtocolDemo1EchoClientHandler extends ChannelInboundHandlerAdapter{

//    private final Message firstMessage;

    private AtomicInteger atomicInteger = new AtomicInteger();

    public SimpleProtocolDemo1EchoClientHandler(){
//        firstMessage = Unpooled.buffer(256);
//
//        for(int i=0;i<firstMessage.capacity();i++) {
//            firstMessage.writeByte((byte)i);
//        }


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        Message m = getMessage();
        System.out.println("channelActive 给服务器发送数据:"+m);
        ctx.writeAndFlush(m);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) {
        System.out.println("收到服务器数据，messsage="+msg);
        // 休息3s
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){
            e.printStackTrace();
        }

        Message m = getMessage();
        System.out.println("channelRead 给服务器发送数据:"+m);
        ctx.writeAndFlush(m);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


    private Message getMessage() {
        Message m = new Message();
        m.setType(2);
        m.setMessage("message body is , index="+atomicInteger.incrementAndGet());
        return m;
    }

}
