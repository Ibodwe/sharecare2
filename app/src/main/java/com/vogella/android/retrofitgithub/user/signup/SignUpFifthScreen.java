package com.vogella.android.retrofitgithub.user.signup;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.user.signin.Login;

public class SignUpFifthScreen extends AppCompatActivity {

    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_fifth_screen);

        signIn = findViewById(R.id.signIn);

        signIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpFifthScreen.this, Login.class);
            startActivity(intent);
        });
    }
}
