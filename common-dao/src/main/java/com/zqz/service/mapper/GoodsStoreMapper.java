package com.zqz.service.mapper;

import com.zqz.service.entity.GoodsStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsStoreMapper {

    GoodsStore getGoodsStore(@Param("code") String code);

    int updateGoodsStore(@Param("code") String code, @Param("count") int count);
}