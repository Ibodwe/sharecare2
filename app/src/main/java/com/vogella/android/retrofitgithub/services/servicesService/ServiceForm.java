package com.vogella.android.retrofitgithub.services.servicesService;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.services.ServiceControllerAPI;
import com.vogella.android.retrofitgithub.common.BackToolbar;
import com.vogella.android.retrofitgithub.common.OwnValidator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ServiceForm extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private Button okay, currentLocation;
    private EditText title_view, description_view, hour_view, location_address, location_postcode, preference_view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ServiceControllerAPI serviceControllerAPI;
    private ProgressBar progressBar;
    private OwnValidator ownValidator;
    private BackToolbar backToolbar;
    private ServiceAddRequest serviceAddRequest;
    TextView address_view;
    private ServiceApiService serviceApiService;
    public static String SERVER_ADRESS = "http://192.168.1.101:8080";

    //기본 activity에 내장되어 있는 함수다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(getApplicationContext(), "Permission rejected, please check the permission", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(getApplicationContext(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(ServiceForm.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(getApplicationContext(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(ServiceForm.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(ServiceForm.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }
    }

    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "network problem", Toast.LENGTH_LONG).show();
            return "network problem";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "wrong gps location", Toast.LENGTH_LONG).show();
            return "wrong gps location";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "unfind address", Toast.LENGTH_LONG).show();
            return "unfind address";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }

    /*
        private void showDialogForLocationServiceSetting() {

            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
          //  builder.setTitle("위치 서비스 비활성화");
         //  builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
         //           + "위치 설정을 수정하실래요?");
            builder.setCancelable(true);
            builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Intent callGPSSettingIntent
                            = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.create().show();
        }
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS ON");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reqeustform);

        dataInit();

        currentLocation.setOnClickListener(v -> {
            gpsTracker = new GpsTracker(ServiceForm.this);

            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            String address = getCurrentAddress(latitude, longitude);

            address_view.setText(address);
            Toast.makeText(getApplicationContext(), "current location \nlatitude " + latitude + "\nlongitude " + longitude, Toast.LENGTH_LONG).show();
        });


        okay.setOnClickListener(v -> {
            if (!areFieldsValid())
                return;

//            serviceAddRequest = ServiceAddRequest.builder()
//                    .title(title_view.getText().toString())
//                    .address(location_address.getText().toString())
//                    .zipcode(location_postcode.getText().toString())
//                    .description(description_view.getText().toString())
//                    .build();

            serviceAddRequest = ServiceAddRequest.builder()
                    .title("title")
                    .address("address")
                    .cost(100)
                    .city("city")
                    .duration(100)
                    .requestedBy(1)
                    .zipcode("zipcode")
                    .description("description")
                    .build();

            sendRequest(serviceAddRequest);
        });

    }

    public void dataInit() {
        serviceApiService = new ServiceApiService();
        serviceControllerAPI = serviceApiService.connectWithAddServiceApi();
        // backToolbar = new BackToolbar(ServiceForm.this, new ServiceService());
        ownValidator = new OwnValidator();
        okay = findViewById(R.id.okay);
        title_view = findViewById(R.id.title_view);
        description_view = findViewById(R.id.description_view);
        hour_view = findViewById(R.id.hour_view);
        location_address = findViewById(R.id.location_address);
        location_postcode = findViewById(R.id.location_postcode);
        preference_view = findViewById(R.id.preference_view);
        currentLocation = findViewById(R.id.currentLocation);
        address_view = findViewById(R.id.address);

    }


    private boolean areFieldsValid() {
        boolean isTitleValid = ownValidator.isFieldNotEmpty(ServiceForm.this, title_view);
        boolean isDescriptionValid = ownValidator.isFieldNotEmpty(ServiceForm.this, description_view);
        boolean isHourValid = ownValidator.isFieldNotEmpty(ServiceForm.this, hour_view);
        boolean isAddressValid = ownValidator.isFieldNotEmpty(ServiceForm.this, location_address);
        boolean isPostcodeValid = ownValidator.isFieldNotEmpty(ServiceForm.this, location_postcode);

        return isTitleValid && isDescriptionValid && isHourValid && isAddressValid && isPostcodeValid;
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
                Intent intent = new Intent(ServiceForm.this, ServiceService.class);
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
