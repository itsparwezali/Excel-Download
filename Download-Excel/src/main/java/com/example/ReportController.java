package com.example;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "/test")
@AllArgsConstructor
public class ReportController {

    private ReportService reportService;

    @GetMapping("/get")
    ModelAndView getReport(){
        return reportService.downloadExcelReport();
    }
}
