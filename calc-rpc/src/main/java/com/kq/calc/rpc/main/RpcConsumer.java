package com.kq.calc.rpc.main;

import com.kq.calc.rpc.api.IRpcHelloService;
import com.kq.calc.rpc.api.IRpcService;
import com.kq.calc.rpc.consume.proxy.RpcProxy;

/**
 * server:RpcRegistry
 */
public class RpcConsumer {

    public static void main(String[] args) {
        IRpcHelloService rpcHelloService = RpcProxy.create(IRpcHelloService.class);
        System.out.println(rpcHelloService.hello("king"));

        IRpcService service = RpcProxy.create(IRpcService.class);
        System.out.println("8+2="+service.add(2,8));
    }

}
