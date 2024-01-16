package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileFragment extends Fragment {
    private TextView editProfile;
    private TextView usernameTextView,userbioTextView,likesTextView;
    private CircleImageView userProfileImage;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    public profileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        editProfile=view.findViewById(R.id.editProfileTextView);
        usernameTextView=view.findViewById(R.id.usernameTextView);
        userbioTextView=view.findViewById(R.id.bioTextView);
        likesTextView=view.findViewById(R.id.likesTextView);
        userProfileImage=view.findViewById(R.id.profileImageView);
        db=FirebaseDatabase.getInstance();
        ref=db.getReference("ChatApp Users");
        String userphonenumber=FirebaseUtility.getCurrentUserPhonenumber();
        ref.child(userphonenumber).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        DataSnapshot dataSnapshot=task.getResult();
                        UserTemplate userTemplate=dataSnapshot.getValue(UserTemplate.class);
                        updateCurrentProfile(userTemplate);
                    }
            }
        });


        Context context=getContext();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,EditProfile.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void updateCurrentProfile(UserTemplate userTemplate) {
        String username= userTemplate.getUsername();
        String userbio=userTemplate.getProfilebio();
        String userimageurl=userTemplate.getProfileimage_url();
        int likes=userTemplate.getLikesOnProfile();
        if(username!=null)
        {
            usernameTextView.setText(username);
        }
        if(userbio!=null)
        {
            userbioTextView.setText(userbio);
        }
        if(userimageurl!=null)
        {
            Glide.with(getContext()).load(userimageurl).into(userProfileImage);
        }
        likesTextView.setText("Likes on profile : "+likes);

    }
}