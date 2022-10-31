package com.bu.softwareengineering.contest.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class PersonUtil {
    public int getAge(Date date){
        LocalDate currentDate = LocalDate.now();
        Long age =  ChronoUnit.YEARS.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),currentDate);
        return age.intValue();
    }
}
