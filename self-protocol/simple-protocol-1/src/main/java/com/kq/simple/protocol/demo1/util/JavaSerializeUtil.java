package com.kq.simple.protocol.demo1.util;

import com.kq.simple.protocol.demo1.dto.Message;

import java.io.*;

/**
 * @author kq
 * @date 2020-12-28 14:48
 * @since 2020-0630
 */
public class JavaSerializeUtil {

    /**
     * 对象转数组
     * @param obj
     * @return
     */
    public static byte[] toByteArray (Object obj) {

        byte[] bytes = null;

        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);) {

            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 数组转对象
     * @param bytes
     * @return
     */
    public static Object toObject (byte[] bytes) {

        Object obj = null;
        try(ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);) {

            obj = ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return obj;
    }


    public static void main(String[] args) {
        Message message = new Message();
        message.setType(6);
        message.setMessage("你好！ this is first demo !");

        byte[] bytes = toByteArray(message);

        Message message1 = (Message)toObject (bytes);
        System.out.println("message1="+message1);
    }

}
