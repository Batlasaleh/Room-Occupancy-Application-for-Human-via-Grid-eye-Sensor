package com.example.room_occupancy;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminServices extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button announcement;
    private ImageView home;
    private ImageView info;
    DatabaseReference reff;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_services);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        button1=findViewById(R.id.findRoomButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminInfo();
            }
        });

        button2=findViewById(R.id.adminButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRoomInfo();
            }
        });

        button3=findViewById(R.id.contactButton);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlert();
            }
        });

        announcement=findViewById(R.id.announcementButton);
        announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAnnouncement();
            }
        });

        home=findViewById(R.id.homeButton2);
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

    public void openAdminInfo(){
        Intent intent=new Intent(this,AdminInfo.class);
        startActivity(intent);
    }

    public void openRoomInfo(){
        Intent intent=new Intent(this,ModifyRooms.class);
        startActivity(intent);
    }

    public void openAnnouncement(){
        Intent intent=new Intent(this,publish_announcements.class);
        startActivity(intent);
    }

    public void openAlert(){
        reff= FirebaseDatabase.getInstance().getReference().child("problem");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()){
                    Intent intent1=new Intent(AdminServices.this,Alert.class);
                    startActivity(intent1);
                }
                else{
                    Intent intent2=new Intent(AdminServices.this,AlertEmpty.class);
                    startActivity(intent2);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void openHome(){
        firebaseAuth.signOut();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openInfo(){
        Intent intent=new Intent(this,Info.class);
        startActivity(intent);
    }

}
