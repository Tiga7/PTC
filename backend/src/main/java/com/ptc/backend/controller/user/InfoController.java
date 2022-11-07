package com.ptc.backend.controller.user;


import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.service.user.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InfoController {

    @Autowired
    private InfoService infoService;

    @RequestMapping("/api/user/info/")
    public OrdinaryUser getInfo() {
        return infoService.getInfo();
    }
}
