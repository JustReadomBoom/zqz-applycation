package com.zqz.service.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: ExcelListener
 * @Date: Created in 17:32 2022/3/8
 */
@Slf4j
@Service
public class ExcelListener extends AnalysisEventListener<Object> {

    private List<Object> data = new ArrayList<>();


    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(o));
        data.add(o);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        log.info("所有数据解析完毕!");
    }

    private void saveData() {
        log.info("{} 条数据, 开始入库!", data.size());


        log.info("入库成功!");
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
