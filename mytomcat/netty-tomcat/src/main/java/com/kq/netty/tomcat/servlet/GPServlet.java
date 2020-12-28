package com.kq.netty.tomcat.servlet;

public abstract class GPServlet {

    public void service(GPRequest request,GPResponse response) throws Exception {
        if("GET".equalsIgnoreCase(request.getMethod())) {
            this.doGet(request,response);
        } else {
            this.doPost(request,response);
        }
    }


    public abstract void doGet(GPRequest request,GPResponse response) throws Exception;
    public abstract void doPost(GPRequest request,GPResponse response) throws Exception;
}
