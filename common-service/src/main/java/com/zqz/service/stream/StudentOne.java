package com.zqz.service.stream;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: StudentOne
 * @Date: Created in 15:16 2022/4/11
 */
@Data
public class StudentOne {

    private Integer id;

    private String name;

    private List<StudentTwo> address = new ArrayList<>();
}
