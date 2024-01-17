package com.yan.daserver.service;

import com.yan.daserver.common.Result;
import com.yan.daserver.common.ResultStatus;
import com.yan.daserver.common.Role;
import com.yan.daserver.entity.Patient;
import com.yan.daserver.mapper.PatientMapper;
import com.yan.daserver.serviceHelper.PatientServiceHelper;
import com.yan.daserver.serviceHelper.TokenServiceHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private TokenServiceHelper tokenServiceHelper;


    //查看号码是否已存在
    public Boolean phoneExist(String phone) {
        return patientMapper.phoneExist(phone) != null;
    }


    //注册
    public Result signup(Patient patient) {
        if(this.phoneExist(patient.getPhone())) {
            //号码已存在
            return new Result(ResultStatus.PHONE_EXIST_ERROR);
        } else {
            patientMapper.insert(patient);
            return new Result(ResultStatus.SUCCESS);
        }
    }


    //登陆
    public Result login(Patient patient) {
        //未注册
        if (!this.phoneExist(patient.getPhone())) {
            return new Result<>(ResultStatus.PHONE_NOT_EXIST_ERROR);
        }
        Patient loginPatient = patientMapper.getPatientByPhoneAndPassword(patient.getPhone(), patient.getPassword());
        //密码错误
        if (loginPatient == null) {
            return new Result<>(ResultStatus.PHONE_NOT_EXIST_ERROR);
        }
        //登陆成功，返回token
        String token = tokenServiceHelper.createToken();
        tokenServiceHelper.saveToken(Role.PATIENT.toString() + loginPatient.getId(), token);
        PatientServiceHelper.LoginResp loginResp = new PatientServiceHelper.LoginResp(loginPatient.getId(), token, Role.PATIENT.toString());
        return new Result<PatientServiceHelper.LoginResp>(ResultStatus.SUCCESS, loginResp);
    }


    //退出登陆
    public Result<Object> logout(Integer accountId) {
        Boolean flag = tokenServiceHelper.deleteToken(Role.PATIENT.toString() + accountId.toString());
        if (flag) {
            return new Result<>(ResultStatus.SUCCESS);
        } else {
            return new Result<>(ResultStatus.LOGOUT_ERROR);
        }
    }

    //预约挂号
    public Result makeAppointment(PatientServiceHelper.MakeAppointmentReq makeAppointmentReq) {
        Integer flag = patientMapper.updateAppointmentById(makeAppointmentReq.getPatientId(), makeAppointmentReq.getAppointmentId());
        if (flag == 1) {
            return new Result(ResultStatus.SUCCESS);
        } else {
            return new Result(ResultStatus.APPOINTMENT_UPDATE_ERROR);
        }
    }
}
