package com.zqz.service.mapper;

import com.zqz.service.entity.GoodsStore;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 3:26 PM 2020/7/21
 */
@Service
public class GoodsStoreService {
    @Resource
    private GoodsStoreMapper mapper;

    public GoodsStore getGoodsStore(String code){
        return mapper.getGoodsStore(code);
    }

    public int updateGoodsStore(String code, int count){
        return mapper.updateGoodsStore(code, count);
    }
}
