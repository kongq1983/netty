package com.kq.calc.rpc.consume.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcProxy {

    public static <T> T create(Class<?> clazz) {
        return null;
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

            }

            return null;
        }


    }

}
