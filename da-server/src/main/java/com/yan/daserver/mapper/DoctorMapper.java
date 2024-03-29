package com.yan.daserver.mapper;

import com.yan.daserver.entity.Appointment;
import com.yan.daserver.entity.Doctor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DoctorMapper {

    @Select("select * from doctor where phone = #{phone} limit 1")
    Doctor phoneExist(String phone);


    @Insert("insert into doctor(name, phone, gender,age,desc,password) value(#{name}, #{phone}, #{gender}, #{age}, #{desc}, #{password})")
    int insert(Doctor doctor);

    @Select("select * from doctor where phone = #{phone} and password = #{password} limit 1")
    Doctor getDoctorByPhoneAndPassword(String phone, String password);

    int insertList(List<Appointment> appointmentList);

    @Update("update appointment set status=#{status} where appointment_id=#{appointmentId}")
    int updateStatusById(Integer appointmentId, Integer status);
}
