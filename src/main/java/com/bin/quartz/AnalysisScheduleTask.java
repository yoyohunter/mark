package com.bin.quartz;

import org.quartz.JobExecutionContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangbin on 16/10/26.
 */

public class AnalysisScheduleTask {
    /**
     * 调度创建表，方法中的参数是JobExecutionContext类型，要使MyDetailQuartzJobBean中的executeInternal方法中利用反射机制调用到相应的方法
     */
    public void createTableTask(JobExecutionContext context) {
        System.out.println("@创建表..." + new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date()));
    }

    /**
     * 调度任务栈，方法中的参数是JobExecutionContext类型，要使MyDetailQuartzJobBean中的executeInternal方法中利用反射机制调用到相应的方法
     */
    public void secheduleTask(JobExecutionContext context) {
        System.out.println("@调度任务栈..." + new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date()));
    }

    /**
     * 删除任务栈，方法中的参数是JobExecutionContext类型，要使MyDetailQuartzJobBean中的executeInternal方法中利用反射机制调用到相应的方法
     */
    public void deleteTask(JobExecutionContext context) {
        System.out.println("deleteTask");
    }
}