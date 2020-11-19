package com.zqz.service.strategy;

import org.springframework.stereotype.Service;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:14 2020/11/13
 */
@Service
public class PrimaryMemberStrategy implements MemberStrategy{
    @Override
    public double calcPrice(double booksPrice) {
        System.out.println("对于初级会员的没有折扣");
        return booksPrice;
    }
}
