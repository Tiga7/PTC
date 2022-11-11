package com.ptc.backend.controller.user;


import com.ptc.backend.service.user.EditService;
import com.ptc.backend.utils.ResultData;
import com.ptc.backend.utils.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EditController {

    @Autowired
    private EditService editService;

    @PostMapping("/api/user/edit/")
    public ResultData<String> editUser(@RequestParam Map<String,String> data){

        String username = data.get("username");
        String gender = data.get("gender");
        String password = data.get("password");
        String newPassword = data.get("new_password");
        String confirmPassword = data.get("confirm_password");
        if(password==null || password.length()==0)
        {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "密码不能为空");
        }
        if (newPassword==null || newPassword.length()==0)
        {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "新密码不能为空");
        }
        if (!newPassword.equals(confirmPassword))
        {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "两个新密码不一致");
        }
        return editService.editUser(username,gender,password,newPassword);
    }
}
