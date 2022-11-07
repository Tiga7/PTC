package com.ptc.backend.service.user;

import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.utils.ResultData;

import java.util.Map;

public interface LoginService {
    public ResultData<Map<String,String>> getToken(String sno, String password);

}
