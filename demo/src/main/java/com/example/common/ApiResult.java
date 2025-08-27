package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ApiResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(2001, "Success", data);
    }
    public static <T> ApiResult<T> success(String msg, T data) {
        return new ApiResult<>(2001, msg, data);
    }
    public static ApiResult<?> failed(String msg) {
        return new ApiResult<>(2003, msg, null);
    }
}