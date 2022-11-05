package com.ptc.backend.service.user;

import com.ptc.backend.config.resp.RespVo;
import com.ptc.backend.pojo.OrdinaryUser;

public interface EditService {
    RespVo editUser(String sno, String password);
}
