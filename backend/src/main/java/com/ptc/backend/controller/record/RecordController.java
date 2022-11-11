package com.ptc.backend.controller.record;

import com.ptc.backend.pojo.Record;
import com.ptc.backend.service.record.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/record/")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping
    public List<Record> getRecordList() {
        return recordService.getList();
    }

    @PostMapping
    public String addRecord(@RequestParam Map<String, String> data) {

        Integer userId = Integer.parseInt(data.get("user_id"));
        Integer roomId = Integer.parseInt(data.get("room_id"));
        String description = data.get("description");

        return recordService.addRecord(Record.builder()
                .userId(userId)
                .roomId(roomId)
                .description(description)
                .modifyTime(LocalDateTime.now())
                .build()
        );
    }

}
