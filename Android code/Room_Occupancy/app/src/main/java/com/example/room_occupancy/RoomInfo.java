package com.example.room_occupancy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;


public class RoomInfo extends AppCompatActivity {
    private ImageView home;
    private ImageView info;
    private Button lectures;
    private Button heatmap;
    private Button problem;
    private TextView title;
    String message;
    Query query;
    DatabaseReference reff, rasp;
    private String r;
    private String f;
    private String s;
    private String c,floo;
    private TextView room;
    private TextView facility;
    private TextView size;
    private TextView chairs;
    private TextView status;
    private TextView ocuppy;
    private TextView floor, temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent secondIntent = getIntent();
        message = secondIntent.getStringExtra("RoomInfoTitle");
        title = findViewById(R.id.RoomInfoTitle);
        title.setText("Room " + message + " Info");
        status=findViewById(R.id.statusText);
        ocuppy=findViewById(R.id.occupation);
        floor=findViewById(R.id.floorText);
        room = findViewById(R.id.roomText);
        facility = findViewById(R.id.facilitiesText2);
        size = findViewById(R.id.sizeText);
        chairs = findViewById(R.id.chairsText2);
        temp=findViewById(R.id.tempText);

        query = FirebaseDatabase.getInstance().getReference("room").orderByChild("sensorID").equalTo(message);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    Dialog();
                }
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //key=ds.child("id").getValue(String.class);
                    r=ds.child("roomnum").getValue().toString();
                    c=ds.child("chairs").getValue().toString();
                    f=ds.child("facilities").getValue().toString();
                    s=ds.child("roomSize").getValue().toString();
                    floo=ds.child("floor").getValue().toString();

                    room.setText(r);
                    chairs.setText(c);
                    facility.setText(f);
                    size.setText(s);
                    floor.setText(floo);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rasp=FirebaseDatabase.getInstance().getReference("occupancy").child("satuts");
        rasp.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String o = dataSnapshot.child("occupy").getValue().toString();
                String y = dataSnapshot.child("year").getValue().toString();
                String mon = dataSnapshot.child("month").getValue().toString();
                String d = dataSnapshot.child("day").getValue().toString();
                String h = dataSnapshot.child("hour").getValue().toString();
                String min = dataSnapshot.child("minute").getValue().toString();
                String t = dataSnapshot.child("temp").getValue().toString();
                if (message.equals("1077")) {
                    temp.setText(t + "˚");
                    int oc = Integer.parseInt(o);
                    int year = Integer.parseInt(y);
                    int month = Integer.parseInt(mon);
                    int hour = Integer.parseInt(h);
                    int minute = Integer.parseInt(min);
                    String day = d.toUpperCase();
                    if (LocalDateTime.now().getHour() == hour && LocalDate.now().getMonthValue() == month && LocalDateTime.now().getYear() == year && LocalDate.now().getDayOfWeek().name().equals(day)) {
                        if (oc > 0)
                            ocuppy.setText("Occupied");
                        else
                            ocuppy.setText("Not Occupied");
                        //Toast.makeText(RoomInfo.this, LocalDateTime.now().getHour() + "&" + hour + "/" + LocalDate.now().getMonthValue() + "&" + month, Toast.LENGTH_SHORT).show();
                    } else {

                        offline();
                    }

                    if (status.getText().equals("#")) {
                        status.setText("Unoccupied (Offline)");
                    }
                } else {
                    offline();
                    heatmap.setEnabled(false);
                }
                if (status.getText().equals("#")) {
                    status.setText("Unoccupied (Offline)");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        home = findViewById(R.id.homeButton2);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

        info = findViewById(R.id.infoButton2);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfo();
            }
        });

        lectures = findViewById(R.id.viewLectureButton);
        lectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLectures();
            }
        });

        heatmap = findViewById(R.id.viewHeatmapButton);
        heatmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHeatMap();
            }
        });

        problem = findViewById(R.id.reportProblemButton);
        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReport();
            }
        });

    }
    private void Dialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("The room is not added to the database yet, please choose another!");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        findroom();
                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void findroom() {
        Intent intent=new Intent(this,FindRoom.class);
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

    public void openLectures(){
        Intent intent=new Intent(this,ViewLectures.class);
        intent.putExtra("number",r);
        startActivity(intent);
    }

    public void openHeatMap(){
        Intent intent=new Intent(this,ViewHeatmap.class);
        startActivity(intent);
    }

    public void openReport(){
        Intent intent=new Intent(this,ReportProblem.class);
        intent.putExtra("number",r);
        startActivity(intent);
    }

    public void offline(){
        if (ocuppy.getText().equals("#")) {
            ocuppy.setVisibility(View.INVISIBLE);
            status.setVisibility(View.VISIBLE);
            reff = FirebaseDatabase.getInstance().getReference("lectures").child(message);
            reff.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> times = dataSnapshot.getChildren().iterator();
                    while (times.hasNext()) {
                        DataSnapshot time = times.next();
                        String t = time.getKey();

                        switch (t) {
                            case "sunday7":
                                if (LocalDate.now().getDayOfWeek().name() == "SUNDAY") {//check if the current day matches the lecture day
                                    if ((LocalDateTime.now().getHour() == 7 && LocalDateTime.now().getMinute() >= 30) || (LocalDateTime.now().getHour() == 8 && LocalDateTime.now().getMinute() <= 45)) {
                                        //check if the current time matches the lecture time
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "sunday9":
                                if (LocalDate.now().getDayOfWeek().name() == "SUNDAY") {
                                    if ((LocalDateTime.now().getHour() == 9) || (LocalDateTime.now().getHour() == 10 && LocalDateTime.now().getMinute() <= 15)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "sunday10":
                                if (LocalDate.now().getDayOfWeek().name() == "SUNDAY") {
                                    if (LocalDateTime.now().getHour() == 10 && LocalDateTime.now().getMinute() >= 30 || LocalDateTime.now().getHour() == 11 && LocalDateTime.now().getMinute() <= 45) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "sunday1":
                                if (LocalDate.now().getDayOfWeek().name() == "SUNDAY") {
                                    if ((LocalDateTime.now().getHour() == 13) || (LocalDateTime.now().getHour() == 14 && LocalDateTime.now().getMinute() <= 15)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "monday7":
                                if (LocalDate.now().getDayOfWeek().name() == "MONDAY") {
                                    if ((LocalDateTime.now().getHour() == 7 && LocalDateTime.now().getMinute() >= 30) || (LocalDateTime.now().getHour() == 8 && LocalDateTime.now().getMinute() <= 45)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "monday9":
                                if (LocalDate.now().getDayOfWeek().name() == "MONDAY") {
                                    if ((LocalDateTime.now().getHour() == 9) || (LocalDateTime.now().getHour() == 10 && LocalDateTime.now().getMinute() <= 15)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "monday10":
                                if (LocalDate.now().getDayOfWeek().name() == "MONDAY") {
                                    if (LocalDateTime.now().getHour() == 10 && LocalDateTime.now().getMinute() >= 30 || LocalDateTime.now().getHour() == 11 && LocalDateTime.now().getMinute() <= 45) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "monday1":
                                if (LocalDate.now().getDayOfWeek().name() == "MONDAY") {
                                    if ((LocalDateTime.now().getHour() == 13) || (LocalDateTime.now().getHour() == 14 && LocalDateTime.now().getMinute() <= 15)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "tueday7":
                                if (LocalDate.now().getDayOfWeek().name() == "TUESDAY") {
                                    if ((LocalDateTime.now().getHour() == 7 && LocalDateTime.now().getMinute() >= 30) || (LocalDateTime.now().getHour() == 8 && LocalDateTime.now().getMinute() <= 45)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "tueday9":
                                if (LocalDate.now().getDayOfWeek().name() == "TUESDAY") {
                                    if ((LocalDateTime.now().getHour() == 9) || (LocalDateTime.now().getHour() == 10 && LocalDateTime.now().getMinute() <= 15)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "tueday10":
                                if (LocalDate.now().getDayOfWeek().name() == "TUESDAY") {
                                    if (LocalDateTime.now().getHour() == 10 && LocalDateTime.now().getMinute() >= 30 || LocalDateTime.now().getHour() == 11 && LocalDateTime.now().getMinute() <= 45) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "tueday1":
                                if (LocalDate.now().getDayOfWeek().name() == "TUESDAY") {
                                    if ((LocalDateTime.now().getHour() == 13) || (LocalDateTime.now().getHour() == 14 && LocalDateTime.now().getMinute() <= 15)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "wenday7":
                                if (LocalDate.now().getDayOfWeek().name() == "WEDNESDAY") {
                                    if ((LocalDateTime.now().getHour() == 7 && LocalDateTime.now().getMinute() >= 30) || (LocalDateTime.now().getHour() == 8 && LocalDateTime.now().getMinute() <= 45)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "wenday9":
                                if (LocalDate.now().getDayOfWeek().name() == "WEDNESDAY") {
                                    if ((LocalDateTime.now().getHour() == 9) || (LocalDateTime.now().getHour() == 10 && LocalDateTime.now().getMinute() <= 15)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "wenday10":
                                if (LocalDate.now().getDayOfWeek().name() == "WEDNESDAY") {
                                    if (LocalDateTime.now().getHour() == 10 && LocalDateTime.now().getMinute() >= 30 || LocalDateTime.now().getHour() == 11 && LocalDateTime.now().getMinute() <= 45) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "wenday1":
                                if (LocalDate.now().getDayOfWeek().name() == "WEDNESDAY") {
                                    if ((LocalDateTime.now().getHour() == 13) || (LocalDateTime.now().getHour() == 14 && LocalDateTime.now().getMinute() <= 15)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "thrday7":
                                if (LocalDate.now().getDayOfWeek().name() == "THURSDAY") {
                                    if ((LocalDateTime.now().getHour() == 7 && LocalDateTime.now().getMinute() >= 30) || (LocalDateTime.now().getHour() == 8 && LocalDateTime.now().getMinute() <= 45)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "thrday9":
                                if (LocalDate.now().getDayOfWeek().name() == "THURSDAY") {
                                    if ((LocalDateTime.now().getHour() == 9) || (LocalDateTime.now().getHour() == 10 && LocalDateTime.now().getMinute() <= 15)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "thrday10":
                                if (LocalDate.now().getDayOfWeek().name() == "THURSDAY") {
                                    if (LocalDateTime.now().getHour() == 10 && LocalDateTime.now().getMinute() >= 30 || LocalDateTime.now().getHour() == 11 && LocalDateTime.now().getMinute() <= 45) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                            case "thrday1":
                                if (LocalDate.now().getDayOfWeek().name() == "THURSDAY") {
                                    if ((LocalDateTime.now().getHour() == 13) || (LocalDateTime.now().getHour() == 14 && LocalDateTime.now().getMinute() <= 15)) {
                                        status.setText("Occupied (Offline)");
                                    }
                                }
                                break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }
    }
}

