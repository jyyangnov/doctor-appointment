package com.yan.daserver.common;

import lombok.Data;

@Data
public class CommonReq<T> {

    private Context context;
    private T data;

}
