package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class LoginPhoneNumber extends AppCompatActivity {

    private EditText phonenumber;
    private CountryCodePicker ccp;
    private ProgressBar progressBar;
    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_number);
        phonenumber=findViewById(R.id.editTextMobile);
        ccp=findViewById(R.id.ccp);
        progressBar=findViewById(R.id.progressBar);
        next=findViewById(R.id.myButton);

        ccp.registerCarrierNumberEditText(phonenumber);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                    if (!ccp.isValidFullNumber()) {
                        phonenumber.setError("Invalid phone number ! ");
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {

                        FirebaseUtility.setCurrentUserPhonenumber(ccp.getFullNumberWithPlus());

                        Intent intent = new Intent(LoginPhoneNumber.this, LoginOTP.class);
                        intent.putExtra("phonenumber", ccp.getFullNumberWithPlus());
                        startActivity(intent);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

        });


    }
}