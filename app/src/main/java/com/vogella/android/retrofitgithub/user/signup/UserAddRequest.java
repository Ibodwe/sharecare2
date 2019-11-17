package com.vogella.android.retrofitgithub.user.signup;

import com.vogella.android.retrofitgithub.common.user.Role;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAddRequest implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateOfBirth;
    private String phoneNumber;
    private String address;
    private String city;
    private String zipcode;
    private Role role;
}
