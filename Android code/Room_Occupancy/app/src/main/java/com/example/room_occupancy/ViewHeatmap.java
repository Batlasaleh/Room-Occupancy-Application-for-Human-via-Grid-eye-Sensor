package com.example.room_occupancy;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ViewHeatmap extends AppCompatActivity {
    private Button back;
    private ImageView home;
    private ImageView info;
    private ImageView heat;
    StorageReference storageReference;
    int count=0;
    private TextView num;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_heatmap);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://room-occupancy-5b0b1.appspot.com/images").child("heat.png");
        heat=findViewById(R.id.heat);
        updating();

        num=findViewById(R.id.count);
        home=findViewById(R.id.homeButton2);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

        reff=FirebaseDatabase.getInstance().getReference("occupancy").child("satuts");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String counting=dataSnapshot.child("occupy").getValue().toString();
                num.setText(counting);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        info=findViewById(R.id.infoButton2);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfo();
            }
        });

        back=findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updating() {
        try{
            final File file=File.createTempFile("image","png");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                    heat.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ViewHeatmap.this,"Image Failed to Load",Toast.LENGTH_SHORT);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        count++;
        refresh(1000);
    }

    private void refresh(int i) {
        final Handler handler= new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updating();
            }
        };
        handler.postDelayed(runnable,i);
    }

    public void openHome(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openInfo(){
        Intent intent=new Intent(this,Info.class);
        startActivity(intent);
    }
    public void openRoomInfo(){
        Intent intent=new Intent(this,RoomInfo.class);
        startActivity(intent);
    }
}
