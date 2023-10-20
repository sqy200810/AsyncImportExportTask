package com.example.asyncimportexporttask.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.asyncimportexporttask.entity.AsyncImportExportTask;
import com.example.asyncimportexporttask.service.IAsyncImportExportTaskService;
import com.example.asyncimportexporttask.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @Author sqy
 * @Date 2023-10-19 15-32
 **/
@RestController
public class AsyncImportExportController {
    @Resource
    private IAsyncImportExportTaskService asyncImportExportTaskService;

    /**
     * 该导导出接口仅保存至AsyncImportExportTask中导出参数
     * 用户企业ID、用户ID应拿用户真实信息，方便查询我的任务时使用
     * @param jsonObject 导出参数
     * @return 导出成功，详情查看我的任务
     */
    @PostMapping("/export/user")
    public R<String> export(@RequestBody JSONObject jsonObject) {
        AsyncImportExportTask asyncImportExportTask = new AsyncImportExportTask();
        asyncImportExportTask.setCreatedTime(new Date());
        asyncImportExportTask.setTaskCode("D-A-01");
        asyncImportExportTask.setTaskName("导出当日呼叫记录");
        asyncImportExportTask.setReqParam(jsonObject.toJSONString());
        asyncImportExportTask.setTaskId(Long.parseLong(UUID.randomUUID().toString()));
        asyncImportExportTask.setCompanyId(-1L);//用户企业ID
        asyncImportExportTask.setTaskStatus(1);
        asyncImportExportTask.setTaskType(2);
        asyncImportExportTask.setUserId(1L);//用户ID
        asyncImportExportTaskService.save(asyncImportExportTask);
        return R.ok("操作成功,请去任务中心查看");
    }
}
