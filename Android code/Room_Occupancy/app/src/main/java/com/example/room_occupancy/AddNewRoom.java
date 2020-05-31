package com.example.room_occupancy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewRoom extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button cancel;
    private Button lectures;
    private ImageView home;
    private ImageView info;
    private EditText sensorID;
    private Spinner floors;
    private EditText roomnum;
    private Spinner facilities;
    private Spinner roomSize;
    private EditText chairs;
    private Button add;
    DatabaseReference reff;
    addroomDB addroom;
    String id;
    String flors,facili,size,charir;
    private FirebaseAuth firebaseAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_room);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        sensorID=findViewById(R.id.roomNumberText);
        floors=findViewById(R.id.floorList);
        floors.setOnItemSelectedListener(this);
        roomnum=findViewById(R.id.RoomNumberText);
        facilities=findViewById(R.id.FacilitiesList);
        facilities.setOnItemSelectedListener(this);
        roomSize=findViewById(R.id.RoomSizeList);
        roomSize.setOnItemSelectedListener(this);
        chairs=findViewById(R.id.ChairsText);
        add=findViewById(R.id.AddButton);

        addroom=new addroomDB();

        reff= FirebaseDatabase.getInstance().getReference().child("room");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flors=floors.getSelectedItem().toString();
                facili=facilities.getSelectedItem().toString();
                size=roomSize.getSelectedItem().toString();
                charir=chairs.getText().toString().trim();

                addroom.setChairs(charir);
                addroom.setFacilities(facili);
                addroom.setFloor(flors);
                addroom.setRoomnum(roomnum.getText().toString().trim());
                addroom.setRoomSize(size);
                addroom.setSensorID(sensorID.getText().toString().trim());

                reff.child(sensorID.getText().toString().trim()).setValue(addroom);

                Dialog();
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

        cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel();
            }
        });

        lectures=findViewById(R.id.Lecturesbutton);
        lectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLectures();
            }
        });

    }

    private void Dialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Room is added successfully!");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Cancel();
                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public void openHome(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openInfo(){
        firebaseAuth.signOut();
        Intent intent=new Intent(this,Info.class);
        startActivity(intent);
    }
    public void Cancel(){
        Intent intent=new Intent(this,ModifyRooms.class);
        startActivity(intent);
    }

    public void OpenLectures(){
        Intent intent=new Intent(this,LecturesTable.class);
        intent.putExtra("roomNum",roomnum.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
