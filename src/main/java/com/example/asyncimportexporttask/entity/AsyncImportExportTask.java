package com.example.asyncimportexporttask.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author sqy
 * @Date 2023-10-19 14-31
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("async_import_export_task")
public class AsyncImportExportTask {

    /**
     * 任务id
     */
    @TableId(value = "task_id", type = IdType.ID_WORKER)
    private Long taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 公司 id
     */
    private Long companyId;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 任务优先级
     * 只针对当前用户的任务起作用
     */
    private Integer priority;

    /**
     * 请求参数
     */
    private String reqParam;


    /**
     * D-A-01 下载当天呼叫记录
     * D-A-02 下载历史呼叫记录
     * U-C-01 导入客户数据
     * D-C-01 导出客户数据
     * U-U-01 导入用户数据
     * D-U-01 导出用户数据
     */
    private String taskCode;

    /**
     * 任务状态
     * 1：init
     * 2: running
     * 3: finish(success)
     * 4: finish(fail)
     */
    private Integer taskStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dealTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    private String tag;

    /**
     * 处理结果
     */
    private String result;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date endTime;

    /**
     * 任务类型
     * 1：上传（导入）
     * 2：下载（导出）
     */
    private Integer taskType;

}

