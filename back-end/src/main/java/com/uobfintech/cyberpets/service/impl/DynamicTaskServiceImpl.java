package com.uobfintech.cyberpets.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class DynamicTaskServiceImpl {
    @Autowired
    private TaskScheduler taskScheduler;  // 自动注入配置的TaskScheduler

    public void scheduleTask(Date dateTime) {
        taskScheduler.schedule(this::executeTask, dateTime);
    }

    private void executeTask() {
        System.out.println("执行计划的任务");
    }
}
