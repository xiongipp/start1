package com.xxh.start1.dto;

import com.xxh.start1.exception.CustomizeErrorCode;
import com.xxh.start1.exception.CustomizeException;
import lombok.Data;


public class ResultDTO {
    private  Integer code;
    private  String message;
    public  static ResultDTO errorof(Integer code,String message){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO errorof(CustomizeException errorCode) {
        return errorof(errorCode.getCode(),errorCode.getMessage());
    }


    public static ResultDTO okof() {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO errorof(CustomizeErrorCode e) {
        return errorof(e.getCode(),e.getMessage());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
