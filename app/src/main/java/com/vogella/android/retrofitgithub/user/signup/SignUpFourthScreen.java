package com.vogella.android.retrofitgithub.user.signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.common.user.User;
import com.vogella.android.retrofitgithub.user.MainAuthMenu;
import com.vogella.android.retrofitgithub.user.UserApiService;
import com.vogella.android.retrofitgithub.user.UserControllerAPI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SignUpFourthScreen extends AppCompatActivity {

    private Button personal_info, account_info, finish;
    private UserAddRequest userAddRequest;
    private UserControllerAPI userControllerAPI;
    private UserApiService userApiService;
    private CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_forth_screen);

        userAddRequest = (UserAddRequest) getIntent().getSerializableExtra("userAddRequest");

        dataInit();

        personal_info.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpFourthScreen.this, SignUpSecondScreen.class);
            startActivity(intent);
        });

        account_info.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpFourthScreen.this, SignUpThirdScreen.class);
            startActivity(intent);
        });

        finish.setOnClickListener(view -> {
            sendRequest(userAddRequest);
        });



//        personal_info.setOnClickListener(v -> {
//            /*Context mContext = getApplicationContext();
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//
//            View layout = inflater.inflate(R.layout.activity_check_info_popup, (ViewGroup) findViewById(R.id.info),false);
//            AlertDialog.Builder aDialog = new AlertDialog.Builder(SignUpFourthScreen.this);
//
//            aDialog.setTitle("Check infomation");
//            aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅
//
//            aDialog.setNegativeButton("check", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                }
//            });
//
//            AlertDialog ad = aDialog.create();
//            ad.show();//보여줌!*/
//
//            /*Intent intent = new Intent(SignUpFourthScreen.this,check_info_popup.class);
//            startActivity(intent);*/
//        });

    }

    private void dataInit(){
        final View drawerView = findViewById(R.id.checkinfo);

        personal_info = findViewById(R.id.personal_info);
        account_info = findViewById(R.id.account_info);
        finish = findViewById(R.id.finish);
        userApiService = new UserApiService();
        compositeDisposable = new CompositeDisposable();
        userControllerAPI = userApiService.connectWithSignUpApi();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return event.getAction() != MotionEvent.ACTION_OUTSIDE;
    }

    private void sendRequest(UserAddRequest userAddRequest) {
        compositeDisposable.add(userControllerAPI.addUser(userAddRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getRepositoriesObserver()));
    }


    private DisposableSingleObserver<User> getRepositoriesObserver() {
        return new DisposableSingleObserver<User>() {

            @Override
            public void onSuccess(User value) {
                if (value != null) {
                    Log.i("SignUpFourthScreen", "Everything was fine!");
                    Toast.makeText(SignUpFourthScreen.this, "Everything was fine!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpFourthScreen.this, SignUpFifthScreen.class);
                    intent.putExtra("userAddRequest", userAddRequest);
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("SignUpFourthScreen", "something wrong on server");
                    Toast.makeText(SignUpFourthScreen.this, "Error!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("SignUpFourthScreen", "Error in connecting with server");
                e.printStackTrace();
                Toast.makeText(SignUpFourthScreen.this, "There was an error! Try again later.", Toast.LENGTH_LONG).show();
            }
        };
    }
}
