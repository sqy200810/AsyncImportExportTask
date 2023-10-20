package com.example.asyncimportexporttask;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.asyncimportexporttask.mapper")
public class AsyncImportExportTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncImportExportTaskApplication.class, args);
    }

}
