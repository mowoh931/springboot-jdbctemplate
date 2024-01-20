package com.baar.springbootjdbctemplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String ipAddress;


}
