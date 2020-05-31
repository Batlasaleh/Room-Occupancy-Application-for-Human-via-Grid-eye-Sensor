package com.example.room_occupancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class announcements extends AppCompatActivity {
    private ImageView home;
    private ImageView info;
    private DatabaseReference reff;
    ListView ListViweAnnouncement;
    List<AddAnnouncementsDB> announcementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        reff= FirebaseDatabase.getInstance().getReference("announcements");
        ListViweAnnouncement=findViewById(R.id.listViewAnnouncements);
        announcementList=new ArrayList<>();

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

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                announcementList.clear();
                for(DataSnapshot announcementSnapshot:dataSnapshot.getChildren()){
                    AddAnnouncementsDB announcements=announcementSnapshot.getValue(AddAnnouncementsDB.class);
                    announcementList.add(announcements);
                }
                announcements_card adapter=new announcements_card(announcements.this, announcementList);
                ListViweAnnouncement.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
