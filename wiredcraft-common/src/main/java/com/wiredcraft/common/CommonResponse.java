package com.wiredcraft.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jeremy.zhang
 * @date 2022-10-19
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonResponse <T>{
    private Integer code;
    private String message;
    private T data;

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(0, "success", data);
    }
}
