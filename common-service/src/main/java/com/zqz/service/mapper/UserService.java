package com.zqz.service.mapper;

import com.zqz.service.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:07 2021/12/3
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public int insert(User user){
        return userMapper.insertSelective(user);
    }

    public List<User> getAll() {
        return userMapper.getAll();
    }

    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }
}
