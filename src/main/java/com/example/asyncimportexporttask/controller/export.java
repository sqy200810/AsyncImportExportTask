package com.example.asyncimportexporttask.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.asyncimportexporttask.entity.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;

/**
 * @Author sqy
 * @Date 2023-10-20 11-07
 **/
public class export {
    public static void main(String[] args) {
        exprot(null,null);
    }
    public static void exprot(HttpServletRequest request, HttpServletResponse response) {
        List<User> list = new ArrayList<>();
        list.add(new User("1","2","3"));
        System.out.println(list);
        list.add(new User("2","3","4"));
        System.out.println(list);
        File excelFile = null;
        FileOutputStream fOut = null;
        try {
//            request.setCharacterEncoding("UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/x-download");

            excelFile = File.createTempFile("123", ".xlsx", new File("D:\\\\"));
            // 第一步：定义一个新的工作簿
            XSSFWorkbook wb = new XSSFWorkbook();
            // 第二步：创建一个Sheet页
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "iso8859-1") + ".xlsx");
            XSSFSheet sheet = wb.createSheet("sheet1");
            sheet.setDefaultRowHeight((short) (2 * 256));// 设置行高
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 15000);// 设置列宽
            sheet.setColumnWidth(2, 3000);

            //设置样式
            XSSFCellStyle title = wb.createCellStyle();
            title.setBorderBottom(BorderStyle.THIN);//设置边框
            title.setBorderLeft(BorderStyle.THIN);
            title.setBorderTop(BorderStyle.THIN);
            title.setBorderRight(BorderStyle.THIN);
            title.setFillPattern(FillPatternType.SOLID_FOREGROUND);    //设置填充方案
            title.setFillForegroundColor(new XSSFColor(new Color(100,149,237)));  //设置填充颜色
            title.setAlignment(HorizontalAlignment.CENTER);//设置左右居中
            title.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直居中
            //设置字体
            XSSFFont font = wb.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 16);

            //创建行
            XSSFRow row3 = sheet.createRow(0);
            //创建列
            XSSFCell cell3 = row3.createCell(0);
            cell3.setCellValue("导出excel");
            CellRangeAddress cal = new CellRangeAddress(0,0,0,2);//合并单元格
            sheet.addMergedRegion(cal);
            bord(row3,title,0);
            RegionUtil.setBorderBottom(BorderStyle.THIN, cal, sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN, cal, sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, cal, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, cal, sheet);//解决单元格合并后没有样式问题
            XSSFRow row4 = sheet.getRow(0);
            row4.setHeightInPoints(30);//设置高度

            XSSFRow row = sheet.createRow(1);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("姓名");
            cell = row.createCell(1);
            cell.setCellValue("年龄");
            cell = row.createCell(2);
            cell.setCellValue("地址");
            int i =0;
            for (int j = 0; j < list.size(); j++) {
                if(i==0){
                    i=i;
                } else{
                i=i+1;
                }
                XSSFRow row1 = sheet.createRow(j+2);
                XSSFCell cell1 = row1.createCell(0);
                cell1.setCellValue(list.get(i).getNickName());

                cell1 = row1.createCell(1);
                cell1.setCellValue(list.get(i).getName());

                cell1 = row1.createCell(2);
                cell1.setCellValue(list.get(i).getAddress());

                bord(row1,title ,2);
            }

//            OutputStream out = response.getOutputStream();
            fOut = new FileOutputStream(excelFile);
            wb.write(fOut);
            fOut.close();
            wb.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void bord(Row row, XSSFCellStyle cellStyle, int j){
        for (int i = 0;i<=j;i++) {
            Cell cell = row.getCell(i);
            cell.setCellStyle(cellStyle);
        }
}

}