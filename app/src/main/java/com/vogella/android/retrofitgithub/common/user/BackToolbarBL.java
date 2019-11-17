package com.vogella.android.retrofitgithub.common.user;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vogella.android.retrofitgithub.R;

public class BackToolbarBL {

    public BackToolbarBL(AppCompatActivity activity, AppCompatActivity destinationActivity) {
        Toolbar myToolbar = activity.findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Back");
        activity.setSupportActionBar(myToolbar);

        myToolbar.setNavigationIcon(ContextCompat.getDrawable(activity, R.drawable.ic_arrow_back_black_24dp));
        myToolbar.setNavigationOnClickListener(v -> activity.startActivity(new Intent(activity, destinationActivity.getClass())));
    }
}
