package com.kq.calc.rpc.consume.proxy;

import com.kq.calc.rpc.protocol.InvokerProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcProxy {

    public static <T> T create(Class<?> clazz) {
        MethodProxy proxy = new MethodProxy(clazz);
        Class<?>[] interfaces = clazz.isInterface()?new Class[]{clazz}:clazz.getInterfaces();
        T result = (T) Proxy.newProxyInstance(clazz.getClassLoader(),interfaces,proxy);
        return result;
    }

    private static class MethodProxy implements InvocationHandler {

        private Class<?> clazz;

        public MethodProxy(Class<?> clazz){
            this.clazz = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if(Object.class.equals(method.getDeclaringClass())) {
                try{
                    return method.invoke(this,args);
                }catch (Throwable t) {
                    t.printStackTrace();
                }
            }else {
                return rpcInvoke(proxy,method,args);
            }

            return null;
        }

        public Object rpcInvoke(Object proxy,Method method,Object[] args) {
            InvokerProtocol invokerProtocol = new InvokerProtocol();
            invokerProtocol.setClassName(this.clazz.getName());
            invokerProtocol.setMethodName(method.getName());
            invokerProtocol.setValues(args);
            invokerProtocol.setParams(method.getParameterTypes());

//            final RpcP
            RpcProxyHandler handler = new RpcProxyHandler();
            EventLoopGroup group = new NioEventLoopGroup();
            try{
                Bootstrap b = new Bootstrap();
                b.group(group).channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY,true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                                pipeline.addLast("frameEncoder",new LengthFieldPrepender(4));
                                pipeline.addLast("encoder",new ObjectEncoder());
                                pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                pipeline.addLast("handler",handler);
                            }
                        });

                ChannelFuture future = b.connect("localhost",8080).sync();
                future.channel().writeAndFlush(invokerProtocol).sync();
                future.channel().closeFuture().sync();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                group.shutdownGracefully();
            }
            return handler.getResponse();
        }

    }



}
