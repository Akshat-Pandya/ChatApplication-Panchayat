package com.example.myapplication;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class chatFragment extends Fragment {


    private RecyclerView recyclerView;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private String senderUserId,senderUsername;
    private String receiverUserId,receiverusername;
    private String chatid;
    private EditText messagesendtext;
    private ImageView sendButton,usericon;
    private TextView chatwith_username_textview;

    private RecyclerAdapterChatFragment adapterChatFragment;
    private ArrayList<MessageModel> dataList;
    private RecyclerAdapterChatFragment adapter;
    private ScrollView scrollView;
    public chatFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_chat,container,false);
        recyclerView=view.findViewById(R.id.recycler_chat);
        sendButton=view.findViewById(R.id.sendButton);
        messagesendtext=view.findViewById(R.id.messagetext);
        scrollView=view.findViewById(R.id.chatscroll);
        chatwith_username_textview=view.findViewById(R.id.usernameTextView);
        usericon=view.findViewById(R.id.profileImageView);
        if(receiverusername!=null&&ImageHolder.getImageDrawable()!=null){
        chatwith_username_textview.setText(receiverusername);
        usericon.setImageDrawable(ImageHolder.getImageDrawable());
        }
        adjustScrolling();
        db=FirebaseDatabase.getInstance();
        ref=db.getReference("Chats");
        dataList=new ArrayList<>();
        if(chatid!=null) {
            ref.child(chatid).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    MessageModel newMessage = snapshot.getValue(MessageModel.class);
                    if (newMessage != null) {
                        adapter.addData(newMessage);
                        adapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        adapter=new RecyclerAdapterChatFragment(dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // in the fragments always use a getContext() for obtaining the context
        recyclerView.setAdapter(adapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=messagesendtext.getText().toString();
                if(message.isEmpty())
                {
                   messagesendtext.setError("No message entered");
                }
                else{
                    // Create a SimpleDateFormat with the desired format
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                    // Format the current date and time
                    String timestamp = dateFormat.format(new Date());


                    MessageModel mymessage=new MessageModel(message,timestamp,senderUserId,receiverUserId);
                    // A new message node created within the node 'chatid' which denotes a message
                    String messagekey=ref.child(chatid).push().getKey();
                    ref.child(chatid).child(messagekey).setValue(mymessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d("SEND STATUS","Message sent successfully to "+senderUsername);
                                messagesendtext.setText("");
                                messagesendtext.requestFocus();
                            }
                            else{
                                Log.d("SEND STATUS","Could not send message");
                            }
                        }
                    });


                }
            }
        });


        return view;
    }

    private void adjustScrolling() {
        // Scroll to the bottom after a short delay to allow the view to be measured and laid out
        // Add a ViewTreeObserver to wait for layout completion on ScrollView
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener to avoid multiple calls
                scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Scroll to the bottom of ScrollView
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

        // Add a ViewTreeObserver to wait for layout completion on RecyclerView
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener to avoid multiple calls
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Scroll to the bottom of RecyclerView
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }


    public void setData(String usernameX, String phonenumberX, String userIdX) {
        senderUserId=FirebaseUtility.getCurrentUserId();
        receiverUserId=userIdX;
        generateChatId(senderUserId,receiverUserId);
        receiverusername=usernameX;

    }

    private void generateChatId(String senderUserId, String receiverUserId) {
        // Concatenate senderUserId and receiverUserId
        chatid = senderUserId + receiverUserId;

        // Convert the string to a character array
        char[] arr = chatid.toCharArray();

        // Sort the character array
        Arrays.sort(arr);

        // Convert the sorted character array back to a string
        chatid = new String(arr);
    }

}