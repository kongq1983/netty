package com.kq.netty.tomcat.server;

import com.kq.netty.tomcat.handler.GPTomcatHandler;
import com.kq.netty.tomcat.servlet.GPServlet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class GPTomcat {
    final int port = 8080;
    private ServerSocket serverSocket;
    private Map<String, GPServlet> servletMapping = new HashMap<>();
    private Properties properties = new Properties();
    private AtomicInteger ato = new AtomicInteger();

    private void init(){
        String webInfo = GPTomcat.class.getResource("/").getPath();
        try(FileInputStream in = new FileInputStream(webInfo+"web.properties")){
            properties.load(in);

            for(Object k : properties.keySet()) {
                String key = k.toString();
                if(key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$","");
                    String url = properties.getProperty(key);
                    String className = properties.getProperty(servletName+".className");

                    // 单实例 多线程
                    GPServlet servlet = (GPServlet)Class.forName(className).newInstance();
                    servletMapping.put(url,servlet);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(){
        init();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();


        try{
            ServerBootstrap server = new ServerBootstrap();

            server.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            ch.pipeline().addLast(new HttpRequestDecoder());
                            ch.pipeline().addLast(new GPTomcatHandler(servletMapping));

                        }
                    }).option(ChannelOption.SO_BACKLOG,128)
            .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture f = server.bind(port).sync();
            System.out.println("GPTomcat is Started ! Listener Port :"+this.port);

            f.channel().closeFuture().sync();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
