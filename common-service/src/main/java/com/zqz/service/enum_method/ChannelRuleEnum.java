package com.zqz.service.enum_method;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 13:48 2020/12/24
 */
@Getter
@AllArgsConstructor
public enum ChannelRuleEnum {

    STUDENT("STUDENT", new StudentChannelRule()),
    TEACHER("TEACHER", new TeacherChannelRule());

    public String name;
    public GeneralChannelRule channel;

    public static ChannelRuleEnum match(String name){
        ChannelRuleEnum[] values = ChannelRuleEnum.values();
        for(ChannelRuleEnum value : values){
            if(value.name.equals(name)){
                return value;
            }
        }
        return null;
    }
}
