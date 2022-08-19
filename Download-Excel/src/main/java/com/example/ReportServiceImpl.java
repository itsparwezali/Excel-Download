package com.example;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Random;

@Service

public class ReportServiceImpl implements ReportService {
    @Override
    public ModelAndView downloadExcelReport() {
        ExcelReportResponse response = new ExcelReportResponse();
        List<Student> studentList = ReportUtil.getReport();
        String password = generatePassword();
        System.out.printf("password ========= "+password +"  ");
        response.setList(studentList);
        response.setPassword(password);
        ModelAndView modelAndView = new ModelAndView(new ReportExcel(), "studentReport", response);
        return modelAndView;
    }

    private String generatePassword(){
        return RandomStringUtils.randomAlphanumeric(5);
    }
}
