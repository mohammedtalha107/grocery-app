package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class otp extends AppCompatActivity {

    private Button verifyotp,resendotp;
    private EditText otPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        verifyotp=findViewById(R.id.verify);
        resendotp=findViewById(R.id.resend);
        otPass=findViewById(R.id.otppass);


    }
}