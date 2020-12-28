package com.kq.tomcat.bio.logic;

import com.kq.tomcat.bio.servlet.GPRequest;
import com.kq.tomcat.bio.servlet.GPResponse;
import com.kq.tomcat.bio.servlet.GPServlet;

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
