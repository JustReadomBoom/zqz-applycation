package com.zqz.service.task;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:44 2020/12/31
 */
public class BatchTaskTwo implements BatchTask{
    @Override
    public TaskResp doTask() {
        TaskResp resp = new TaskResp();
        resp.setCode("0002");
        resp.setMsg("Task Two Success");
        return resp;
    }
}
