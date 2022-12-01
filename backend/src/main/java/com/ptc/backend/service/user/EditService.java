package com.ptc.backend.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ptc.backend.controller.utils.ResultData;
import com.ptc.backend.pojo.OrdinaryUser;

public interface EditService extends IService<OrdinaryUser> {
    ResultData<String> editUser(String username,String gender, String password, String newPassword);
}
