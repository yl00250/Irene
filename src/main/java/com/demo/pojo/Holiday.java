package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Holiday {
    // add private String countryCode, String holidayName, String holidayDate, String countryDesc
    private String countryCode;
    private String holidayName;
    private String holidayDate;
    private String countryDesc;


}
