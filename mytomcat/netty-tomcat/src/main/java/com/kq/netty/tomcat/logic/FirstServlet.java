package com.kq.netty.tomcat.logic;


import com.kq.netty.tomcat.servlet.GPRequest;
import com.kq.netty.tomcat.servlet.GPResponse;
import com.kq.netty.tomcat.servlet.GPServlet;

public class FirstServlet extends GPServlet {


    @Override
    public void doGet(GPRequest request, GPResponse response) throws Exception {
        this.doPost(request,response);
    }

    @Override
    public void doPost(GPRequest request, GPResponse response) throws Exception {
        response.write("This is First Servlet!");
    }
}
