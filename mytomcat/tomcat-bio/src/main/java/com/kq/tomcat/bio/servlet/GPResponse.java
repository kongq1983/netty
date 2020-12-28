package com.kq.tomcat.bio.servlet;

import java.io.OutputStream;

public class GPResponse {

    private OutputStream out;

    public GPResponse(OutputStream out) {
        this.out = out;
    }

    public void write(String s) throws Exception {
        //200
        StringBuilder str = new StringBuilder();
        str.append("HTTP/1.1 200 OK\n");
        str.append("Content-Type: text/html;\n");
        str.append("\r\n");
        str.append(s);

        out.write(str.toString().getBytes());
    }

}
