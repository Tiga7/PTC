package com.ptc.backend.service.record;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ptc.backend.pojo.Record;

public interface RecordService extends IService<Record> {
    String addRecord(Record record);

    JSONObject getList(Integer page, Integer size);
}
