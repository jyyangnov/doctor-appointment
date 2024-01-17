package com.yan.daserver.controller;

import com.yan.daserver.common.CommonReq;
import com.yan.daserver.common.Result;
import com.yan.daserver.entity.Doctor;
import com.yan.daserver.service.DoctorService;
import com.yan.daserver.serviceHelper.DoctorServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/signup")
    public Result signup(@RequestBody Doctor doctor) {
        return doctorService.signup(doctor);
    }

    @PostMapping("/login")
    public Result login(@RequestBody Doctor doctor) {
        return doctorService.login(doctor);
    }

    @PostMapping("/logout")
    public Result logout(@RequestBody CommonReq request) {
        return doctorService.logout(request.getContext().getAccountId());
    }

    @PostMapping("/generateAppointment")
    public Result generateAppointment(@RequestBody CommonReq<DoctorServiceHelper.GenerateAppointmentReq> request) {
        return doctorService.generateAppointment(request.getData());
    }

    public Result changeStatus(@RequestBody CommonReq<DoctorServiceHelper.ChangeStatusReq> request) {
        return doctorService.changeStatus(request.getData());
    }




}
