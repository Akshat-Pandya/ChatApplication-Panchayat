package com.example.myapplication;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private ArrayList<UserTemplate> dataset;
    private Context context;

    public RecyclerAdapter(ArrayList<UserTemplate> dataset,Context context) {
        this.dataset = dataset;
        this.context=context;

    }
    void setData(ArrayList<UserTemplate> filteredDataset)
    {
        this.dataset=filteredDataset;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.username.setText(dataset.get(position).getUsername());
        holder.phonenumber.setText(dataset.get(position).getPhonenumber());

        int random = (int) (Math.random() * 8) + 1;
       if(random==1){
        holder.icon.setImageResource(R.drawable.chick);
       }
        if(random==2){
            holder.icon.setImageResource(R.drawable.koala);
        }
        if(random==3){
            holder.icon.setImageResource(R.drawable.fox);
        }
        if(random==4){
            holder.icon.setImageResource(R.drawable.cat);
        }
        if(random==5){
            holder.icon.setImageResource(R.drawable.lion);
        }
        if(random==6){
            holder.icon.setImageResource(R.drawable.duck);
        }
        if(random==7){
            holder.icon.setImageResource(R.drawable.whale);
        }
        if(random==8){
            holder.icon.setImageResource(R.drawable.owl);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,MainActivity.class);
                 ImageHolder.setUsernameX(dataset.get(holder.getAdapterPosition()).getUsername());
                 ImageHolder.setPhonenumberX(dataset.get(holder.getAdapterPosition()).getPhonenumber());
                 ImageHolder.setUserIdX(dataset.get(holder.getAdapterPosition()).getUserId());
                ImageHolder.setLikesOnProfileX(dataset.get(holder.getAbsoluteAdapterPosition()).getLikesOnProfile());
                Drawable imageDrawable=holder.icon.getDrawable();
                ImageHolder.setImageDrawable(imageDrawable);
                context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)); // startActivity is not directly available inside the adapter class. It is a part of Context class .
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataset.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        ImageView icon;
        TextView phonenumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             username=itemView.findViewById(R.id.recycler_username);
             icon=itemView.findViewById(R.id.recycler_icon);
             phonenumber=itemView.findViewById(R.id.recycler_contact);
        }
    }
}
