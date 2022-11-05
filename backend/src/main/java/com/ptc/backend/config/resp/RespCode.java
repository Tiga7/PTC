package com.ptc.backend.config.resp;

import lombok.Getter;

@Getter
public enum RespCode implements StatusCode {
    /**
     *
     */
    SUCCESS(1000, "success"),
    FAILED(2000, "failed"),
    VALIDATE_ERROR(3000, "参数校验失败"),
    RESPONSE_PACK_ERROR(4000, "返回包装失败");

    private final Integer code;
    private final String msg;

    RespCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
