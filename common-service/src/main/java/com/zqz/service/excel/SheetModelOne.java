package com.zqz.service.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: SheetModelOne
 * @Date: Created in 17:26 2022/3/8
 */
@Data
public class SheetModelOne {

    @ExcelProperty(index = 0)
    private String name;

    @ExcelProperty(index = 1)
    private Integer age;

    @ExcelProperty(index = 2)
    private String contactNum;

    @ExcelProperty(index = 3)
    private String address;
}
