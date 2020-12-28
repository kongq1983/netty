package com.kq.simple.protocol.demo1.codec;

import com.kq.simple.protocol.demo1.util.Constants;
import com.kq.simple.protocol.demo1.util.JavaSerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author kq
 * @date 2020-12-28 14:58
 * @since 2020-0630
 */
public class Decoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        int availableBytes = in.readableBytes(); // writerIndex - readerIndex
        if (availableBytes < 8) {
            return; // 魔幻数字(4字节)+长度(4字节)
        }
        // 打个标记，有可能要回退的
        in.markReaderIndex();

        // 魔幻数字
        int magicNumber = in.readInt();

        //不是魔幻数字开头
        if(magicNumber!= Constants.MAGIC_NUMBER) {
            System.out.println("DECODER decode 不是魔幻数字开头！！！");
            in.resetReaderIndex();
            return;
        }

        // 消息长度
        int length = in.readInt();

        if (in.readableBytes() < length) { // readerIndex(markedReaderIndex);
            in.resetReaderIndex();
            return;
        }

        byte[] payload = new byte[length];

        in.readBytes(payload);

        Object data = JavaSerializeUtil.toObject(payload);
        if(data!=null) {
            out.add(data);
        } else {
            System.out.println("DECODER decode is fail !");
        }
    }
}
