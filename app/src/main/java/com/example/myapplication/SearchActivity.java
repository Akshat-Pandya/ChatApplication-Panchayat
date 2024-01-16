package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private ImageView backpress,searchButton;
    private RecyclerView recyclerView;
    private ArrayList<UserTemplate> userList;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backpress=findViewById(R.id.backpress);
        searchButton=findViewById(R.id.search_button);
        editTextUsername=findViewById(R.id.search_username);
        recyclerView=findViewById(R.id.recycler);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList=new ArrayList<>();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("ChatApp Users");

        reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for( DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        UserTemplate temp=dataSnapshot.getValue(UserTemplate.class);
                        userList.add(temp);
                    }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 Log.d("DATABASE ERROR",error.toString());
             }
         });
         adapter=new RecyclerAdapter(userList,getApplicationContext());

        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    filterDataset(editTextUsername.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




       recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchelement=editTextUsername.getText().toString();
                if (searchelement.isEmpty())
                {
                    editTextUsername.setError("Invalid Username");
                }
                setUpSearchRecyclerView(searchelement);
            }
        });

    }

    private void filterDataset(String enteredText) {
        ArrayList<UserTemplate> filteredDataset=new ArrayList<>();
        for(UserTemplate temp:userList)
        {
            if(temp.getUsername().toLowerCase().contains(enteredText.toLowerCase()))
                filteredDataset.add(temp);
        }
        adapter.setData(filteredDataset); // method to change the data source of the recycler view as the text is being entered by the user dynamically
        adapter.notifyDataSetChanged();

    }

    private void setUpSearchRecyclerView(String searchelement) {
    }
}