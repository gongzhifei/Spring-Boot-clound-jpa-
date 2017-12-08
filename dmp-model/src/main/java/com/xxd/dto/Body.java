package com.xxd.dto;



/**
 * @author gongzhifei
 */
public class Body<Boolean,T> {

    private T data;

    private Boolean success;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
