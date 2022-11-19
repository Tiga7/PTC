package com.ptc.backend.service.user;

import com.ptc.backend.controller.utils.ResultData;

import java.util.Map;

public interface LoginService {
    public ResultData<Map<String,String>> getToken(String sno, String password);

}
