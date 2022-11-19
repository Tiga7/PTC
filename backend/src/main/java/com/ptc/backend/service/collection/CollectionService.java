package com.ptc.backend.service.collection;

import com.alibaba.fastjson.JSONObject;

public interface CollectionService {
    JSONObject getList(Integer page, Integer size);

    String addCollection(Integer roomId);

    String removeCollection(Integer collectionId);
}
