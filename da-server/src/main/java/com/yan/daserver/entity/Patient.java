package com.yan.daserver.entity;

import lombok.Data;

@Data
public class Patient {
    private Integer id;
    private String name;
    private String phone;
    private Integer gender;
    private Integer age;
    private String password;
}
