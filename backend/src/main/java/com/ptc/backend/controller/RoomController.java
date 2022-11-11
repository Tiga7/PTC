package com.ptc.backend.controller;

import com.ptc.backend.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room/")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public String getList(){
        return null;
    }
}
