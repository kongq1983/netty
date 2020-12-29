package com.kq.netty.tomcat.starter;

import com.kq.netty.tomcat.server.GPTomcat;
import com.kq.netty.tomcat.server.GPTomcat1;

public class NettyStarter {

    public static void main(String[] args) {
        new GPTomcat().start();
//        new GPTomcat1().start();
    }

}
