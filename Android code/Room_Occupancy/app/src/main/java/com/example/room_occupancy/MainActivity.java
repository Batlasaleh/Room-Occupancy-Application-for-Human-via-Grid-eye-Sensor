package com.example.room_occupancy;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button announcement;
    private ImageView home;
    private ImageView info;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        button1=findViewById(R.id.findRoomButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFindRoom();
            }
        });

        button2=findViewById(R.id.adminButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdmin();
            }
        });

        button3=findViewById(R.id.contactButton);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContact();
            }
        });

        announcement=findViewById(R.id.announcementButton);
        announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAnnouncement();
            }
        });


        home=findViewById(R.id.HomeButton1);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

        info=findViewById(R.id.infoButton2);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfo();
            }
        });


    }

    public void openFindRoom(){
        Intent intent=new Intent(this,FindRoom.class);
        startActivity(intent);
    }

    public void openAnnouncement(){
        reff= FirebaseDatabase.getInstance().getReference().child("announcements");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Intent intent=new Intent(MainActivity.this,announcements.class);
                    startActivity(intent);
                }
                else{
                    Intent intent2=new Intent(MainActivity.this,AnnouncementEmpty.class);
                    startActivity(intent2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openAdmin(){
        Intent intent=new Intent(this,AdminLogin.class);
        startActivity(intent);
    }

    public void openContact(){
        Intent intent=new Intent(this,Contact.class);
        startActivity(intent);
    }



    public void openHome(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openInfo(){
        Intent intent=new Intent(this,Info.class);
        startActivity(intent);
    }



}
