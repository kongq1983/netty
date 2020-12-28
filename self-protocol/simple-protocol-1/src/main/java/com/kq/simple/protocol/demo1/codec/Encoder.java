package com.kq.simple.protocol.demo1.codec;

import com.kq.simple.protocol.demo1.dto.Message;
import com.kq.simple.protocol.demo1.util.Constants;
import com.kq.simple.protocol.demo1.util.JavaSerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * @author kq
 * @date 2020-12-28 14:30
 * @since 2020-0630
 */
public class Encoder extends MessageToByteEncoder<Object> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

        if(msg instanceof Message) {
            this.writeMessage(out, Constants.MAGIC_NUMBER, JavaSerializeUtil.toByteArray(msg));
        }else {
            System.out.println("Encoder.encode 未知的消息类型！！！");
        }

    }

    private void writeMessage(ByteBuf out, int magicNumber, byte[] bytes) {
        // 魔幻数字 4字节
        out.writeInt(magicNumber);
        //长度 4字节
        out.writeInt(bytes.length);
        // 数据
        out.writeBytes(bytes);
    }


}
