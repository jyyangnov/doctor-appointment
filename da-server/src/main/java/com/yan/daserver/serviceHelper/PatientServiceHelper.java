package com.yan.daserver.serviceHelper;

import lombok.Data;

public class PatientServiceHelper {

    @Data
    public static class LogoutReqData {
        private Integer patientId;
    }

}
