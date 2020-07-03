package com.kq.buffer;

import java.nio.ByteBuffer;

/**
 * @author kq
 * @date 2020-06-30 18:50
 * @since 2020-0630
 */
public class BufferWrap {

    public static void main(String[] args) {
        //缓存区分配
        ByteBuffer buffer = ByteBuffer.allocate(10);

        //包装1个现有数组
        byte array[] = new byte[10];
        ByteBuffer buffer1 = ByteBuffer.wrap(array);

    }

}
