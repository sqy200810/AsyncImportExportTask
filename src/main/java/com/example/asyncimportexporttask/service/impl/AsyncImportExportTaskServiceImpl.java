package com.example.asyncimportexporttask.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.asyncimportexporttask.entity.AsyncImportExportTask;
import com.example.asyncimportexporttask.mapper.AsyncImportExportTaskMapper;
import com.example.asyncimportexporttask.service.IAsyncImportExportTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author sqy
 * @Date 2023-10-19 15-38
 **/
@Service
public class AsyncImportExportTaskServiceImpl extends ServiceImpl<AsyncImportExportTaskMapper, AsyncImportExportTask> implements IAsyncImportExportTaskService {


    @Autowired
    private AsyncImportExportTaskMapper asyncImportExportTaskMapper;

    @Override
    public IPage<AsyncImportExportTask> selectJobOrderByPage(IPage<AsyncImportExportTask> page, AsyncImportExportTask asyncImportExportTask) {
        QueryWrapper<AsyncImportExportTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(asyncImportExportTask.getTaskStatus() != null, "task_status", asyncImportExportTask.getTaskStatus());
        queryWrapper.like(StringUtils.hasText(asyncImportExportTask.getTaskName()), "task_name", asyncImportExportTask.getTaskName());
        if (asyncImportExportTask.getBeginTime() != null) {
            queryWrapper.ge("created_time", asyncImportExportTask.getBeginTime());
        }
        if (asyncImportExportTask.getEndTime() != null) {
            queryWrapper.le("created_time", asyncImportExportTask.getEndTime());
        }
        queryWrapper.eq("user_id", asyncImportExportTask.getUserId());
        queryWrapper.eq("company_id", asyncImportExportTask.getCompanyId());
        queryWrapper.orderByDesc("created_time");
        return asyncImportExportTaskMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<AsyncImportExportTask> getTaskAndUpdateStatusSynchronized() {
        // 查询状态为 init 的任务
        List<AsyncImportExportTask> list = asyncImportExportTaskMapper.selectByTimeAndStatus(DateUtil.date(), 1);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Long> taskIds = new ArrayList<>();
        list.forEach(asyncImportExportTask -> {
            taskIds.add(asyncImportExportTask.getTaskId());
        });
        UpdateWrapper<AsyncImportExportTask> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("task_status", 2);
        updateWrapper.set("deal_time", new Date());
        updateWrapper.in("task_id", taskIds);
        asyncImportExportTaskMapper.update(null, updateWrapper);
        return list;
    }

}

