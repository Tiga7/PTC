package com.ptc.backend.config.resp;

import lombok.Data;

@Data
public class RespVo {
    // 状态码
    private Integer code;

    // 状态信息
    private String msg;

    // 返回对象
    private Object data;

    // 手动设置返回vo
    public RespVo(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 默认返回成功状态码，数据对象
    public RespVo(Object data) {
        this.code = RespCode.SUCCESS.getCode();
        this.msg = RespCode.SUCCESS.getMsg();
        this.data = data;
    }

    // 只返回状态码
    public RespVo(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = null;
    }

    // 返回指定状态码，数据对象
    public RespVo(StatusCode statusCode, Object data) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

}