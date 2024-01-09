package com.yan.daserver.common;

import lombok.Data;

@Data
public class Context {

    private String token;
    private Integer accountId;
    private String role;
    private String timezone;
    private Long timeStamp;

}
