package com.yan.daserver.service;

import com.yan.daserver.common.Result;
import com.yan.daserver.common.ResultStatus;
import com.yan.daserver.common.Role;
import com.yan.daserver.entity.Patient;
import com.yan.daserver.mapper.PatientMapper;
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

    public Boolean phoneExist(String phone) {
        return patientMapper.phoneExist(phone) != null;
    }

    public Result signup(Patient patient) {
        if(this.phoneExist(patient.getPhone())) {
            //号码已存在
            return new Result(ResultStatus.PHONE_EXIST_ERROR);
        } else {
            patientMapper.insert(patient);
            return new Result(ResultStatus.SUCCESS);
        }
    }

    public Result<Object> login(Patient patient) {
        if (!this.phoneExist(patient.getPhone())) {
            //未注册
            return new Result<>(ResultStatus.PHONE_NOT_EXIST_ERROR);
        }
        Patient loginPatient = patientMapper.getPatientByPhoneAndPassword(patient.getPhone(), patient.getPassword());
        if (loginPatient == null) {
            //密码错误
            return new Result<>(ResultStatus.PHONE_NOT_EXIST_ERROR);
        }
        //登陆成功，返回token
        String token = tokenServiceHelper.createToken();
        tokenServiceHelper.saveToken(Role.PATIENT.toString() + loginPatient.getId(), token);
        LoginData loginData = new LoginData(loginPatient.getId(), token, Role.PATIENT.toString());
        return new Result<Object>(ResultStatus.SUCCESS, loginData);
    }

    public Result<Object> logout(Integer accountId) {
        Boolean flag = tokenServiceHelper.deleteToken(Role.PATIENT.toString() + accountId.toString());
        if (flag) {
            return new Result<>(ResultStatus.SUCCESS);
        } else {
            return new Result<>(ResultStatus.LOGOUT_ERROR);
        }
    }

    @Data
    public static class LoginData {
        public Integer accountId;
        public String token;
        public String role;

        public LoginData(Integer accountId, String token, String role) {
            this.accountId = accountId;
            this.token = token;
            this.role = role;
        }
    }
}
