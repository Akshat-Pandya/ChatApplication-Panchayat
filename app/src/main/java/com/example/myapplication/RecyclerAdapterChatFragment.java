package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterChatFragment extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MessageModel> dataList;

    public RecyclerAdapterChatFragment(ArrayList<MessageModel> dataList) {
        this.dataList = dataList;
    }
    public void addData(MessageModel message) {
        dataList.add(message);
    }

    @Override
    public int getItemViewType(int position) {
        MessageModel message = dataList.get(position);
        return message.getSenderid().equals(FirebaseUtility.getCurrentUserId()) ? 0 : 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        if(viewType==0)
        {
            View view=inflater.inflate(R.layout.message_sent_layout,parent,false);
            return new SentMessageViewHolder(view);
        }
        else{
            View view=inflater.inflate(R.layout.message_received_layout,parent,false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel message=dataList.get(position);
        if(holder instanceof SentMessageViewHolder)
        {
            ((SentMessageViewHolder) holder).bind(message);
        }
        else if(holder instanceof ReceivedMessageViewHolder)
        {
            ((ReceivedMessageViewHolder)holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void setData(ArrayList<MessageModel> modifieddatalist) {
        dataList=modifieddatalist;
    }
}
class SentMessageViewHolder extends RecyclerView.ViewHolder {
    private TextView sentMessageTextView;
    private TextView timestamp;

    public SentMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        sentMessageTextView = itemView.findViewById(R.id.sentMessageTextView);
        timestamp=itemView.findViewById(R.id.sentTimestampTextView);
    }

    public void bind(MessageModel message) {
        sentMessageTextView.setText(message.getMessage());
        timestamp.setText(message.getTime_stamp());
    }
}
// ReceivedMessageViewHolder.java
 class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
    private TextView receivedMessageTextView;
    private TextView timestamp;

    public ReceivedMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        receivedMessageTextView = itemView.findViewById(R.id.receivedMessageTextView);
        timestamp=itemView.findViewById(R.id.receivedTimestampTextView);
    }

    public void bind(MessageModel message) {
        receivedMessageTextView.setText(message.getMessage());
        timestamp.setText(message.getTime_stamp());

    }
}
