package com.kq.tomcat.bio.server;

import com.kq.tomcat.bio.servlet.GPRequest;
import com.kq.tomcat.bio.servlet.GPResponse;
import com.kq.tomcat.bio.servlet.GPServlet;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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

        try{
            serverSocket = new ServerSocket(this.port);
            System.out.println("GPTomcat is Started ! Listener Port :"+this.port);

            //wait for user request

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("index="+ato.incrementAndGet()+" localPort="+socket.getLocalPort()+" port="+socket.getPort());

                this.process(socket);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Socket socket) {
        System.out.println("process enter index="+ato.get());
        try(InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream()){
            System.out.println("process logic index="+ato.get());
            GPRequest request = new GPRequest(in);
            System.out.println("process request index="+ato.get());
            GPResponse response = new GPResponse(out);
            System.out.println("process response index="+ato.get());
//            favicon.ico:1 GET http://localhost:8080/favicon.ico
            String url = request.getUrl();
//            if(url==null) {
//
//            }
//            if(url.indexOf("favicon.ico")>0){
//                return;
//            }
            System.out.println("url="+url);
            if(servletMapping.containsKey(url)) {
                servletMapping.get(url).service(request,response);
            }else {
                response.write("404 - Not Found");
            }

            out.flush();


        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
