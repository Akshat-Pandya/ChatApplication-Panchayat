package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginUserName extends AppCompatActivity {
    private EditText editTextusername;
    private Button startChatting;
    private String username;
    private String phonenumber;
    private FirebaseDatabase db;
    private  UserTemplate user ;
    private DatabaseReference rf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_name);
        editTextusername = findViewById(R.id.editTextUsername);
        startChatting = findViewById(R.id.myButton3);
        String phonenumber = getIntent().getExtras().getString("phonenumber");


        /*
        updateUsernameIfSaved();
        */
        db=FirebaseDatabase.getInstance();
        rf=db.getReference("ChatApp Users");
        rf.child(phonenumber).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot=task.getResult();
                         user=dataSnapshot.getValue(UserTemplate.class);
                        if(user.getUsername()!=null)
                        {
                            editTextusername.setText(user.getUsername());
                        }
                    }
                }
            }
        });
        startChatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextusername.getText().toString().isEmpty()) {
                    username = editTextusername.getText().toString();
                    if(user==null){
                        user = new UserTemplate(username, phonenumber, FirebaseUtility.getCurrentDateTime(), FirebaseUtility.getCurrentUserId());
                    rf.child(phonenumber).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginUserName.this, "Task successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginUserName.this, MainActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(LoginUserName.this, "Task Unsuccessful please retry", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                    else{
                        user.setUsername(username);
                        Intent intent = new Intent(LoginUserName.this, MainActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                    }
                   }
                else {
                    Toast.makeText(LoginUserName.this, "Please enter username", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    }
