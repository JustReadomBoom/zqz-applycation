package com.zqz.service.mapper;

import com.zqz.service.entity.OrderRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderRecordMapper {

    int insertBatch(List<OrderRecord> list);

    List<OrderRecord> selectByOrderId(String orderId);

}