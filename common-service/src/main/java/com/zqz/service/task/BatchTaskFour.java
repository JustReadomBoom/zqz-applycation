package com.zqz.service.task;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:45 2020/12/31
 */
public class BatchTaskFour implements BatchTask{


    @Override
    public TaskResp doTask() {
        TaskResp resp = new TaskResp();
        resp.setCode("0004");
        resp.setMsg("Task Four Success");
        return resp;
    }
}
