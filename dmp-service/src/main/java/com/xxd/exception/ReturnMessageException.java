package com.xxd.exception;

/**
 * @author gongzhifei
 */
public class ReturnMessageException extends RuntimeException {

    private boolean success;

    private String content;

    public ReturnMessageException(boolean success,String content){
        super(content);
        this.success = success;
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
