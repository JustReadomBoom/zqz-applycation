package com.zqz.service.enums;

import com.zqz.service.factory.ProcessService;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:46 AM 2020/7/21
 */
public enum ProcessEnum implements ProcessService {


    ORDER_TYPE{
        public String process() {
            return "订单类型业务";
        }
    },

    REFUND_TYPE{
        public String process() {
            return "退货类型业务";
        }
    }






}
