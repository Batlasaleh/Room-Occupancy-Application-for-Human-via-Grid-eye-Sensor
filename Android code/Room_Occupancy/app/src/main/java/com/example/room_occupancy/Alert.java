package com.example.room_occupancy;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Alert extends AppCompatActivity  {
    private ImageView home;
    private ImageView info;
    private DatabaseReference reff;
    ListView ListViweProblems;
    List<sendproblemDB> problemList;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();

        reff=FirebaseDatabase.getInstance().getReference("problem");
        ListViweProblems=findViewById(R.id.listViewProblems);
        problemList=new ArrayList<>();

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
                problemList.clear();
                for(DataSnapshot problemSnapshot:dataSnapshot.getChildren()){
                    sendproblemDB problem=problemSnapshot.getValue(sendproblemDB.class);
                    problemList.add(problem);
                }
                ProblemList adapter=new ProblemList(Alert.this, problemList);
                ListViweProblems.setAdapter(adapter);
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
