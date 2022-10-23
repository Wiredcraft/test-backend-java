package com.craig.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PageQueryModel {
    /**
     * page size
     */
    @Schema(description = "page size", example = "10")
    public long size = 10;

    /**
     * current page, start from 1
     */
    @Schema(description = "current page", example = "1")
    public long current = 1;
}
