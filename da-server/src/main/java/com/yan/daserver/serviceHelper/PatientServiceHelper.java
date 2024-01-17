package com.yan.daserver.serviceHelper;

import lombok.Data;

public class PatientServiceHelper {

    @Data
    public static class LoginResp {
        private Integer accountId;
        private String token;
        private String role;

        public LoginResp(Integer accountId, String token, String role) {
            this.accountId = accountId;
            this.token = token;
            this.role = role;
        }
    }

    @Data
    public static class MakeAppointmentReq {
        private Integer patientId;
        private Integer appointmentId;
    }

}
