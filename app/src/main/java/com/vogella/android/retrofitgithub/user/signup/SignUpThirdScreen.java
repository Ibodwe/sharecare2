package com.vogella.android.retrofitgithub.user.signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.common.BackToolbar;
import com.vogella.android.retrofitgithub.common.OwnValidator;
import com.vogella.android.retrofitgithub.common.user.Role;
import com.vogella.android.retrofitgithub.user.MainAuthMenu;
import com.vogella.android.retrofitgithub.user.UserControllerAPI;
import com.vogella.android.retrofitgithub.user.UserApiService;
import com.vogella.android.retrofitgithub.common.user.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SignUpThirdScreen extends AppCompatActivity {

    private Button  next;
    private UserControllerAPI userControllerAPI;
    private UserApiService userApiService;
    private CompositeDisposable compositeDisposable;
    private ProgressBar progressBar;
    private BackToolbar backToolbar;
    private OwnValidator ownValidator;
    private UserAddRequest userAddRequest;
    private EditText email_address,email_address_repeat,password,password_repeat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_third_screen);

        userAddRequest = (UserAddRequest) getIntent().getSerializableExtra("userAddRequest");

        backToolbar = new BackToolbar(SignUpThirdScreen.this,new SignUpSecondScreen());

        dataInit();

        next.setOnClickListener(v -> {
            //if(!areFieldsValid()) return;

            userAddRequest.setPassword("Passw0rd");
            userAddRequest.setEmail("email@email.com");
            userAddRequest.setRole(Role.ELDERLY);

            Intent intent = new Intent(SignUpThirdScreen.this, SignUpFourthScreen.class);
            intent.putExtra("userAddRequest", userAddRequest);
            startActivity(intent);
        });

    }

    public void dataInit() {
        next = findViewById(R.id.next);
        email_address = findViewById(R.id.email_address);
        email_address_repeat = findViewById(R.id.email_address_repeat);
        password = findViewById(R.id.password);
        password_repeat=findViewById(R.id.password_repeat);
        progressBar = findViewById(R.id.progressBar);
        ownValidator = new OwnValidator();
        userApiService = new UserApiService();
        compositeDisposable = new CompositeDisposable();
        userControllerAPI = userApiService.connectWithSignUpApi();
    }

    private boolean areFieldsValid() {
        boolean isEmailValid = ownValidator.validEmail(SignUpThirdScreen.this, email_address);
        boolean isPasswordValid = ownValidator.validPassword(SignUpThirdScreen.this, password);

        return isPasswordValid && isEmailValid;
    }

}
