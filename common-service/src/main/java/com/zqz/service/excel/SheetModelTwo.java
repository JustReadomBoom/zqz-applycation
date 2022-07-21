package com.zqz.service.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: SheetModelTwo
 * @Date: Created in 17:27 2022/3/8
 */
@Data
public class SheetModelTwo {

    @ExcelProperty(index = 0)
    private String orderId;

    @ExcelProperty(index = 1)
    private BigDecimal price;

    @ExcelProperty(index = 2)
    private String createTime;

    @ExcelProperty(index = 3)
    private String name;
}
