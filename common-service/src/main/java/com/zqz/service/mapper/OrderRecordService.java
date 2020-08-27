package com.zqz.service.mapper;

import com.zqz.service.entity.OrderRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 16:52 2020/8/24
 */
@Service
public class OrderRecordService {
    @Resource
    private OrderRecordMapper mapper;

    public int insertBatch(List<OrderRecord> records){
        return mapper.insertBatch(records);
    }
}
