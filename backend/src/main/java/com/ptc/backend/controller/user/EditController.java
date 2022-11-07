package com.ptc.backend.controller.user;


import com.ptc.backend.service.user.EditService;
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
    public Map<String,String> editUser(@RequestParam Map<String,String> data){

        return editService.editUser(data.get("sno"),data.get("password"));
    }
}
