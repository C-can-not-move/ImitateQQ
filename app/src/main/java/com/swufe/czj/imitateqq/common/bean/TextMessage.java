package com.swufe.czj.imitateqq.common.bean;

import java.io.Serializable;

/**
 * 文本消息
 *
 */
public class TextMessage implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String message;

    public TextMessage() {

    }

    public TextMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
