package com.vogella.android.retrofitgithub.services.servicesList;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.common.BackToolbar;
import com.vogella.android.retrofitgithub.services.servicesService.ServiceService;


public class ServiceListview extends AppCompatActivity {
    private ListView listView;
    private Toolbar my_toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_listview);

        my_toolbar = (Toolbar)findViewById(R.id.my_toolbar);

        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Back");

        my_toolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_arrow_back_white_24dp));
        my_toolbar.setNavigationOnClickListener(v -> getApplicationContext().startActivity(new Intent(ServiceListview.this, ServiceListMain.class)));
        ListViewAdapter adapter = new ListViewAdapter();



        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);
        //replace code with data from database;
      //  adapter.addItem(ContextCompat.getDrawable(this,R.drawable.profile),"save","12","help save desk");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.listbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_list:
                Intent intent = new Intent(ServiceListview.this, ServiceListview.class);
                startActivity(intent);
                return true;

            case R.id.menu_map:
                Intent intent2 = new Intent(ServiceListview.this, ServiceListMain.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
