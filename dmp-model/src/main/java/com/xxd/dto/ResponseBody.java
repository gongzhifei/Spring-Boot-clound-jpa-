package com.xxd.dto;

import java.util.List;

/**
 * @author gongzhifei
 */
public class ResponseBody<T> {

    public ResponseBody() {

    }

    public ResponseBody(List<T> data, int pageIndex, Long totalCount, boolean success, String message) {
        this.data = data;
        this.pageIndex = pageIndex;
        this.totalCount = totalCount;
        this.success = success;
        this.message = message;
    }

    public ResponseBody(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseBody(List<T> data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    private List<T> data;

    private int pageIndex;

    private Long totalCount;

    private boolean success;

    private String message;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
