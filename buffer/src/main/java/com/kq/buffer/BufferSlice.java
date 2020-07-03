package com.kq.buffer;

import java.nio.ByteBuffer;

/**
 * @author kq
 * @date 2020-06-30 18:52
 * @since 2020-0630
 */
public class BufferSlice {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        //缓存区中的数据0-9
        for(int i=0;i<byteBuffer.capacity();++i) {
            byteBuffer.put((byte)i);
        }

        //创建子缓存冲区
        byteBuffer.position(3);
        byteBuffer.limit(7);

        //共享内存
        ByteBuffer slice = byteBuffer.slice();

        //改变子缓存区的内容  子缓存冲区和原缓存区是共享的
        for(int i=0;i<slice.capacity();++i) {
            byte b = slice.get(i);
            b *=10;
            slice.put(i,b);
        }

        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());

        while (byteBuffer.remaining()>0) {
            System.out.println(byteBuffer.get());
        }

    }

}
