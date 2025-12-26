package com.elderlycare.dto;

import java.util.List;

public class ApiResponse<T> {
    private T data;
    private long total;
    private String message;
    private int code;

    public ApiResponse() {
    }

    public ApiResponse(T data, long total) {
        this.data = data;
        this.total = total;
        this.code = 200;
        this.message = "success";
    }

    public ApiResponse(T data, long total, String message, int code) {
        this.data = data;
        this.total = total;
        this.message = message;
        this.code = code;
    }

    public static <T> ApiResponse<T> success(T data, long total) {
        return new ApiResponse<>(data, total);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, data instanceof List ? ((List<?>) data).size() : 1);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, 0, message, 500);
    }

    // Getters and Setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
