package com.zqz.service.strategy;

import org.springframework.stereotype.Service;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:15 2020/11/13
 */
@Service
public class IntermediateMemberStrategy implements MemberStrategy{
    @Override
    public double calcPrice(double booksPrice) {

        System.out.println("对于中级会员的折扣为10%");
        return booksPrice * 0.9;
    }
}
