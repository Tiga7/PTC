package com.ptc.backend.controller.user;

import com.ptc.backend.service.user.LoginService;
import com.ptc.backend.controller.utils.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/api/user/token/")
    public ResultData<Map<String,String>> getToken(@RequestParam Map<String, String> data) {

        String sno = data.get("sno");
        String password = data.get("password");

        return loginService.getToken(sno, password);
    }

}
