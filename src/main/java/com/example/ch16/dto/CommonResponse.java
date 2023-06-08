package com.example.ch16.dto;

public enum CommonResponse {

    SUCCESS(0,"Succese"), FAIL(-1, "Fail");
    int code;
    String msg;

    CommonResponse(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
