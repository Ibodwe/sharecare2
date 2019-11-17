package com.vogella.android.retrofitgithub.user.signin;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;
import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.common.BackToolbar;
import com.vogella.android.retrofitgithub.common.OwnValidator;
import com.vogella.android.retrofitgithub.common.SaveSharedPreferences;
import com.vogella.android.retrofitgithub.common.apiresponse.ApiResponse;
import com.vogella.android.retrofitgithub.common.user.BackToolbarBL;
import com.vogella.android.retrofitgithub.common.user.User;
import com.vogella.android.retrofitgithub.common.user.UserDeserializer;
import com.vogella.android.retrofitgithub.services.servicesService.ServiceService;
import com.vogella.android.retrofitgithub.user.MainAuthMenu;
import com.vogella.android.retrofitgithub.user.UserApiService;
import com.vogella.android.retrofitgithub.user.UserControllerAPI;
import com.vogella.android.retrofitgithub.user.signup.SignUpSecondScreen;
import com.vogella.android.retrofitgithub.user.signup.Verification;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Login extends AppCompatActivity {

    private EditText inputEmailText, inputPasswordText;
    private ProgressBar progressBar;
    private Button btnLogin;
    private UserControllerAPI userControllerAPI;
    private UserApiService userApiService;
    private CompositeDisposable compositeDisposable;
    private OwnValidator ownValidator;
    private com.vogella.android.retrofitgithub.common.user.BackToolbarBL BackToolbarBL;
    private TextView register;
    private LoginPayloadRequest loginPayloadRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // PushUtils.acquireWakeLock(this);
        //App 어쩌구가 말하는 것은 new activity를 말함.

        BackToolbarBL = new BackToolbarBL(Login.this, new MainAuthMenu());

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    String token = task.getResult().getToken();
                    String msg = getString(R.string.fcm_token, token);
                    //   Log.d(TAG, msg);

                });

        dataInit();

        buttonsListeners();
    }

    public void dataInit() {
        ownValidator = new OwnValidator();
        userApiService = new UserApiService();
        userControllerAPI = userApiService.connectWithLoginApi();
        compositeDisposable = new CompositeDisposable();
        inputEmailText = findViewById(R.id.email);
        inputPasswordText = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.btn_login);
        register = findViewById(R.id.register);
    }

    public void buttonsListeners() {
        btnLogin.setOnClickListener(view -> {
            //if (!areFieldsValid()) return;

            loginPayloadRequest = LoginPayloadRequest.builder()
                    .email("email@email.com")
                    .password("Passw0rd")
                    .build();

            sendRequest(loginPayloadRequest);

            Intent intent = new Intent(Login.this, Verification.class);
            startActivity(intent);
        });

        register.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUpSecondScreen.class);
            startActivity(intent);
        });
    }


    private boolean areFieldsValid() {
        boolean isEmailValid = ownValidator.validEmail(Login.this, inputEmailText);
        boolean isPasswordValid = ownValidator.validPassword(Login.this, inputPasswordText);

        return isPasswordValid && isEmailValid;
    }

    private void sendRequest(LoginPayloadRequest loginPayloadRequest) {
        compositeDisposable.add(userControllerAPI.login(loginPayloadRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getRepositoriesObserver()));
    }

    private DisposableSingleObserver<ApiResponse> getRepositoriesObserver() {
        return new DisposableSingleObserver<ApiResponse>() {
            @Override
            public void onSuccess(ApiResponse value) {
                if (value != null) {
                    Log.i("loginSuccessful", "Login was successful!");
                    UserDeserializer userDeserializer = new UserDeserializer();
                    User user = userDeserializer.deserializeUser((JsonObject) value.getResult());
                    saveInSharedPreferences(user.getEmail(), user.getFirstName(), user.getLastName());
                    goToMenu();
                } else {
                    Log.i("loginUnsuccessful", "Login was NOT successful!");
                    Toast.makeText(Login.this, "There was an error in login! Try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.i("loginUnsuccessful", "There was an error with login!");
                e.printStackTrace();
                Toast.makeText(Login.this, "We couldnt log you. Try again!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void saveInSharedPreferences(String userEmail, String firstName, String lastName) {
        SaveSharedPreferences.setEmail(Login.this, userEmail);
        SaveSharedPreferences.setFirstname(Login.this, firstName);
        SaveSharedPreferences.setLastname(Login.this, lastName);
    }

    private void goToMenu() {
        startActivity(new Intent(Login.this, Verification.class));
        finish();
    }
}
