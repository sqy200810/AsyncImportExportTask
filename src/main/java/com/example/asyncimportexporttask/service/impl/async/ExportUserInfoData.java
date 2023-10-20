package com.example.asyncimportexporttask.service.impl.async;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.asyncimportexporttask.entity.AsyncImportExportTask;
import com.example.asyncimportexporttask.entity.User;
import com.example.asyncimportexporttask.mapper.UserMapper;
import com.example.asyncimportexporttask.service.AsyncTaskCommonService;
import com.example.asyncimportexporttask.service.IAsyncImportExportTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Author sqy
 * @Date 2023-10-19 15-45
 **/
@Component("D-A-01")
@Slf4j
public class ExportUserInfoData implements AsyncTaskCommonService {

    @Value("${temp.file.path}")
    private String baseTempFilePath;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private IAsyncImportExportTaskService asyncImportExportTaskService;

    @Override
    public void invoke(AsyncImportExportTask asyncImportExportTask) {
        log.info(Thread.currentThread().getName() + "导出历史呼叫记录。。。");

        //导出时的参数，需要根据参数来导出数据
        final String reqParam = asyncImportExportTask.getReqParam();

        //创建csv文件到指定路径
        String filePath = baseTempFilePath + UUID.randomUUID() + ".csv";
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        try (FileWriter fw = new FileWriter(file, true)) {
            if (!file.exists()) {
                file.createNewFile();
            }

            // 添加 BOM 头，防止 excel 打开 csv 中文乱码
            final byte[] bytes = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
            fw.write(getChars(bytes));
            fw.write("姓名,昵称,地址,生日,性别,手机");
            fw.write("\r\n");
            boolean continueFlag = true;
            int num = 0;
            //以分页的方式查询所需数据写入csv文件，此时的FileWriter仅执行flush 不要关闭
            while (continueFlag) {
                //自定义分页参数，每次查询1000条数据
                IPage<User> page = new Page<>();
                page.setCurrent(++num);
                page.setSize(1000);

                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                IPage<User> userPageInfo = userMapper.selectPage(page, queryWrapper);
                final List<User> rows = userPageInfo.getRecords();
                //若当次查询的条数小于1000，则设置continueFlag为false，跳出循环
                if (rows.size() < 1000) {
                    continueFlag = false;
                }
                //将每次查询的数据写入csv
                for (User row : rows) {
                    write(fw, row);
                }
                fw.flush();
            }
        } catch (Exception e) {
            log.error("invoke FileWriter error:", e);
        }
        String tag = filePath.substring(1);
        asyncImportExportTask.setResult("任务成功");
        asyncImportExportTask.setFinishTime(new Date());
        asyncImportExportTask.setTaskStatus(3);
        asyncImportExportTask.setTag(tag);
        asyncImportExportTaskService.saveOrUpdate(asyncImportExportTask);
        log.info(Thread.currentThread().getName() + "导出任务完成！！！！！！！！！！！！");
    }

    public static char[] getChars(byte[] bytes) {
        Charset cs = StandardCharsets.UTF_8;
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }


    private static void write(FileWriter fw, User row) {
        try {
            fw.write(row.getName() + "\t,\""
                    + row.getNickName() + "\"\t,\""
                    + row.getAddress() + "\"\t,\""
                    + row.getBirthday() + "\"\t,\""
                    + row.getSex() + "\"\t,\""
                    + row.getMobile() + "\"\t");
            fw.write("\r\n");
        } catch (IOException e) {
            log.error("write error:", e);
        }
    }

}
