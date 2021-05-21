package com.example.selfieeclick.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfieeclick.ModelClass.User;
import com.example.selfieeclick.R;
import com.example.selfieeclick.activity.DashBoard;
import com.example.selfieeclick.activity.chat_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.Viewholder> {
    Context dashboard;
    ArrayList<User> userArrayList;


    public UserAdaptor(DashBoard dashBoard, ArrayList<User> userArrayList) {
        this.dashboard= dashBoard;
        this.userArrayList= userArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(dashboard).inflate(R.layout.item_user,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        User users = userArrayList.get(position);

        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(users.getUid()))
        {
            holder.itemView.setVisibility(View.GONE);
        }

        holder.username.setText(users.getName());
        holder.userstatus.setText(users.getStatus());
        Picasso.get().load(users.getImageuri()).placeholder(R.drawable.profileimage).into(holder.userprofile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(dashboard, chat_activity.class);
                intent.putExtra("name",users.getName());
                intent.putExtra("RecieverImage",users.getImageuri());
                intent.putExtra("uid",users.getUid());
                dashboard.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        CircleImageView userprofile;
        TextView username;
        TextView userstatus;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            userprofile=itemView.findViewById(R.id.profileicon);
            username=itemView.findViewById(R.id.username);
            userstatus=itemView.findViewById(R.id.userstatus);


        }
    }
}
