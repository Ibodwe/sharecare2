package com.vogella.android.retrofitgithub.user.signup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.common.BackToolbar;
import com.vogella.android.retrofitgithub.common.OwnValidator;
import com.vogella.android.retrofitgithub.common.user.Role;
import com.vogella.android.retrofitgithub.user.MainAuthMenu;

import java.util.Calendar;
import java.util.Date;

public class SignUpSecondScreen extends AppCompatActivity {

    private EditText firstName, lastName, phoneNumber, address, city, zipcode;
    private TextView dateOfBirth;
    private ImageView calender;
    private Button btnNext;
    private OwnValidator ownValidator;
    private BackToolbar backToolbar;
    private UserAddRequest userAddRequest;
    private CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up_second_screen);

        backToolbar = new BackToolbar(SignUpSecondScreen.this, new MainAuthMenu());
        dataInit();

        buttonListeners();

        calendarInit();
    }

    private void calendarInit() {
        calender.setImageResource(R.drawable.profile);

        calender.setOnClickListener(new CalenderListener());

        ccp = findViewById(R.id.ccp);

        ccp.setOnCountryChangeListener(() -> {
            String countryCodeAndroid = ccp.getSelectedCountryCode();
        });
    }

    //calender
    class CalenderListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            DialogFragment dialogfragment = new DatePickerDialogTheme();
            dialogfragment.show(getFragmentManager(), "Theme");
        }
    }

    public static class DatePickerDialogTheme extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView DateOfBirth = getActivity().findViewById(R.id.DateOfBirth);
            DateOfBirth.setText(day + "." + (month + 1) + "." + year);
        }
    }

    public void dataInit() {
        ownValidator = new OwnValidator();
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        zipcode = findViewById(R.id.zipCode);
        btnNext = findViewById(R.id.next);
        calender = findViewById(R.id.calender);
    }


    private void buttonListeners() {

        btnNext.setOnClickListener(view -> {

            //if (!areFieldsValid()) return;

            userAddRequest = UserAddRequest.builder()
                    .firstName("firstName")
                    .lastName("lastName")
                    .phoneNumber("phoneNumber")
                    .dateOfBirth(new Date(2010, 10, 10))
                    .address("address")
                    .city("city")
                    .zipcode("zipcode")
                    .build();

//            userAddRequest = UserAddRequest.builder()
//                    .firstName(firstName.getText().toString())
//                    .lastName(lastName.getText().toString())
//                    .phoneNumber(phoneNumber.getText().toString())
//                    .dateOfBirth(new Date(2010, 10, 10))
//                    .address(address.getText().toString())
//                    .city(city.getText().toString())
//                    .zipcode(zipcode.getText().toString())
//                    .build();

            startNewActivity();


        });
    }

    private boolean areFieldsValid() {
        if (firstName.getText().toString() != null && lastName.getText().toString() != null && phoneNumber.getText().toString() != null
                && address.getText().toString() != null && city.getText().toString() != null && dateOfBirth.getText().toString() != null) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "There are empty fields. Please fill in the required fields ", Toast.LENGTH_LONG).show();
            return false;
        }


    }

    private void startNewActivity() {
        Intent intent = new Intent(SignUpSecondScreen.this, SignUpThirdScreen.class);
        intent.putExtra("userAddRequest", userAddRequest);
        startActivity(intent);
    }



}
