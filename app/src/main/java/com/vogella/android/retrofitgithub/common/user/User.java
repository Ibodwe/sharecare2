package com.vogella.android.retrofitgithub.common.user;

import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String dateOfBirth;
    private String address;
    private String city;
    private String zipcode;
    private String lastLocation;
    private Role role;
    private Locale locale;
}
