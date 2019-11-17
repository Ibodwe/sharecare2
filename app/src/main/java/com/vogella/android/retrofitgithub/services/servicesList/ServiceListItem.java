package com.vogella.android.retrofitgithub.services.servicesList;

import android.graphics.drawable.Drawable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//class with capital letter
public class ServiceListItem {
    private String task, duration, token, caretaker;

    public ServiceListItem(String task, String duration, String token, String caretaker) {
        this.task = task;
        this.duration = duration;
        this.token = token;
        this.caretaker = caretaker;
    }



}
