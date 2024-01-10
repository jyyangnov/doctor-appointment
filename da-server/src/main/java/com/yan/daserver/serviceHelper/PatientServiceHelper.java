package com.yan.daserver.serviceHelper;

import lombok.Data;

public class PatientServiceHelper {

    @Data
    public static class LogoutReqData {
        private Integer patientId;
    }

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

}
