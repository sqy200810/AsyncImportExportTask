package com.example.asyncimportexporttask.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.asyncimportexporttask.entity.AsyncImportExportTask;
import com.example.asyncimportexporttask.service.IAsyncImportExportTaskService;
import com.example.asyncimportexporttask.utils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author sqy
 * @Date 2023-10-19 15-51
 **/
@RestController
@RequestMapping("/vos/custom/task")
@Api(value = "我的任务", tags = {"我的任务"})
public class MyTaskController {

    @Resource
    private IAsyncImportExportTaskService asyncImportExportTaskService;

    /**
     * 查询我的任务
     *
     * @param page                  分页参数
     * @param asyncImportExportTask 用户ID
     * @return 我的任务列表
     */
    @PostMapping("/queryMyTask")
    public R queryMyTask(IPage<AsyncImportExportTask> page, @RequestBody AsyncImportExportTask asyncImportExportTask) {
        asyncImportExportTask.setCompanyId(-1L);
        asyncImportExportTask.setUserId(1L);
        return R.ok(asyncImportExportTaskService.selectJobOrderByPage(page, asyncImportExportTask));
    }

    /**
     * 下载任务文件
     *
     * @param taskId 任务ID
     * @return 文件下载路径
     */
    @GetMapping("/downloadFile")
    public R downloadFile(Long taskId) {
        return R.ok("对应任务保存的文件路径");
    }
}

