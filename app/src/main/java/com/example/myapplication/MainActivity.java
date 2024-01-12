package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ImageView searchView;
    private BottomNavigationView myBottomNavigation;
    private static String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatFragment chatFragment1=new chatFragment();
        profileFragment profileFragment1=new profileFragment() ;
        setContentView(R.layout.activity_main);
        searchView=findViewById(R.id.search_button);
        myBottomNavigation=findViewById(R.id.bottomnavigation);
        myBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.navigation_chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,chatFragment1).commit();
                        break;
                    case R.id.navigation_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,profileFragment1).commit();
                        break;
                }
                return true;
            }
        });
        myBottomNavigation.setSelectedItemId(R.id.navigation_chat);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        if(getIntent().getExtras().getString("KEY")!=null)
        {
            String usernameX=getIntent().getExtras().getString("usernameX");
            String phonenumberX=getIntent().getExtras().getString("phonenumberX");
            String userIdX=getIntent().getExtras().getString("userIdX");
            Log.d("TESTIFY",usernameX);
            Log.d("TESTIFY",phonenumberX);
            Log.d("TESTIFY",userIdX);
        }
    }
}