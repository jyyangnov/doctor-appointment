package com.yan.daserver.entity;

import lombok.Data;

@Data
public class Doctor {
    private Integer id;
    private String name;
    private String phone;
    private Integer gender;
    private Integer age;
    private String desc;
    private Integer department;
    private String password;
}
