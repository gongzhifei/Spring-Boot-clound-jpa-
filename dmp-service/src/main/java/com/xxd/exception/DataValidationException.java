package com.xxd.exception;

/**
 * @author gongzhifei
 */
public class DataValidationException extends RuntimeException {

    private String className;

    public DataValidationException(String className){
        super("数据验证失败！");
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
