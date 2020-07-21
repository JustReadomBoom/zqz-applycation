package com.zqz.service.factory;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:36 AM 2020/7/21
 */
public class StrategyContext {

    private ProcessService processService;

    public StrategyContext(ProcessService processService){
        this.processService = processService;
    }

    public String execute(){
        return processService.process();
    }
}
