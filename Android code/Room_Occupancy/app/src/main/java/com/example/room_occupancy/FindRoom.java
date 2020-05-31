package com.example.room_occupancy;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class FindRoom extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private ImageView home;
    private ImageView info;
    private Button search;
    //private Button list;
    private Button map;
    private Spinner floors;
    private Spinner corridor;
    private Spinner rooms;
    private TextView title;
    private String roomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_room);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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

        search=findViewById(R.id.SearchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRoom();
            }
        });

        //list=findViewById(R.id.listView);
        /*list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openList();
            }
        });*/

        map=findViewById(R.id.mapView);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

        floors=findViewById(R.id.floorList);
        floors.setOnItemSelectedListener(this);

        corridor=findViewById(R.id.corridorList);
        corridor.setOnItemSelectedListener(this);

        rooms=findViewById(R.id.roomList);
        rooms.setOnItemSelectedListener(this);

        title=findViewById(R.id.RoomInfoTitle);
    }

    public void openHome(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openInfo(){
        Intent intent=new Intent(this,Info.class);
        startActivity(intent);
    }

    public void openRoom(){
        Intent intent=new Intent(this,RoomInfo.class);
        intent.putExtra("RoomInfoTitle",roomNumber);
        startActivity(intent);
    }
    /*public void openList(){
        Intent intent=new Intent(this,FindRoom.class);
        startActivity(intent);
    }*/

    public void openMap(){
        Intent intent=new Intent(this,FindRoom_map.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.equals(floors)){
            if(parent== floors&& parent.getSelectedItemPosition()==0){
                ArrayAdapter<String> GroundCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.GroundCorridorItems));
                corridor.setAdapter(GroundCorridorItems);
            }
            if(parent== floors&& parent.getSelectedItemPosition()==1){
                ArrayAdapter<String> FirstCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.FirstCorridorItems));
                corridor.setAdapter(FirstCorridorItems);
            }
            if(parent== floors&& parent.getSelectedItemPosition()==2){
                ArrayAdapter<String> SecondCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.SecondCorridorItems));
                corridor.setAdapter(SecondCorridorItems);
            }
        }
        if(parent.equals(corridor)){
            //Ground floor and different corridors
            if(parent.getSelectedItemPosition()==0 && floors.getSelectedItemPosition()==0){
                ArrayAdapter<String> GroundCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.G_seventhItems));
                rooms.setAdapter(GroundCorridorItems);
            }if(parent.getSelectedItemPosition()==1 && floors.getSelectedItemPosition()==0){
                ArrayAdapter<String> GroundCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.G_eighthItems));
                rooms.setAdapter(GroundCorridorItems);
            }if(parent.getSelectedItemPosition()==2 && floors.getSelectedItemPosition()==0){
                ArrayAdapter<String> GroundCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.G_ninthItems));
                rooms.setAdapter(GroundCorridorItems);
            }

            //First floor and different corridors
            if(parent.getSelectedItemPosition()==0 && floors.getSelectedItemPosition()==1){
                ArrayAdapter<String> GroundCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.F_seventhItems));
                rooms.setAdapter(GroundCorridorItems);
            }if(parent.getSelectedItemPosition()==1 && floors.getSelectedItemPosition()==1){
                ArrayAdapter<String> GroundCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.F_eighthItems));
                rooms.setAdapter(GroundCorridorItems);
            }if(parent.getSelectedItemPosition()==2 && floors.getSelectedItemPosition()==1){
                ArrayAdapter<String> GroundCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.F_ninthItems));
                rooms.setAdapter(GroundCorridorItems);
            }

            //Second floor and different corridors
            if(parent.getSelectedItemPosition()==0 && floors.getSelectedItemPosition()==2){
                ArrayAdapter<String> GroundCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.S_seventhItems));
                rooms.setAdapter(GroundCorridorItems);
            }if(parent.getSelectedItemPosition()==1 && floors.getSelectedItemPosition()==2){
                ArrayAdapter<String> GroundCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.S_eighthItems));
                rooms.setAdapter(GroundCorridorItems);
            }if(parent.getSelectedItemPosition()==2 && floors.getSelectedItemPosition()==2){
                ArrayAdapter<String> GroundCorridorItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.S_ninthItems));
                rooms.setAdapter(GroundCorridorItems);
            }

        }
        if(parent.equals(rooms)){
            roomNumber=rooms.getSelectedItem().toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
