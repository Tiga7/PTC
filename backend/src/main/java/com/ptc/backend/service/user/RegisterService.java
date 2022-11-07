package com.ptc.backend.service.user;

import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.utils.ResultData;

import java.util.List;
import java.util.Map;

public interface RegisterService {
    public ResultData<String> register(String sno, String password, String confirmPwd, String phone);
}
