package com.kq.buffer;

import java.io.FileInputStream;
import java.nio.channels.FileChannel;

/**
 * @author kq
 * @date 2020-06-30 19:21
 * @since 2020-0630
 */
public class DirectBuffer {

    public static void main(String[] args) throws Exception{
        // 53页
        String infile = "D:\\tmp\\test_in.txt";
        // 首先从磁盘读取文件内容
        FileInputStream in = new FileInputStream(infile);
        FileChannel fileChannel = in.getChannel();

        //把读入的内容写到一个新的文件
        String outfile = "D:\\tmp\\test_out.txt";

    }

}
