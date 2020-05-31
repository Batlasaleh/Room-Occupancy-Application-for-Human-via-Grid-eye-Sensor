package com.example.room_occupancy;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteRoom extends AppCompatActivity {
    private Button cancel;
    private ImageView home;
    private ImageView info;
    private Spinner roomNumber;
    private Button delete;
    DatabaseReference reff;
    addroomDB addroom;
    ArrayList<String> rooms;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    String num;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_room);
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

        cancel=findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCancel();
            }
        });

        roomNumber=findViewById(R.id.roomList);
        delete=findViewById(R.id.deleteButton);

        reff= FirebaseDatabase.getInstance().getReference("room");
        rooms=new ArrayList<>();
        adapter= new ArrayAdapter<String>(DeleteRoom.this,android.R.layout.simple_spinner_dropdown_item,rooms);

        roomNumber.setAdapter(adapter);
        reciver();
        roomNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                num= roomNumber.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRoom(num);
            }
        });

    }

    private void deleteRoom(String num) {
        DatabaseReference droom=FirebaseDatabase.getInstance().getReference("room").child(num);
        DatabaseReference dtable=FirebaseDatabase.getInstance().getReference("lectures").child(num);

        droom.removeValue();
        dtable.removeValue();
        Dialog();
    }
    private void Dialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Room is deleted successfully!");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        openCancel();
                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void reciver() {
        listener = reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    rooms.add(item.child("roomnum").getValue().toString());
                }
                adapter.notifyDataSetChanged();
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
    public void openCancel(){
        Intent intent=new Intent(this,ModifyRooms.class);
        startActivity(intent);
    }
}
