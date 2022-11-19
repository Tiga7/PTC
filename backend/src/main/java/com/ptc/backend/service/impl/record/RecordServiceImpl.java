package com.ptc.backend.service.impl.record;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptc.backend.mapper.RecordMapper;
import com.ptc.backend.mapper.RoomMapper;
import com.ptc.backend.mapper.UserMapper;
import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.pojo.Record;
import com.ptc.backend.pojo.Room;
import com.ptc.backend.service.Po2Vo;
import com.ptc.backend.service.record.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoomMapper roomMapper;

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
        if (record.getDescription() == null) {
            record.setDescription("这个人很懒,什么没说.");
        }
        recordMapper.insert(record);
        return "打卡成功";

    }

    @Override
    public JSONObject getList(Integer page, Integer size) {
        IPage<Record> iPage = new Page<>(0, 20);
        IPage<Record> items = recordMapper.selectPage(iPage, null);
        List<Record> records = items.getRecords();

        int totalCount = (int) items.getTotal();

        QueryWrapper<OrdinaryUser> qwForUser = new QueryWrapper<>();
        QueryWrapper<Room> qwForRoom = new QueryWrapper<>();

        List<RecordVo> recordVos = new ArrayList<>();
        for (Record record : records) {
            qwForUser.clear();
            qwForRoom.clear();
            qwForUser.eq("id", record.getUserId());
            qwForRoom.eq("id", record.getRoomId());
            RecordVo recordVo = Po2Vo.INSTANCE.recordAndUser2Vo(record,
                    userMapper.selectOne(qwForUser),
                    roomMapper.selectOne(qwForRoom));

            recordVos.add(recordVo);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total_count", items.getTotal());
        jsonObject.put("records", recordVos);

        return jsonObject;
    }
}
