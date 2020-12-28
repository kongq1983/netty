package com.kq.netty.tomcat.servlet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class GPRequest {

    private String method;
    private String url;

    private HttpRequest request;
    private ChannelHandlerContext ctx;

    public GPRequest(ChannelHandlerContext ctx,HttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    public String getMethod() {
        return request.getMethod().name();
    }



    public String getUrl() {
        return request.getUri();
    }

    public Map<String, List<String>> getParameters(){
        QueryStringDecoder decoder = new QueryStringDecoder(this.getUrl());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        if(param==null){
            return null;
        } else {
            return param.get(0);
        }
    }

}
