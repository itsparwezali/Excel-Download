package com.example;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReportExcel extends AbstractXlsxStreamingView {

    public static final String MMM_dd_yyyy = "MMM dd, yyyy";
    public static final String MMM_dd_yyyy_HH_MM_SS = "MMM dd, yyyy HH:mm:ss";



    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + Constant.STUDENT_REPORT+ Constant.XLXS);

        ExcelReportResponse reportResponse = (ExcelReportResponse) model.get("studentReport");
        @SuppressWarnings("unchecked")
        File file = File.createTempFile(Constant.STUDENT_REPORT, Constant.XLXS);
        FileOutputStream fos = new FileOutputStream(file);
        populateWorkBook(workbook, reportResponse.getList());
        workbook.write(fos);
        fos.close();
        FileEncryptionUtil.writeFileSystem(httpServletResponse, file, reportResponse.getPassword());
    }

    private void populateWorkBook(Workbook workbook, List<Student> studentList) {



        try {

            Sheet sheet = workbook.createSheet(Constant.STUDENT_REPORT);
            sheet.setDefaultColumnWidth(5);

            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(HSSFColor.BLACK.index);
            style.setFont(font);

            // Create Cell Style for formatting Date
            CellStyle dateCellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(MMM_dd_yyyy));

            CellStyle studentReportCellStyle = workbook.createCellStyle();
            CreationHelper studentReportCreationHelper = workbook.getCreationHelper();
            studentReportCellStyle.setDataFormat(studentReportCreationHelper.createDataFormat().getFormat(MMM_dd_yyyy_HH_MM_SS));

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue(("Report Generated on : " + new Date()) );
//            row.getCell(0).setCellType(CellType.STRING);

            // create row header

            Row header = sheet.createRow(1);
            header.createCell(0).setCellValue("Student Name");
            header.getCell(0).setCellStyle(style);

            header.createCell(1).setCellValue("Address");
            header.getCell(1).setCellStyle(style);

            header.createCell(2).setCellValue("Collage");
            header.getCell(2).setCellStyle(style);

            // set list data

            int rowCount = 2;

            for (Student student : studentList){

                Row reportRow = sheet.createRow(rowCount++);

                reportRow.createCell(0).setCellValue(student.getStudentName());
//                reportRow.getCell(0).setCellType(CellType.STRING);

                reportRow.createCell(1).setCellValue(student.getAddress());
//                reportRow.getCell(1).setCellType(CellType.STRING);

                reportRow.createCell(2).setCellValue(student.getCollage());
//                reportRow.getCell(2).setCellType(CellType.STRING);
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
