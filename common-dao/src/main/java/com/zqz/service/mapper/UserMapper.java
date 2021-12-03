package com.zqz.service.mapper;

import com.zqz.service.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:59 2021/12/3
 */
@Mapper
public interface UserMapper {

    int insertSelective(User user);

    List<User> getAll();

    void deleteById(Integer id);
}
