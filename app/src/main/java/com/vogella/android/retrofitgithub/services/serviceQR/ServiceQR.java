package com.vogella.android.retrofitgithub.services.serviceQR;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import com.google.zxing.WriterException;
import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.common.BackToolbar;
import com.vogella.android.retrofitgithub.services.servicesService.ServiceService;
import com.vogella.android.retrofitgithub.services.servicesService.TaskLogFragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.BitSet;

public class ServiceQR extends AppCompatActivity {
    Button generate_QR;
    EditText editText;
    ImageView qrView;
    private Toolbar my_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_qr);
        qrView = (ImageView)findViewById(R.id.qrView);
        qrView.setVisibility(View.GONE);
        my_toolbar =(Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        my_toolbar.setTitle("Back");


        my_toolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_arrow_back_white_24dp));
        my_toolbar.setNavigationOnClickListener(v -> getApplicationContext().startActivity(new Intent(ServiceQR.this,ServiceService.class)));
                GenerateQRCodeActivity generateQRCodeActivity = new GenerateQRCodeActivity();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String getTime = sdf.format(date);



                generateQRCodeActivity.generateQRCode(getTime);


                try{
                    qrView.setVisibility(View.VISIBLE);
                    //should chagne code to be editible value.
                    Bitmap qr = generateQRCodeActivity.createQRImage(getTime,200);
                    qrView.setImageBitmap(qr);

                }
                catch (WriterException e){
                    e.printStackTrace();
                }
            }



    }

