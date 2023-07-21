package com.demo.controller;

//add controller related annotations

import com.demo.pojo.Holiday;
import com.demo.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class HolidayController {

    @Autowired
    private HolidayService holidayService;


    // add restAPI for  all the methods from HolidayService
    @GetMapping("/insertHoliday")
    public void insertHoliday(@RequestBody Holiday holiday){
        try {
            holidayService.insertHoliday(holiday);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // add bulkinsertHoliday
    @RequestMapping("/insertHolidays")
    public void bulkInsertHolidays(@RequestBody List<Holiday> holidays) {
        holidayService.bulkInsertHolidays(holidays);
    }

    // add updateHoliday
    @RequestMapping("/updateHoliday")
    public void updateHoliday(@RequestBody Holiday holiday) {
        try {
            holidayService.updateHoliday(holiday);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // add updateHolidays
    @RequestMapping("/updateHolidays")
    public void updateHolidays(@RequestBody List<Holiday> holidays) {
        try {
            holidayService.updateHolidays(holidays);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // add removeHoliday
    @GetMapping("/removeHoliday")
    public void removeHoliday(@RequestBody Holiday holiday) {
        try {
            holidayService.removeHoliday(holiday);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // add removeHolidays
    @GetMapping("/removeHolidays")
    public void removeHolidays(@RequestBody List<Holiday> holidays) {
        try {
            holidayService.removeHolidays(holidays);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // add getNextYearHolidays
    @RequestMapping("/getNextYearHolidays")
    @ResponseBody
    public List<Holiday> getNextYearHolidays(@RequestParam(value = "countryCode") String countryCode, @RequestParam(value = "Date") String Date) {
        try {
            return holidayService.getNextYearHolidays(countryCode, Date);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // add getNextHoliday
    @RequestMapping("/getNextHoliday")
    @ResponseBody
    public Holiday getNextHoliday(@RequestParam(value = "countryCode") String countryCode, @RequestParam(value = "Date") String Date) {
        try {
            return holidayService.getNextHoliday(countryCode, Date);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // add isholiday
    @RequestMapping("/isHoliday")
    @ResponseBody
    public boolean isHoliday(@RequestParam(value = "countryCode") String countryCode, @RequestParam(value = "Date") String Date) {
        try {
            return holidayService.isHoliday(countryCode, Date);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
