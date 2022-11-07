package com.ptc.backend.controller.user;

import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.service.user.RegisterService;
import com.ptc.backend.utils.ResultData;
import com.ptc.backend.utils.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/api/user/register/")
    public ResultData<String> register(@RequestParam Map<String, String> data) {
        String sno = data.get("sno");
        String password = data.get("password");
        String confirmPwd = data.get("confirmPwd");
        String phone = data.get("phone");

        if (sno == null || sno.length() != 9) {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "学号长度不正确");
        }
        if (password == null || password.length() == 0) {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "密码不能为空");
        }
        if (!password.equals(confirmPwd)) {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "两次密码不一致");
        }
        if (phone == null || phone.length() == 0) {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "手机号不能为空");
        }
        return registerService.register(sno, password,confirmPwd,phone);
    }

}
