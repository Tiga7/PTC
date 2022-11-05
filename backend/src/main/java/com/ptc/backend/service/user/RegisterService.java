package com.ptc.backend.service.user;

import com.ptc.backend.pojo.OrdinaryUser;

import java.util.List;
import java.util.Map;

public interface RegisterService {
    public Map<String,String>  register(String sno, String password, String confirmPwd,String phone);
}
