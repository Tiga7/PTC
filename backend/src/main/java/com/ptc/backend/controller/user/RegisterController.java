package com.ptc.backend.controller.user;

import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.service.user.RegisterService;
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
    public Map<String, String> register(@RequestParam Map<String, String> data) {
        String sno = data.get("sno");
        String password = data.get("password");
        String confirmPwd = data.get("confirmPwd");
        String phone = data.get("phone");
        return registerService.register(sno, password,confirmPwd,phone);
    }

}
