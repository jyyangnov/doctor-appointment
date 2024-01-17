package com.yan.daserver.controller;


import com.yan.daserver.common.CommonReq;
import com.yan.daserver.common.Result;
import com.yan.daserver.mapper.PatientMapper;
import com.yan.daserver.entity.Patient;
import com.yan.daserver.service.PatientService;
import com.yan.daserver.serviceHelper.PatientServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yan
 * @since 2023-12-14
 */
@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PatientService patientService;

    //注册
    @PostMapping("/signup")
    public Result signup(@RequestBody Patient patient) {
        return patientService.signup(patient);
    }

    //登陆
    @PostMapping("/login")
    public Result login(@RequestBody Patient patient) {
        return patientService.login(patient);
    }

    //登出
    @PostMapping("/logout")
    public Result<Object> logout(@RequestBody CommonReq request) {
        return patientService.logout(request.getContext().getAccountId());
    }

    //挂号
    @PostMapping("/makeAppointment")
    public Result makeAppointment(@RequestBody CommonReq<PatientServiceHelper.MakeAppointmentReq> request) {
        return patientService.makeAppointment(request.getData());
    }

    //@PostMapping("/makeAppointment")
    //public Result<Object> makeAppointment(@RequestBody CommonReq request) {
    //
    //}





    //@PostMapping("/save")
    //public Integer save(@RequestBody Patient patient) {
    //    return patientService.save(patient);
    //}

    //@PostMapping("page")
    //public Integer findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
    //    pageNum = (pageNum - 1) * pageSize;
    //    return patientMapper.selectTotal();
    //    //return patientMapper.selectPage(pageNum, pageSize);
    //}

    //@GetMapping("/signup")
    //public String signup(@RequestBody Patient patient) {
    //
    //    return patient.toString();
    //}



}
