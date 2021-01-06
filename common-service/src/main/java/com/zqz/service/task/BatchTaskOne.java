package com.zqz.service.task;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:44 2020/12/31
 */
public class BatchTaskOne implements BatchTask{
    @Override
    public TaskResp doTask() {
        TaskResp resp = new TaskResp();
        resp.setCode("0001");
        resp.setMsg("Task One Success");
        return resp;
    }
}
