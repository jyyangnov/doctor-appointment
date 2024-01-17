package com.yan.daserver.service;

import com.yan.daserver.common.Result;
import com.yan.daserver.common.ResultStatus;
import com.yan.daserver.common.Role;
import com.yan.daserver.entity.Appointment;
import com.yan.daserver.entity.Doctor;
import com.yan.daserver.mapper.DoctorMapper;
import com.yan.daserver.serviceHelper.DoctorServiceHelper;
import com.yan.daserver.serviceHelper.TokenServiceHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private TokenServiceHelper tokenServiceHelper;


    //注册
    public Result signup(Doctor doctor) {
        if (this.phoneExist(doctor.getPhone())) {
            //号码已存在
            return new Result(ResultStatus.PHONE_EXIST_ERROR);
        } else {
            doctorMapper.insert(doctor);
            return new Result(ResultStatus.SUCCESS);
        }
    }


    //查看号码是否存在
    public Boolean phoneExist(String phone) {
        return doctorMapper.phoneExist(phone) != null;
    }


    //登陆
    public Result login(Doctor doctor) {
        //未注册
        if (!this.phoneExist(doctor.getPhone())) {
            return new Result<>(ResultStatus.PHONE_NOT_EXIST_ERROR);
        }
        Doctor loginDoctor = doctorMapper.getDoctorByPhoneAndPassword(doctor.getPhone(), doctor.getPassword());
        //密码错误
        if (loginDoctor == null) {
            return new Result<>(ResultStatus.PHONE_NOT_EXIST_ERROR);
        }
        //登陆成功 返回token
        String token = tokenServiceHelper.createToken();
        tokenServiceHelper.saveToken(Role.DOCTOR.toString() + loginDoctor.getId(),token);
        DoctorServiceHelper.LoginResp loginResp = new DoctorServiceHelper.LoginResp(loginDoctor.getId(), token, Role.DOCTOR.toString());
        return new Result<DoctorServiceHelper.LoginResp>(ResultStatus.SUCCESS, loginResp);
    }


    //退出登陆
    public Result logout(Integer accountId) {
        Boolean flag = tokenServiceHelper.deleteToken(Role.DOCTOR.toString() + accountId.toString());
        if (flag) {
            return new Result<>(ResultStatus.SUCCESS);
        } else {
            return new Result<>(ResultStatus.LOGOUT_ERROR);
        }
    }

    //释放号源
    public Result generateAppointment(DoctorServiceHelper.GenerateAppointmentReq data) {
        Integer doctorId = data.getDoctorId();
        List<Appointment> appointmentList = data.getAppointmentList();
        for (Appointment apt : appointmentList) {
            apt.setDoctorId(doctorId);
            apt.setStatus(0);
        }
        Integer num = doctorMapper.insertList(appointmentList);
        if (num == appointmentList.size()) {
            return new Result(ResultStatus.SUCCESS);
        } else {
            return new Result(ResultStatus.APPOINTMENT_INSERT_ERROR);
        }
    }


    public Result changeStatus(DoctorServiceHelper.ChangeStatusReq changeStatusReq) {
        Integer flag = doctorMapper.updateStatusById(changeStatusReq.getAppointmentId(), changeStatusReq.getStatus());
        if (flag == 1) {
            return new Result(ResultStatus.SUCCESS);
        } else {
            return new Result(ResultStatus.STATUS_UPDATE_ERROR);
        }
    }
}



