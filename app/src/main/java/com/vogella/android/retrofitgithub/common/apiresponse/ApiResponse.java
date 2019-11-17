package com.vogella.android.retrofitgithub.common.apiresponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private int status;
    private String message;
    private Object result;
}
