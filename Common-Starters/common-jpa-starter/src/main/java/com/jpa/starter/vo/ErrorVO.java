package com.jpa.starter.vo;

public class ErrorVO {
    private Boolean isSuccess = false; // 是否成功
    private String description;  // 原因

    public ErrorVO(String description) {
        this.description = description;
    }

}
