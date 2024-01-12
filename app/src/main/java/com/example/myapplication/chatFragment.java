package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chatFragment extends Fragment {

    /*
    private RecyclerView recyclerView;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private String senderUserId;
    private String receiverUserId;
    private String chatid;
    private String receiverid;*/
    public chatFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_chat,container,false);
      //  recyclerView=view.findViewById(R.id.recycler);
        return view;
    }


}