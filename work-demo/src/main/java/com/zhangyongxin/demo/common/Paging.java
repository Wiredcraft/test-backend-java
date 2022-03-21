package com.zhangyongxin.demo.common;

/**
 * @Auther zhangyongxin
 * @date 2022/3/19 下午10:25
 */
public interface Paging {

     int getCurrentPage();

     void setCurrentPage(int currentPage);

     int getPageSize();

     void setPageSize(int pagesize);

     boolean isEnableCount();

     void setEnableCount(boolean enable);
}
