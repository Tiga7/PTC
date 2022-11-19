package com.ptc.backend.service.user;

import com.ptc.backend.controller.utils.ResultData;

public interface EditService {
    ResultData<String> editUser(String username,String gender, String password, String newPassword);
}
