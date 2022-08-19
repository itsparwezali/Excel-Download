package com.example;

import java.util.ArrayList;
import java.util.List;

public class ReportUtil {

    public static ArrayList<Student> studentList = new ArrayList<>();

    static {
        studentList.add(new Student("parwez ali","brt","global"));
        studentList.add(new Student("nasim aktar","janakpur","modal"));
        studentList.add(new Student("shivam shah","nepalgunj","orchid"));
    }

    public static List<Student> getReport(){
        return studentList;
    }



}
