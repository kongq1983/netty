package com.kq.buffer;

import java.nio.ByteBuffer;

/**
 * @author kq
 * @date 2020-06-30 19:02
 * @since 2020-0630
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        // 缓冲区中的数据0-9
        for(int i=0;i<byteBuffer.capacity();++i){
            byteBuffer.put((byte)i);
        }

        //创建只读缓存区 只读缓冲区对保护数据很有作用
        ByteBuffer readonly = byteBuffer.asReadOnlyBuffer();
        // 改变原缓冲区的内容
        for(int i=0;i<byteBuffer.capacity();++i){
            byte b = byteBuffer.get(i);
            b *=10;
            byteBuffer.put(i,b);
        }

        readonly.position(0);
        readonly.limit(byteBuffer.capacity());

        //只读缓冲区的内容也随之改变
        while (readonly.remaining()>0) {
            System.out.println(readonly.get());
        }



    }

}
