package com.zqz.service.task;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:44 2020/12/31
 */
public class BatchTaskThree implements BatchTask{
    @Override
    public TaskResp doTask() {
        TaskResp resp = new TaskResp();
        resp.setCode("0003");
        resp.setMsg("Task Three Success");
        return resp;
    }
}
