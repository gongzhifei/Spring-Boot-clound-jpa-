package com.xxd.dto.market.condition;

/**
 * @author gongzhifei
 */
public class PageSize {

    /**
     * 每页条数
     */
    private int pageSize=20;

    /**
     * 页码
     */
    private int pageNo=1;


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
