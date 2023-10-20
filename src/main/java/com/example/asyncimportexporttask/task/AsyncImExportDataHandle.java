package com.example.asyncimportexporttask.task;


import com.example.asyncimportexporttask.entity.AsyncImportExportTask;
import com.example.asyncimportexporttask.service.AsyncTaskCommonService;
import com.example.asyncimportexporttask.service.IAsyncImportExportTaskService;
import com.example.asyncimportexporttask.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author sqy
 * @Date 2023-10-19 15-42
 **/
@Component
@Slf4j
public class AsyncImExportDataHandle {

    static ThreadPoolExecutor.CallerRunsPolicy policy = new ThreadPoolExecutor.CallerRunsPolicy();

    static ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>(200), new ThreadFactory() {
        int num = 0;

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("async-task-thread-" + ++num);
            return thread;
        }
    }, policy);


    @Autowired
    private IAsyncImportExportTaskService asyncImportExportTaskService;

    public void dealAsyncTask() {
        List<AsyncImportExportTask> list = asyncImportExportTaskService.getTaskAndUpdateStatusSynchronized();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        log.info("====> 需要处理任务条数：{}", list.size());
        for (AsyncImportExportTask asyncImportExportTask : list) {
            ((AsyncTaskCommonService) SpringContextHolder.getBean(asyncImportExportTask.getTaskCode())).invoke(asyncImportExportTask);
        }

        /*list.forEach(asyncImportExportTask -> CompletableFuture.runAsync(
                () -> ((AsyncTaskCommonService)SpringContextHolder.getBean(asyncImportExportTask.getTaskCode())).invoke(asyncImportExportTask), executor));*/
    }
}

