package com.example.student.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {
    public String FormatCurrentTime(){
        Date dd=new Date();
        //格式化
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=sim.format(dd);
        return time;
    }
}
