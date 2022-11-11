package com.ptc.backend.service.record;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ptc.backend.pojo.Record;
import com.ptc.backend.utils.ResultData;

import java.util.Collection;
import java.util.List;

public interface RecordService extends IService<Record> {
    String addRecord(Record record);

    List<Record> getList();
}
