package com.vogella.android.retrofitgithub.user.signup;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.common.BackToolbar;
import com.vogella.android.retrofitgithub.common.user.BackToolbarBL;
import com.vogella.android.retrofitgithub.services.servicesService.ServiceService;
import com.vogella.android.retrofitgithub.user.signin.Login;

public class Verification extends AppCompatActivity {
    Button verify;
    EditText verification;
    private com.vogella.android.retrofitgithub.common.user.BackToolbarBL BackToolbarBL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        verify = findViewById(R.id.verify);
        verification = findViewById(R.id.verification);
        BackToolbarBL = new BackToolbarBL(Verification.this, new Login());
        int verificationcode = 0;
        verify.setOnClickListener(v -> {
            // verificationcode = "value from server "
            /*if(Integer.parseInt(verification.getText().toString())== verificationcode){

                Intent intent = new Intent(Verification.this, ServiceService.class);
                startActivity(intent);
            }

            else{
                Toast.makeText(getApplicationContext(),"wrong verificatino code",Toast.LENGTH_LONG).show();

            }*/

            Intent intent = new Intent(Verification.this, ServiceService.class);
            startActivity(intent);

        });
    }
}
