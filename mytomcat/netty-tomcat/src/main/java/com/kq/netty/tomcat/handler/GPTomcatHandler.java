package com.kq.netty.tomcat.handler;

import com.kq.netty.tomcat.servlet.GPRequest;
import com.kq.netty.tomcat.servlet.GPResponse;
import com.kq.netty.tomcat.servlet.GPServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Map;

public class GPTomcatHandler extends ChannelInboundHandlerAdapter {

    Map<String, GPServlet> servletMapping;

    public GPTomcatHandler(Map<String, GPServlet> servletMapping){
        this.servletMapping = servletMapping;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest)msg;
            GPRequest request = new GPRequest(ctx,req);
            GPResponse response = new GPResponse(ctx,req);
            String url = request.getUrl();

            System.out.println("url="+url);
            if(servletMapping.containsKey(url)) {
                servletMapping.get(url).service(request,response);
            }else {
                response.write("404 - Not Found");
            }

        }
    }

}
