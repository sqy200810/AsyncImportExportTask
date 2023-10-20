package com.example.asyncimportexporttask.service;


import com.example.asyncimportexporttask.entity.AsyncImportExportTask;

/**
 * @Author sqy
 * @Date 2023-10-19 15-43
 **/
public interface AsyncTaskCommonService {
    /**
     * 执行任务
     *
     * @param asyncImportExportTask
     */
    void invoke(AsyncImportExportTask asyncImportExportTask);
}
