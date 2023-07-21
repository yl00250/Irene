package com.demo.dao.impl;

import com.demo.dao.HolidayDao;
import com.demo.pojo.Holiday;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class HolidayDaoImpl implements HolidayDao {

    private static final String fileName = "src/main/resources/holidayData.csv";

    public static void main(String[] args) {
        HolidayDaoImpl holidayDaoImpl = new HolidayDaoImpl();
        Holiday holiday = new Holiday();
        holiday.setCountryCode("CN");
        holiday.setCountryDesc("China");
        holiday.setHolidayDate("2021-01-01");
        holiday.setHolidayName("New Year");

        Holiday holiday1 = new Holiday();
        holiday1.setCountryCode("us");
        holiday1.setCountryDesc("United States");
        holiday1.setHolidayDate("2022-04-05");
        holiday1.setHolidayName("Good Friday");
        List<Holiday> holidays = new ArrayList<>();
holidays.add(holiday);
holidays.add(holiday1);
        System.err.println(holidays);
//        try {
//            holidayDaoImpl.bulkInsertHolidays(holidays);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void insertHoliday(Holiday holiday) throws RuntimeException, IOException {
        bulkInsertHolidays(Collections.singletonList(holiday));
    }

    @Override
    public void bulkInsertHolidays(List<Holiday> holidays) {

        File file = new File(fileName);
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        ) {

            List<Holiday> holidayList = getHolidaysFromFile(file);
            for (Holiday holiday : holidays) {
                if (!isValidHoliday(holiday)) {
                    throw new RuntimeException("Invalid holiday");
                }
                // adjust holiday is not duplicate by countryCode and holidayDate
                for (Holiday holiday1 : holidayList) {
                    if (holiday1.getCountryCode().equals(holiday.getCountryCode()) && holiday1.getHolidayDate().equals(holiday.getHolidayDate())) {
                        throw new RuntimeException("Duplicate holiday");
                    }
                }

                bufferedWriter.write(holiday.getCountryCode());
                //write to next cell in csv file
                bufferedWriter.append(",");
                bufferedWriter.append(holiday.getCountryDesc());
                bufferedWriter.append(",");
                bufferedWriter.append(holiday.getHolidayDate());
                bufferedWriter.append(",");
                bufferedWriter.write(holiday.getHolidayName());
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
            outputStreamWriter.flush();
            fileOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateHoliday(Holiday holiday) throws IOException {
        updateHolidays(Collections.singletonList(holiday));
    }

    @Override
    public void updateHolidays(List<Holiday> holidays) throws IOException {
        List<Holiday> holidayList = getHolidaysFromFile(new File(fileName));
        for (Holiday holiday : holidays) {
            if (!isValidHoliday(holiday)) {
                throw new RuntimeException("Invalid holiday");
            }
            for (Holiday holiday1 : holidayList) {
                if (holiday1.getCountryCode().equals(holiday.getCountryCode()) && holiday1.getHolidayDate().equals(holiday.getHolidayDate())) {
                    holiday1.setHolidayName(holiday.getHolidayName());
                    holiday1.setCountryDesc(holiday.getCountryDesc());
                }
            }
            //write to file
            try (
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            ) {
                for (Holiday holiday1 : holidayList) {
                    bufferedWriter.write(holiday1.getCountryCode());
                    //write to next cell in csv file
                    bufferedWriter.append(",");
                    bufferedWriter.append(holiday1.getCountryDesc());
                    bufferedWriter.append(",");
                    bufferedWriter.append(holiday1.getHolidayDate());
                    bufferedWriter.append(",");
                    bufferedWriter.write(holiday1.getHolidayName());
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                outputStreamWriter.flush();
                fileOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removeHoliday(Holiday holiday) throws IOException {
        removeHolidays(Collections.singletonList(holiday));
    }

    @Override
    public void removeHolidays(List<Holiday> holidays) throws IOException {

        List<Holiday> holidayList = getHolidaysFromFile(new File(fileName));
        List<Holiday> tmp = new ArrayList<>();
        tmp.addAll(holidayList);
        for (Holiday holiday : holidays){
            for (Holiday holiday1 : tmp)  {
                if (holiday1.getCountryCode().equals(holiday.getCountryCode()) && holiday1.getHolidayDate().equals(holiday.getHolidayDate())) {
                    holidayList.remove(holiday1);
                }
            }
        }
        //write to file
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        ) {
            for (Holiday holiday1 : holidayList) {
                bufferedWriter.write(holiday1.getCountryCode());
                //write to next cell in csv file
                bufferedWriter.append(",");
                bufferedWriter.append(holiday1.getCountryDesc());
                bufferedWriter.append(",");
                bufferedWriter.append(holiday1.getHolidayDate());
                bufferedWriter.append(",");
                bufferedWriter.write(holiday1.getHolidayName());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            outputStreamWriter.flush();
            fileOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Holiday> getNextYearHolidays(String countryCode, String Date) throws IOException {
        //get all holidays from file
        List<Holiday> holidaysFromFile = getHolidaysFromFile(new File(fileName));
        //get next year holidays
        List<Holiday> nextYearHolidays = new ArrayList<>();
        for (Holiday holiday : holidaysFromFile) {
            if (holiday.getCountryCode().equals(countryCode) && holiday.getHolidayDate().startsWith(String.valueOf(Integer.parseInt(Date.substring(0, 4)) + 1))) {
                nextYearHolidays.add(holiday);
            }
        }
        return nextYearHolidays;
    }

    @Override
    public Holiday getNextHoliday(String countryCode, String Date) throws IOException {
        //get all holidays from file
        List<Holiday> holidaysFromFile = getHolidaysFromFile(new File(fileName));
        //get next holiday
        for (Holiday holiday : holidaysFromFile) {
            if (holiday.getCountryCode().equals(countryCode) && holiday.getHolidayDate().compareTo(Date) > 0) {
                return holiday;
            }
        }
        return null;
    }

    @Override
    public boolean isHolidayExist(String countryCode, String Date) throws IOException {
        List<Holiday> holidaysFromFile = getHolidaysFromFile(new File(fileName));
        //adjust holiday exist in file
        boolean isExist = false;
        for (Holiday holiday : holidaysFromFile) {
            if (holiday.getCountryCode().equals(countryCode) && holiday.getHolidayDate().equals(Date)) {
                isExist = true;
                break;
            }
        }

        return isExist;
    }

    //add method return boolean to adjust Holiday by date and countryCode
    private boolean isValidHoliday(Holiday holiday) {
        //adjust holiday is not null
        if (holiday == null) {
            return false;
        }
        //adjust holidayName is not null
        if (holiday.getHolidayName() == null) {
            return false;
        }
        //adjust holidayDate is not null
        if (holiday.getHolidayDate() == null) {
            return false;
        }
        //adjust countryCode is not null
        if (holiday.getCountryCode() == null) {
            return false;
        }
        //adjust countryDesc is not null
        if (holiday.getCountryDesc() == null) {
            return false;
        }
        //adjust holidayDate is valid date
        if (!isValidDate(holiday.getHolidayDate())) {
            return false;
        }
        //adjust countryCode is valid country code
        if (!isValidCountryCode(holiday.getCountryCode())) {
            return false;
        }
        return true;
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isValidCountryCode(String countryCode) {
        //adjust countryCode lengthto 2 characters
        if (countryCode.length() != 2) {
            return false;
        }
        //adjust countryCode is letters
        if (!countryCode.matches("[a-zA-Z]+")) {
            return false;
        }
        return true;
    }

    private List<Holiday> getHolidaysFromFile(File file) throws IOException {
        List<Holiday> holidayList = new ArrayList<>();
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            for (String line : lines) {
                String[] lineArray = line.split(",");
                if (lineArray.length != 4) {
                    throw new RuntimeException("Invalid holiday");
                }
                Holiday holiday = new Holiday();
                holiday.setCountryCode(lineArray[0]);
                holiday.setCountryDesc(lineArray[1]);
                holiday.setHolidayDate(lineArray[2]);
                holiday.setHolidayName(lineArray[3]);
                holidayList.add(holiday);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return holidayList;
    }

}