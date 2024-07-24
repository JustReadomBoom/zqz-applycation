package com.zqz.service.mapper;

import com.zqz.service.entity.LoanDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: zqz
 * @CreateTime: 2024/07/24
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface LoanDetailMapper {

    int insertBatch(List<LoanDetail> list);
}
