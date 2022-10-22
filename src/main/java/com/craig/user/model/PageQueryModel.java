package com.craig.user.model;

import lombok.Data;

@Data
public class PageQueryModel {
    /**
     * page size
     */
    public long size = 10;

    /**
     * current page, start from 1
     */
    public long current = 1;
}
