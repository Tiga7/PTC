package com.ptc.backend.service.user;

import com.ptc.backend.controller.utils.ResultData;

public interface RegisterService {
    public ResultData<String> register(String sno, String password, String confirmPwd, String phone);
}
