package com.ptc.backend.controller.collection;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ptc.backend.pojo.Collection;
import com.ptc.backend.service.collection.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/collection/")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping("list")
    public JSONObject getList(@RequestParam Map<String, String> data) {

        Integer page = Integer.parseInt(data.get("page"));
        Integer size = Integer.parseInt(data.get("size"));

        return collectionService.getList(page, size);
    }

    @PostMapping("add")
    public String addCollection(@RequestParam Map<String, String> data) {
        Integer roomId = Integer.parseInt(data.get("room_id"));
        return collectionService.addCollection(roomId);
    }

    @DeleteMapping()
    public String removeCollection(@RequestParam Map<String, String> data) {
         Integer collectionId = Integer.parseInt(data.get("collection_id"));

        return collectionService.removeCollection(collectionId);

    }

}
