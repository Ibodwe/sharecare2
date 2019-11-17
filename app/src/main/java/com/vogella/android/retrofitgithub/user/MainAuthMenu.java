package com.vogella.android.retrofitgithub.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.user.signin.Login;
import com.vogella.android.retrofitgithub.user.signup.SignUpSecondScreen;

public class MainAuthMenu extends AppCompatActivity {

    private Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_auth_menu);

        dataInit();

        buttonsListeners();
    }

    private void dataInit(){
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_sign_up);

    }

    public void buttonsListeners() {
        btnSignUp.setOnClickListener(v -> startActivity(new Intent(MainAuthMenu.this, SignUpSecondScreen.class)));
        btnLogin.setOnClickListener(v -> startActivity(new Intent(MainAuthMenu.this, Login.class)));
    }

}
