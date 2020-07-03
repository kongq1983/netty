package com.kq.buffer;

import java.io.FileInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author kq
 * @date 2020-06-30 18:38
 * @since 2020-0630
 */
public class BufferDemo {

    public static void main(String[] args) throws Exception{
        // 文件内容比如 1268
        FileInputStream in = new FileInputStream("D:\\tmp\\test.txt");
        FileChannel fc = in.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        output("初始化",byteBuffer);
        fc.read(byteBuffer);
        output("调用read()",byteBuffer);
        byteBuffer.flip();
        output("flip()",byteBuffer);

        while (byteBuffer.remaining()>0) {
            byte b = byteBuffer.get();
        }

        output("get()",byteBuffer);
        byteBuffer.clear();
        output("clear()",byteBuffer);

        in.close();

    }


    public static void output(String step, Buffer buffer) {
        System.out.println(step+" : ");
        System.out.print("capacity: "+buffer.capacity()+", ");
        System.out.print("position: "+buffer.position()+", ");
        System.out.println("limit: "+buffer.limit());
        System.out.println();
    }


}
