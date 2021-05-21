package com.example.selfieeclick.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selfieeclick.Adaptor.MessagesAdaptor;
import com.example.selfieeclick.ModelClass.Messages;
import com.example.selfieeclick.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class chat_activity extends AppCompatActivity {

    String RecieverImage,Recieveruid,RecieverName,SenderUid;
    CircleImageView profile_imageview;
    TextView recievername;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    public static String sImage;
    public static String rImage;

    CardView sendbtn;
    EditText edittext;
    String senderroom,recieverroom;
    RecyclerView messageadaptor;
    ArrayList<Messages> messagesArrayList;


    MessagesAdaptor msgadaptor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activity);

        database=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();


        RecieverImage=getIntent().getStringExtra("RecieverImage");
        RecieverName=getIntent().getStringExtra("name");
        Recieveruid=getIntent().getStringExtra("uid");
        messagesArrayList=new ArrayList<>();


        profile_imageview=findViewById(R.id.profileimage);
        recievername=findViewById(R.id.recievername);

        messageadaptor=findViewById(R.id.msgadpator);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageadaptor.setLayoutManager(linearLayoutManager);
        msgadaptor= new MessagesAdaptor(chat_activity.this,messagesArrayList);
        messageadaptor.setAdapter(msgadaptor);



        sendbtn=findViewById(R.id.sendbtn);
        edittext=findViewById(R.id.edittext);

        Picasso.get().load(RecieverImage).into(profile_imageview);
        recievername.setText(""+RecieverName);

        SenderUid=firebaseAuth.getUid();
        senderroom=SenderUid+Recieveruid;
        recieverroom=Recieveruid+SenderUid;

        DatabaseReference reference=database.getReference().child("user").child(firebaseAuth.getUid());
        DatabaseReference chatreference=database.getReference().child("chats").child(senderroom).child("messages");



        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Messages messages=dataSnapshot.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                msgadaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sImage=snapshot.child("imageuri").getValue().toString();
                rImage=RecieverImage;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=edittext.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(chat_activity.this, "pls enter valid msg", Toast.LENGTH_SHORT).show();
                    return;
                }
                edittext.setText("");
                Date date=new Date();

                Messages messages=new Messages(message,SenderUid,date.getTime());
                database=FirebaseDatabase.getInstance();
                database.getReference().child("chats")
                        .child(senderroom)
                        .child("messages")
                        .push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats")
                                .child(recieverroom)
                                .child("messages")
                                .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });

            }
        });















    }
}