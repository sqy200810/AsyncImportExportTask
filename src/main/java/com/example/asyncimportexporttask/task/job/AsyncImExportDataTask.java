package com.example.asyncimportexporttask.task.job;


import com.example.asyncimportexporttask.task.AsyncImExportDataHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author sqy
 * @Date 2023-10-19 15-41
 **/
@Slf4j
@Component
@EnableScheduling
public class AsyncImExportDataTask {
    @Autowired
    private AsyncImExportDataHandle asyncImExportDataHandle;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void asyncImExportData() {
        asyncImExportDataHandle.dealAsyncTask();
    }
}
