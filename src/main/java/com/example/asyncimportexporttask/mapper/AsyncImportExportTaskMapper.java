package com.example.asyncimportexporttask.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.asyncimportexporttask.entity.AsyncImportExportTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



/**
 * @Author sqy
 * @Date 2023-10-19 15-26
 **/
@Mapper
@Repository
public interface AsyncImportExportTaskMapper extends BaseMapper<AsyncImportExportTask> {
    /**
     * 根据时间和状态查询要处理的定时任务
     *
     * @param now 当前时间
     * @param i 状态
     * @return 任务列表
     */
    List<AsyncImportExportTask> selectByTimeAndStatus(@Param("beginTime") DateTime now, @Param("status") int i);

}


