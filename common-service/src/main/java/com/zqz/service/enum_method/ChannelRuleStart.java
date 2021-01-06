package com.zqz.service.enum_method;

import org.junit.Test;

import java.util.Optional;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 13:51 2020/12/24
 */
public class ChannelRuleStart {

    @Test
    public void test1(){
        String name = "STUDET";
        GeneralChannelRule rule = Optional.ofNullable(ChannelRuleEnum.match(name))
                .map(ChannelRuleEnum::getChannel)
                .orElseThrow(() -> new RuntimeException("some exception msg..."));
        rule.process();
    }
}
