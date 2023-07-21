package com.demo.service.impl;

import com.demo.dao.HolidayDao;
import com.demo.pojo.Holiday;
import com.demo.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

// add service related annotations
@Service
public class HolidayServiceImpl implements HolidayService{

    @Autowired
    private HolidayDao holidayDao;


    @Override
    public void insertHoliday(Holiday holiday) throws IOException {
        holidayDao.insertHoliday(holiday);
    }

    @Override
    public void bulkInsertHolidays(List<Holiday> holidays) {
        holidayDao.bulkInsertHolidays(holidays);
    }

    @Override
    public void updateHoliday(Holiday holiday) throws IOException {
        holidayDao.updateHoliday(holiday);
    }

    @Override
    public void updateHolidays(List<Holiday> holidays) throws IOException {
        holidayDao.updateHolidays(holidays);
    }

    @Override
    public void removeHoliday(Holiday holiday) throws IOException {
        holidayDao.removeHoliday(holiday);
    }

    @Override
    public void removeHolidays(List<Holiday> holidays) throws IOException {
        holidayDao.removeHolidays(holidays);
    }

    @Override
    public List<Holiday> getNextYearHolidays(String countryCode, String Date) throws IOException {
       return holidayDao.getNextYearHolidays(countryCode, Date);
    }

    @Override
    public Holiday getNextHoliday(String countryCode, String Date) throws IOException {
       return holidayDao.getNextHoliday(countryCode, Date);
    }

    @Override
    public boolean isHoliday(String countryCode, String Date) throws IOException {
        return holidayDao.isHolidayExist(countryCode, Date);
    }
}
