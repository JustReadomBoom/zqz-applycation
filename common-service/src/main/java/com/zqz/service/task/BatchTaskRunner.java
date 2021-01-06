package com.zqz.service.task;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:33 2020/12/31
 */
@Slf4j
public class BatchTaskRunner extends RecursiveAction {
    protected int threshold; //每个线程处理的任务数
    protected List taskList;
    Consumer<List> action;

    /**
     * @param taskList  任务列表
     * @param threshold 每个线程处理的任务数
     */
    private BatchTaskRunner(List taskList, int threshold, Consumer action) {
        this.taskList = taskList;
        this.threshold = threshold;
        this.action = action;
    }

    /**
     * 多线程批量执行任务
     *
     * @param taskList
     * @param threshold
     * @param action
     */
    public static <T> void execute(List<T> taskList, int threshold, Consumer<List<T>> action) {
        new BatchTaskRunner(taskList, threshold, action).invoke();
    }


    @Override
    protected void compute() {
        if (taskList.size() <= threshold) {
            this.action.accept(taskList);
        } else {
            this.splitFromMiddle(taskList);
        }
    }

    /**
     * 任务中分
     *
     * @param list
     */
    private void splitFromMiddle(List list) {
        int middle = (int) Math.ceil(list.size() / 2.0);
        List leftList = list.subList(0, middle);
        List RightList = list.subList(middle, list.size());
        BatchTaskRunner left = newInstance(leftList);
        BatchTaskRunner right = newInstance(RightList);
        ForkJoinTask.invokeAll(left, right);
    }

    private BatchTaskRunner newInstance(List taskList) {
        return new BatchTaskRunner(taskList, threshold, action);
    }


    public static void main(String[] args) {
        List<TaskResp> allTasks = new ArrayList<>();
        BatchTaskOne taskOne = new BatchTaskOne();
        BatchTaskTwo taskTwo = new BatchTaskTwo();
        BatchTaskThree taskThree = new BatchTaskThree();
        BatchTaskFour taskFour = new BatchTaskFour();
        allTasks.add(taskOne.doTask());
        allTasks.add(taskTwo.doTask());
        allTasks.add(taskThree.doTask());
        allTasks.add(taskFour.doTask());
        int taskPerThread = 1;
        BatchTaskRunner.execute(allTasks, taskPerThread, result -> {
            log.info("{} ----> {}", Thread.currentThread().getName(), result);
        });
    }
}
