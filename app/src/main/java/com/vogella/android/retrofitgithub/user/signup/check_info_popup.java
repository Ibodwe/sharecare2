package com.vogella.android.retrofitgithub.user.signup;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.vogella.android.retrofitgithub.R;

public class check_info_popup extends FragmentActivity {

    Button next;
    protected void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info_popup);


        next = findViewById(R.id.next);

        next.setOnClickListener(v -> finish());

    }
}
