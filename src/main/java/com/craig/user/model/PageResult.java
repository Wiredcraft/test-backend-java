package com.craig.user.model;

import java.util.List;

import lombok.Data;

@Data
public class PageResult<T> {
    /**
     * reult data
     */
    private List<T> data;
    /*
     * current page
     */
    private Long current;
    /**
     * page size
     */
    private Long size;
    /**
     * total record count
     */
    private Long total;
}
