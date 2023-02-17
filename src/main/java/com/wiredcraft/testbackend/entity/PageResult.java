package com.wiredcraft.testbackend.entity;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Paging return object
 * author: yongchen
 * date: 2023/2/18
 */
public class PageResult<T> {

    /**
     * number of displays per page
     */
    private Integer pageSize;

    /**
     * current page number
     */
    private Integer curPage;

    /**
     * total number of data
     */
    private Long total;

    /**
     * total page
     */
    private Integer totalPage;

    /**
     * data
     */
    private List<T> data;

    public PageResult() {
    }

    public PageResult(Page<T> pageData) {
        this.total = pageData.getTotalElements();
        this.pageSize = pageData.getSize();
        this.curPage = pageData.getNumber();
        this.totalPage = pageData.getTotalPages();
        this.data = pageData.getContent();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
