package com.kq.calc.rpc.provider;

import com.kq.calc.rpc.api.IRpcHelloService;

public class RpcHelloServiceImpl implements IRpcHelloService {


    @Override
    public String hello(String name) {
        return "Hello, "+name+"!";
    }
}
