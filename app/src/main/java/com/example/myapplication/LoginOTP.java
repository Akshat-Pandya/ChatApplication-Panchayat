package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.PhoneAuthCredential;
        import com.google.firebase.auth.PhoneAuthProvider;

        import java.util.concurrent.TimeUnit;

public class LoginOTP extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button proceed;
    private String verificationId;
    private EditText otpEditText1,otpEditText2,otpEditText3,otpEditText4,otpEditText5,otpEditText6;
    private String phonenumber;
    private ProgressBar progressBar;
    private TextView resendotp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        mAuth = FirebaseAuth.getInstance();
       proceed=findViewById(R.id.myButton2);
       progressBar=findViewById(R.id.progressBar);
        otpEditText1 = findViewById(R.id.box1);
        otpEditText2 = findViewById(R.id.box2);
        otpEditText3 = findViewById(R.id.box3);
        otpEditText4 = findViewById(R.id.box4);
        otpEditText5 = findViewById(R.id.box5);
        otpEditText6 = findViewById(R.id.box6);
        resendotp=findViewById(R.id.resendotp);
        setOtpTextWatcher(otpEditText1, otpEditText2);
        setOtpTextWatcher(otpEditText2, otpEditText3);
        setOtpTextWatcher(otpEditText3, otpEditText4);
        setOtpTextWatcher(otpEditText4, otpEditText5);
        setOtpTextWatcher(otpEditText5, otpEditText6);

        phonenumber=getIntent().getExtras().getString("phonenumber");

        // sending the otp
        sendVerificationCode(phonenumber);
        progressBar.setVisibility(View.VISIBLE);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otpEditText1.getText().toString().isEmpty() && otpEditText1.getText().toString().isEmpty() && otpEditText1.getText().toString().isEmpty() && otpEditText1.getText().toString().isEmpty() && otpEditText1.getText().toString().isEmpty() && otpEditText1.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginOTP.this, "Invalid OTP - Please Reenter", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else {
                        // extracting the 6 digit code from checkboxes and sending it for verification
                        String code=otpEditText1.getText().toString()+otpEditText2.getText().toString()+otpEditText3.getText().toString()+otpEditText4.getText().toString()+otpEditText5.getText().toString()+otpEditText6.getText().toString();
                        verifyCode(code);
                }
                }
        });

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode(phonenumber);
            }
        });

    }



    private void setOtpTextWatcher(final EditText currentEditText, final EditText nextEditText) {
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    nextEditText.requestFocus();
                }
            }
        });
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity for callback binding
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // if entered number is on same device
                        // Automatically verify the code if the SMS is received.
                        autofillData(phoneAuthCredential);
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.w("PhoneAuthActivity", "onVerificationFailed", e);
                        Toast.makeText(LoginOTP.this, "Could not process OTP Please Retry after sometime", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        confirmResend();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        // Save the verification ID and resending token so we can use them later
                        verificationId = s;
                        startCountDownTimer();
                    }
                });
    }

    private void confirmResend() {
        resendotp.setClickable(true);
        resendotp.setVisibility(View.VISIBLE);
        resendotp.setEnabled(true);
        resendotp.setText("Resend OTP");
    }

    private void startCountDownTimer() {
        resendotp.setEnabled(true);
        resendotp.setVisibility(View.VISIBLE);
        resendotp.setClickable(false);
        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long millisuntilfinished) {
                //updating the ui after each second and during this periiod the ui is unclickable
                resendotp.setText("Resend OTP in "+(millisuntilfinished/1000)+" seconds");
            }

            @Override
            public void onFinish() {
                // making the ui clickable after completion of the time of 60 seconds.
                confirmResend();
            }
        }.start();
    }

    private void autofillData(PhoneAuthCredential phoneAuthCredential) {
        String code=phoneAuthCredential.getSmsCode().toString();
        otpEditText1.setText(code.charAt(0));
        otpEditText2.setText(code.charAt(1));
        otpEditText3.setText(code.charAt(2));
        otpEditText4.setText(code.charAt(3));
        otpEditText5.setText(code.charAt(4));
        otpEditText6.setText(code.charAt(5));

    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("PhoneAuthActivity", "signInWithCredential:success");
                        Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                        // User signed in successfully
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent=new Intent(LoginOTP.this,LoginUserName.class);
                        intent.putExtra("phonenumber",phonenumber);
                        startActivity(intent);
                        finish();

                    }
                    else {
                        Log.w("PhoneAuthActivity", "signInWithCredential:failure", task.getException());
                        Toast.makeText(this, "Signin Failed : PLease try after few seconds . ", Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user.
                        progressBar.setVisibility(View.INVISIBLE);
                        confirmResend();
                    }
                });
    }
}
