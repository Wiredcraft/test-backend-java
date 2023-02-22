package com.wiredcraft.testbackend.entity.param;

/**
 * page param
 * author: yongchen
 * date: 2023/2/18
 */
public class PageParam {
    /**
     * current page number
     */
    private Integer page = 0;

    /**
     * number of displays per page
     */
    private Integer pageSize = 10;

    public PageParam() {
    }

    public PageParam(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
