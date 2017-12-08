package com.xxd.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongzhifei on 2017/7/3.
 */
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 5452766031354261421L;

    public static final int DEFAULT_PAGE_SIZE = 20;

    private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

    private int pageIndex; // 当前页第一条数据在List中的位置,从0开始

    private List<T> result; // 当前页中存放的记录,类型一般为List

    private int totalCount; // 总记录数

    /**
     * 构造方法，只构造空页.
     */
    public PageInfo() {
        this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
    }

    /**
     * 构造方法，第一页开始，每页为默认容量
     * @param totalSize
     * @param data
     */
    public PageInfo(int totalSize, List<T> data) {
        this(0, totalSize, DEFAULT_PAGE_SIZE, data);
    }

    /**
     * 默认构造方法.
     *
     * @param pageIndex	 本页数据在数据库中的起始位置
     * @param totalCount 数据库中总记录条数
     * @param pageSize  本页容量
     * @param result	  本页包含的数据
     */
    public PageInfo(int pageIndex, int totalCount, int pageSize, List<T> result) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalCount = totalCount;
        this.result = result;
    }

    public PageInfo(int pageIndex, int pageSize){
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    /**
     * 取总记录数.
     */
    public int getTotalCount() {
        return this.totalCount;
    }

    /**
     * 取总页数.
     */
    public long getTotalPageCount() {
        if (totalCount % pageSize == 0) {
            return totalCount / pageSize;
        } else {
            return totalCount / pageSize + 1;
        }
    }

    /**
     * 取每页数据容量.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 取当前页中的记录.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 取该页当前页码,页码从1开始.
     */
    public long getCurrentPageNo() {
        return pageIndex / pageSize + 1;
    }

    /**
     * 该页是否有下一页.
     */
    public boolean hasNextPage() {
        return this.getCurrentPageNo() < this.getTotalPageCount() - 1;
    }

    /**
     * 该页是否有上一页.
     */
    public boolean hasPreviousPage() {
        return this.getCurrentPageNo() > 1;
    }

    /**
     * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
     *
     * @see #getStartOfPage(int,int)
     */
    protected static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取任一页第一条数据在数据集的位置.
     *
     * @param pageNo   从1开始的页号
     * @param pageSize 每页记录条数
     * @return 该页第一条数据
     */
    public static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
