package com.craig.user.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description =  "pagination result model")
public class PageResult<T> {
    /**
     * reult data
     */
    private List<T> data;
    /*
     * current page
     */
    @Schema(description = "current page")
    private Long current;
    /**
     * page size
     */
    @Schema(description = "page size")
    private Long size;
    /**
     * total record count
     */
    @Schema(description = "total record count")
    private Long total;
}
