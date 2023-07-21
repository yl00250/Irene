package com.demo.dao;

import com.demo.pojo.Holiday;

import java.io.IOException;
import java.util.List;

public interface HolidayDao {

    // add the following methods
     public void insertHoliday(Holiday holiday) throws IOException;

     public void bulkInsertHolidays(List<Holiday> holidays);

     public void updateHoliday(Holiday holiday) throws IOException;

     public void updateHolidays(List<Holiday> holidays) throws IOException;

     public void removeHoliday(Holiday holiday) throws IOException;

     public void removeHolidays(List<Holiday> holidays) throws IOException;

     public List<Holiday> getNextYearHolidays(String countryCode, String Date) throws IOException;

     public Holiday getNextHoliday(String countryCode, String Date) throws IOException;

     public boolean isHolidayExist(String countryCode, String Date) throws IOException;

}
