package com.example.asyncimportexporttask.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.asyncimportexporttask.entity.AsyncImportExportTask;

import java.util.List;
/**
 * @Author sqy
 * @Date 2023-10-19 15-37
 **/



public interface IAsyncImportExportTaskService extends IService<AsyncImportExportTask> {

    /**
     * 查询我的任务列表
     *
     * @param page                  分页参数
     * @param asyncImportExportTask 公司ID、用户ID
     * @return 我的导入导出任务列表
     */
    IPage<AsyncImportExportTask> selectJobOrderByPage(IPage<AsyncImportExportTask> page, AsyncImportExportTask asyncImportExportTask);

    /**
     * 查询状态为 init 的任务
     * @return 需要处理的任务列表
     */
    List<AsyncImportExportTask> getTaskAndUpdateStatusSynchronized();
}
