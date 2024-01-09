package com.yan.daserver.mapper;

import com.yan.daserver.entity.Patient;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PatientMapper {

    @Select("select * from patient where phone = #{phone} limit 1")
    Patient phoneExist(String phone);




    @Select("select * from patient")
    List<Patient> findAll();

    @Insert("insert into patient(name, phone, gender, age, password) value(#{name}, #{phone}, #{gender}, #{age}, #{password})")
    int insert(Patient patient);

    int update(Patient patient);

    @Select("select * from patient limit #{pageNum}, #{pageSize}")
    List<Patient> selectPage(Integer pageNum, Integer pageSize);

    @Select("select count(*) from patient")
    Integer selectTotal();

    @Select("select * from patient where phone = #{phone} and password = #{password} limit 1")
    Patient getPatientByPhoneAndPassword(String phone, String password);
}
