package com.ptc.backend.controller.user;

import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/api/user/token/")
    public Map<String, String> getToken(@RequestParam Map<String, String> map) {

        String sno = map.get("sno");
        String password = map.get("password");

        return loginService.getToken(sno,password);
    }


}
