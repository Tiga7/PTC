package com.ptc.backend;

import com.ptc.backend.mapper.RoomMapper;
import com.ptc.backend.pojo.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private RoomMapper roomMapper;

    @Test
    void contextLoads() {
//        String buildingName = "五号楼";
//        String[] room = new String[]{"210", "220", "230", "510", "520", "530"};
//
//        for (String s : room) {
//            for (int i = 0; i < 10; i++) {
//                roomMapper.insert(Room.builder()
//                        .buildingName(buildingName)
//                        .roomName(s + i)
//                        .capacity(100)
//                        .build());
//                ;
//            }
//        }


    }

}
