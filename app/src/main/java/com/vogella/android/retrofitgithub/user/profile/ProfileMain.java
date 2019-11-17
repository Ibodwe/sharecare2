package com.vogella.android.retrofitgithub.user.profile;

import android.annotation.TargetApi;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.common.user.BackToolbarBL;
import com.vogella.android.retrofitgithub.services.servicesService.ServiceService;
import com.vogella.android.retrofitgithub.common.BackToolbar;

//class should start with capital letter
public class ProfileMain extends AppCompatActivity {

    //fields in class should be private
    private ImageView profile_picture;
    private com.vogella.android.retrofitgithub.common.user.BackToolbarBL BackToolbarBL;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        BackToolbarBL = new BackToolbarBL(ProfileMain.this, new ServiceService());
        //its ok, because its small class, but it can be also in function data init
      //  backToolbar = new BackToolbar(ProfileMain.this, new ServiceService());
        profile_picture = findViewById(R.id.profile_p);
        GradientDrawable drawable = (GradientDrawable) this.getDrawable(R.drawable.profile_rounded);

        profile_picture.setBackground(drawable);
        profile_picture.setClipToOutline(true);

    }
}
