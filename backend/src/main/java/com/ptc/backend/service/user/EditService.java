package com.ptc.backend.service.user;

import com.ptc.backend.utils.ResultData;

import java.util.Map;

public interface EditService {
    ResultData<String> editUser(String username,String gender, String password, String newPassword);
}
