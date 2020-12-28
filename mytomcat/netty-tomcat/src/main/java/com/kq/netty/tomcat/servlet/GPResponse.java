package com.kq.netty.tomcat.servlet;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

public class GPResponse {

    private HttpRequest request;
    private ChannelHandlerContext ctx;

    public GPResponse(ChannelHandlerContext ctx,HttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }
    public void write(String out) throws Exception {
        try{
            if(out==null||out.length()==0) {
                return;
            }

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(out.getBytes("UTF-8")));

            response.headers().set("Content-Type","text/html;");
            ctx.write(response);

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ctx.flush();
            ctx.close();
        }
    }

}
