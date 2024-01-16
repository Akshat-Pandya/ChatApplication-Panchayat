package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private ProgressBar progressBar;
    private Uri imageUri;
    private Bitmap imageBitmap;
    private TextView updateProfile;
    private EditText usernameeditText,bioeditText;
    private CircleImageView profileimage;
    private ImageButton updateprofileimage;
    private String username,profileimageurl,profilebio;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private final int REQUEST_GALLERY_PICK=1;
    private final int REQUEST_CAMERA_CAPTURE=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        updateProfile=findViewById(R.id.updateProfileButton);
        progressBar=findViewById(R.id.progressBar);
        updateprofileimage=findViewById(R.id.update_profileimage);
        usernameeditText=findViewById(R.id.usernameEditText);
        bioeditText=findViewById(R.id.bioEditText);
        profileimage=findViewById(R.id.profileImageView);
        progressBar.setVisibility(View.VISIBLE);
        setPredefinedParams();
        updateprofileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectionDialog();
            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=usernameeditText.getText().toString();
                profilebio=bioeditText.getText().toString();
                // fetching current UserTemplate for modification as per requirement
                ref=db.getReference("ChatApp Users");
                ref.child(FirebaseUtility.getCurrentUserPhonenumber()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                DataSnapshot snapshot=task.getResult();
                                UserTemplate userTemplate=snapshot.getValue(UserTemplate.class);
                                if(username!=null){
                                userTemplate.setUsername(username);}
                                if(profilebio!=null){
                                userTemplate.setProfilebio(profilebio);}
                                uploadDetails(userTemplate);
                            }
                    }
                });
            }
        });
    }
    private void uploadDetails(UserTemplate userTemplate) {
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference strref=firebaseStorage.getReference("profileimages/");
        String imagename=userTemplate.getUserId();
        if(imageUri!=null)
        {
            strref.child(imagename).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            profileimageurl = uri.toString();
                            Log.d("PROFILEIMAGEURL",profileimageurl);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this, "Couldnt obtain image url", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        if(profileimageurl!=null){
            Log.d("PROFILEIMAGEURL2",profileimageurl);
        userTemplate.setProfileimage_url(profileimageurl);}
        ref=db.getReference("ChatApp Users");
        ref.child(FirebaseUtility.getCurrentUserPhonenumber()).setValue(userTemplate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(EditProfile.this, "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }


    private void setPredefinedParams() {
        // This method will set the current values of each view
        String userPhonenumber=FirebaseUtility.getCurrentUserPhonenumber();
        db=FirebaseDatabase.getInstance();
        ref=db.getReference("ChatApp Users");
        ref.child(userPhonenumber).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    DataSnapshot dataSnapshot=task.getResult();
                    UserTemplate currentUserData=dataSnapshot.getValue(UserTemplate.class);
                    username= currentUserData.getUsername();
                    profilebio=currentUserData.getProfilebio();
                    profileimageurl=currentUserData.getProfileimage_url();
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(EditProfile.this, username, Toast.LENGTH_SHORT).show();
                    usernameeditText.setText(username);
                    if(profilebio!=null){bioeditText.setText(profilebio);}
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(EditProfile.this, "Could not sync user details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(profileimageurl!=null)
        {
            Glide.with(this).load(profileimageurl).into(profileimage);
        }

    }

    private void showSelectionDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Select image source -");
        dialog.setIcon(R.drawable.baseline_image_24);
        dialog.setPositiveButton("Capture", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                captureImage();
            }
        });
        dialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectFromGallery();
            }
        });
        dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(EditProfile.this, "Profile Image is unmodified", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        dialog.setCancelable(true);
    }

    private void selectFromGallery() {
        // Image Selection from the gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,REQUEST_GALLERY_PICK);

    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // the purpose of this if block is to resolve that is there any app which can handle image capture event
            startActivityForResult(takePictureIntent, REQUEST_CAMERA_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data!=null)
        {
            switch(requestCode)
            {
                case REQUEST_GALLERY_PICK:
                    // Gallery
                    imageUri=data.getData(); // The URI is a reference to the image in the content provider.
                    profileimage.setImageURI(imageUri);
                    break;
                case REQUEST_CAMERA_CAPTURE:
                    // Camera
                    Bundle extras = data.getExtras(); // In captured image; the image data is often returned as a thumbnail in the extras of the Intent Object and from that the key for image data is usually "data"
                    imageBitmap = (Bitmap) extras.get("data");
                    profileimage.setImageBitmap(imageBitmap);
                    // Convert Bitmap to Uri (you may need to implement this method)
                    imageUri = getImageUri(getApplicationContext(), imageBitmap);
                    break;
            }
        }
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}