package com.zqz.service.factory;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:26 AM 2020/7/21
 */
public class TestProcess {

    public static void main(String[] args) {
        //工场模式
//        String type = "refund";
//        ProcessService process = ProcessFactory.getProcess(type);
//        String process1 = process.process();
//        System.out.println(process1);

        //策略模式
//        OrderProcess process = new OrderProcess("order");
        RefundProcess process = new RefundProcess("refund");
        StrategyContext context = new StrategyContext(process);
        String execute = context.execute();
        System.out.println(execute);
    }



}
