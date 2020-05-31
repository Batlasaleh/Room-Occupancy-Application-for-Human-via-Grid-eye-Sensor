package com.example.room_occupancy;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class ModifyRooms extends AppCompatActivity {
    private ImageView home;
    private ImageView info;
    private Button add;
    private Button edit;
    private Button delete;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_rooms);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
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

        add=findViewById(R.id.AddRoomButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddRoom();
            }
        });

        edit=findViewById(R.id.EditRoomButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditRoom();
            }
        });

        delete=findViewById(R.id.DeleteRoomButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteRoom();
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

    public void openAddRoom(){
        Intent intent=new Intent(this,AddNewRoom.class);
        startActivity(intent);
    }

    public void openEditRoom(){
        Intent intent=new Intent(this,EditRoom.class);
        startActivity(intent);
    }

    public void openDeleteRoom(){
        Intent intent=new Intent(this,DeleteRoom.class);
        startActivity(intent);
    }
}
