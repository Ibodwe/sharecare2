package com.vogella.android.retrofitgithub.services.servicesService;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.common.BackToolbar;
import com.vogella.android.retrofitgithub.common.OwnValidator;
import com.vogella.android.retrofitgithub.services.ServiceControllerAPI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ServiceAddActivity extends AppCompatActivity {

    private Button next, currentLocation;
    private EditText title, description, cost, address, zipcode, duration, city;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ServiceControllerAPI serviceControllerAPI;
    private ProgressBar progressBar;
    private OwnValidator ownValidator;
    private BackToolbar backToolbar;
    private ServiceAddRequest serviceAddRequest;
    private ServiceApiService serviceApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_add);

        dataInit();


        backToolbar = new BackToolbar(ServiceAddActivity.this, new ServiceService());
        next.setOnClickListener(v -> {
            if (!areFieldsValid())
                return;

            serviceAddRequest = ServiceAddRequest.builder()
                    .title(title.getText().toString())
                    .address(address.getText().toString())
                    .cost(Integer.valueOf(cost.getText().toString()))
                    .city(city.getText().toString())
                    .duration(Integer.valueOf(duration.getText().toString()))
                    .requestedBy(1)
                    .zipcode(zipcode.getText().toString())
                    .description(description.getText().toString())
                    .build();

            sendRequest(serviceAddRequest);
        });
    }

    public void dataInit() {
        serviceApiService = new ServiceApiService();
        serviceControllerAPI = serviceApiService.connectWithAddServiceApi();
        ownValidator = new OwnValidator();
        next = findViewById(R.id.next);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        cost = findViewById(R.id.cost);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        zipcode = findViewById(R.id.zipcode);
        duration = findViewById(R.id.duration);


    }

    private boolean areFieldsValid() {
        boolean isTitleValid = ownValidator.isFieldNotEmpty(ServiceAddActivity.this, title);
        boolean isDescriptionValid = ownValidator.isFieldNotEmpty(ServiceAddActivity.this, description);
        boolean isAddressValid = ownValidator.isFieldNotEmpty(ServiceAddActivity.this, address);
        boolean isPostcodeValid = ownValidator.isFieldNotEmpty(ServiceAddActivity.this, zipcode);

        return isTitleValid && isDescriptionValid && isAddressValid && isPostcodeValid;
    }


    private void sendRequest(ServiceAddRequest request) {
        compositeDisposable.add(serviceControllerAPI.addRequest(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getRepositoriesObserver()));
    }

    private DisposableSingleObserver<Service> getRepositoriesObserver() {
        return new DisposableSingleObserver<Service>() {
            @Override
            public void onSuccess(Service value) {
                Log.i("taskAdded", "Added task was a success");
                Toast.makeText(getApplicationContext(), "Task was added!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceAddActivity.this, ServiceService.class);
                startActivity(intent);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("taskAdded", "Error with adding task");
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "There was an error with repository!", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
