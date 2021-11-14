package com.example.dateservice.service;

import com.example.dateservice.dto.DateRequest;
import com.example.dateservice.dto.DateResponse;
import com.example.dateservice.dto.Result;
import com.example.dateservice.exception.DateParseException;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class DateService {
    private static final Map<Integer, String> DAY_OF_WEEK = ImmutableMap.of(1, "一", 2, "二", 3, "三", 4, "四", 5, "五", 6, "六", 0, "七");

    /**
     * 计算出请求日期是一周中的第几天
     *
     * @param dateRequest 请求日期
     * @return {@link Result}<{@link DateResponse}>
     */
    public Result<DateResponse> getDayOfWeek(DateRequest dateRequest) {
        log.debug("start getDayOfWeek");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // 开启严格校验模式

        DateResponse response;
        try {
            Date date = dateFormat.parse(dateRequest.getDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            response = new DateResponse();
            response.setDayOfWeek(DAY_OF_WEEK.get(calendar.get(Calendar.DAY_OF_WEEK) - 1));
        } catch (ParseException e) {
            log.error("parse date failed. date is " + dateRequest.getDate());
            throw new DateParseException(dateRequest.getDate());
        }
        log.debug("end getDayOfWeek");
        return Result.success(response);
    }
}
