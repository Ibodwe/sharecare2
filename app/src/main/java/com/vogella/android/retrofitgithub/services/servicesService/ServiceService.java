package com.vogella.android.retrofitgithub.services.servicesService;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.vogella.android.retrofitgithub.R;

//fragmentAcitivity
public class ServiceService extends AppCompatActivity implements NotificationFragment.OnFragmentInteractionListener, TaskLogFragment.OnFragmentInteractionListener, CallLogFragment.OnFragmentInteractionListener,CurrentTaskFrg.OnFragmentInteractionListener,RemainingTask.OnFragmentInteractionListener,AcceptedTask.OnFragmentInteractionListener,CreateTask.OnFragmentInteractionListener,PendingTask.OnFragmentInteractionListener {
    BottomBar bottomBar;
    private CallLogFragment callLogFragment;
    private NotificationFragment notificationFragment;
    private TaskLogFragment taskLogFragment;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_service);

        FragmentManager fragmentManager = getSupportFragmentManager();

        callLogFragment = new CallLogFragment();
        notificationFragment = new NotificationFragment();
        taskLogFragment = new TaskLogFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contentContainer, callLogFragment).commitAllowingStateLoss();




        bottomNavigationView  = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.home:{
                        transaction.replace(R.id.contentContainer, callLogFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.tasks:{
                        transaction.replace(R.id.contentContainer, taskLogFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.notification:{
                        transaction.replace(R.id.contentContainer, notificationFragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
    }


    // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
