package com.yan.daserver.serviceHelper;

import com.yan.daserver.entity.Appointment;
import lombok.Data;

import java.util.List;

public class DoctorServiceHelper {

    @Data
    public static class LoginResp {
        public Integer accountId;
        public String token;
        public String role;

        public LoginResp(Integer accountId, String token, String role) {
            this.accountId = accountId;
            this.token = token;
            this.role = role;
        }
    }

    @Data
    public static class GenerateAppointmentReq{
        private Integer doctorId;
        private List<Appointment> appointmentList;
    }




}
