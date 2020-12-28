package com.kq.simple.protocol.demo1.dto;

import java.io.Serializable;

/**
 * @author kq
 * @date 2020-12-28 14:51
 * @since 2020-0630
 */
public class Message implements Serializable {

    private int type;
    private String message;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", message='" + message + '\'' +
                '}';
    }
}
