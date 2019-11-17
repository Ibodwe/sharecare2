package com.vogella.android.retrofitgithub.user.signin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginPayloadRequest {
    private String email;
    private String password;
}
