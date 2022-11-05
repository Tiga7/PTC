package com.ptc.backend.service.user;

import com.ptc.backend.pojo.OrdinaryUser;

import java.util.Map;

public interface LoginService {
    public Map<String, String> getToken(String sno, String password);

}
