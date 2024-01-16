package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatWithUserProfile extends AppCompatActivity {

    private ImageView likeprofile;
    private boolean result=false;
    private boolean flag = true;
    private CircleImageView profileimage;
    private ProgressBar progressBar;
    ArrayList<LikedProfile> likedProfileArrayList;
    private TextView usernameTextView, userbioTextView, userPhonenumber;
    private TextView likesOnProfile;
    private UserTemplate ChatWithUserTemplate;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_user_profile);
        likeprofile = findViewById(R.id.likeprofile);
        likesOnProfile = findViewById(R.id.likesTextView);
        profileimage = findViewById(R.id.profileImageView);
        usernameTextView = findViewById(R.id.usernameTextView);
        userbioTextView = findViewById(R.id.bioTextView);
        userPhonenumber = findViewById(R.id.phonenumber);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("ChatApp Users");
        // fetching the current state of ChatWith user profile and update UI accordingly
        ref.child(ImageHolder.getPhonenumberX()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    UserTemplate ut = (UserTemplate) snapshot.getValue(UserTemplate.class);
                    updateProfileUI();
                } else {
                    Toast.makeText(ChatWithUserProfile.this, "Failed to fetch user details", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        //liking the profile
        likeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isProfileLiked()){

                    ref.child(ImageHolder.getPhonenumberX()).child("likesOnProfile").setValue(ImageHolder.getLikesOnProfile()+1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(ChatWithUserProfile.this, "Wow! You just liked "+ImageHolder.getUsernameX()+"'s Profile.", Toast.LENGTH_SHORT).show();
                                likeprofile.setImageResource(R.drawable.love);
                                DatabaseReference rx=FirebaseDatabase.getInstance().getReference("ChatApp Users");
                                LikedProfile likedProfile=new LikedProfile(ImageHolder.getUsernameX(),ImageHolder.getPhonenumberX());
                                rx.child(FirebaseUtility.getCurrentUserPhonenumber()).child("Liked Profiles").child(ImageHolder.getPhonenumberX()).setValue(likedProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Log.d("LIKED PROFILES UPDATED","Liked profiles updated successfully");
                                                updateProfileUI();
                                            }
                                            else {
                                                Log.d("ERROR UPDATING LIKED PROFILES","Liked profiles cant be updated");
                                            }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(ChatWithUserProfile.this, "Please try later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(ChatWithUserProfile.this, "Dear you have already liked this profile :)", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean isProfileLiked() {
        // Checking if the profile is already liked or not
        DatabaseReference rmx=FirebaseDatabase.getInstance().getReference("ChatApp Users");
        rmx.child(FirebaseUtility.getCurrentUserPhonenumber()).child("Liked Profiles").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()) {

                        DataSnapshot dataSnapshot=task.getResult();
                        for(DataSnapshot temp : dataSnapshot.getChildren()) {

                            LikedProfile lp=(LikedProfile) temp.getValue(LikedProfile.class);
                            if(lp.getPhonenumber().equals(ImageHolder.getPhonenumberX())) {
                                result=true;
                                break;
                            }
                        }
                    }
            }
        });
        return result;
    }
    private void updateProfileUI() {
        DatabaseReference x=FirebaseDatabase.getInstance().getReference("ChatApp Users");
        x.child(ImageHolder.getPhonenumberX()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        UserTemplate userTemplate=(UserTemplate) task.getResult().getValue(UserTemplate.class);
                        if (userTemplate != null) {
                            String username = userTemplate.getUsername();
                            String phonenumber = userTemplate.getPhonenumber();
                            String imageUrl = userTemplate.getProfileimage_url();
                            String userbio = userTemplate.getProfilebio();
                            likesOnProfile.setText("Likes on profile - " + userTemplate.getLikesOnProfile());
                            if (username != null) {
                                usernameTextView.setText(username);
                            }
                            if (phonenumber != null) {
                                userPhonenumber.setText("contact number - " + phonenumber);
                            }
                            if (userbio != null) {
                                userbioTextView.setText(userbio);
                            }
                            if (imageUrl != null) {
                                Glide.with(getApplicationContext()).load(imageUrl).into(profileimage);
                            }
                            if(isProfileLiked())
                            {
                                likeprofile.setImageResource(R.drawable.love);
                            }

                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
            }
        });


    }

    }