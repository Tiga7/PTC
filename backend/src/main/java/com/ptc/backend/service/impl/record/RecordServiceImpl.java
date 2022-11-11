package com.ptc.backend.service.impl.record;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptc.backend.mapper.RecordMapper;
import com.ptc.backend.pojo.Record;
import com.ptc.backend.service.record.RecordService;
import com.ptc.backend.utils.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public String addRecord(Record record) {
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", record.getUserId());
        List<Record> records = recordMapper.selectList(queryWrapper);

        for (Record r : records) {
            if (r.getModifyTime().getDayOfYear() == LocalDate.now().getDayOfYear()
                    && r.getModifyTime().getYear() == LocalDate.now().getYear()) {
                return "今天已经打过卡了,请明天再打卡吧.";
            }
        }
        recordMapper.insert(record);
        return "打卡成功";

    }

    @Override
    public List<Record> getList() {

        IPage<Record> page = new Page<>(0, 20);
        IPage<Record> items = recordMapper.selectPage(page, null);
        return items.getRecords();
    }
}
