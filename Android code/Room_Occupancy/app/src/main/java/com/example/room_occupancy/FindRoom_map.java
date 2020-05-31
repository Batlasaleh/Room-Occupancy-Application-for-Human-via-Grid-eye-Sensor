package com.example.room_occupancy;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ZoomControls;

public class FindRoom_map extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageView home;
    private ImageView info;
    private ImageView mapImage;
    private Button list;
    private float natural_x,natural_y;
    //private Button map;
    private Button room1098;
    private Button room1078;
    private Button room2077;
    private Spinner floors;
    ZoomControls simpleZoomControls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_room_map);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        home=findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

        info=findViewById(R.id.infoButton);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfo();
            }
        });

        list=findViewById(R.id.listView2);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openList();
            }
        });

        mapImage=findViewById(R.id.mapImage);
        mapImage.setHorizontalScrollBarEnabled(true);
        mapImage.setVerticalScrollBarEnabled(true);
         natural_x = mapImage.getScaleX();
         natural_y = mapImage.getScaleY();

        floors=findViewById(R.id.floorList2);
        floors.setOnItemSelectedListener(this);

        room1098=findViewById(R.id.Room1098button);
        room1098.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRoom();
            }
        });

        room1078=findViewById(R.id.room1078button);
        room1078.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRoom2();
            }
        });

        room2077=findViewById(R.id.room2077button);
        room2077.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRoom3();
            }
        });

        simpleZoomControls=findViewById(R.id.simpleZoomControl);

        // perform setOnZoomInClickListener event on ZoomControls
        simpleZoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calculate current scale x and y value of ImageView
                float x = mapImage.getScaleX();
                float y = mapImage.getScaleY();
                // set increased value of scale x and y to perform zoom in functionality
                room1098.setVisibility(View.GONE);
                room1078.setVisibility(View.GONE);
                room2077.setVisibility(View.GONE);
                mapImage.setScaleX((float) (x + 1));
                mapImage.setScaleY((float) (y + 1));

                if(mapImage.getScaleX()==natural_x && mapImage.getScaleY()==natural_y){
                    if(mapImage.getDrawable().equals(R.drawable.groundfloor)){
                        room1098.setVisibility(View.VISIBLE);
                        room1098.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        room1078.setVisibility(View.VISIBLE);
                        room1078.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        room2077.setVisibility(View.GONE);
                    }
                    if(mapImage.getDrawable().equals(R.drawable.firstfloor)) {
                        room2077.setVisibility(View.VISIBLE);
                        room2077.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        room1098.setVisibility(View.GONE);
                        room1078.setVisibility(View.GONE);
                    }
                    if(mapImage.getDrawable().equals(R.drawable.secondfloor)){
                        room1098.setVisibility(View.GONE);
                        room1078.setVisibility(View.GONE);
                        room2077.setVisibility(View.GONE);
                    }
                }else {
                    room1098.setVisibility(View.GONE);
                    room1078.setVisibility(View.GONE);
                    room2077.setVisibility(View.GONE);
                }

                // display a toast to show Zoom In Message on Screen
                Toast.makeText(getApplicationContext(),"Zoom In",Toast.LENGTH_SHORT).show();

            }
        });
        // perform setOnZoomOutClickListener event on ZoomControls
        simpleZoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calculate current scale x and y value of ImageView
                float x = mapImage.getScaleX();
                float y = mapImage.getScaleY();
                // set decreased value of scale x and y to perform zoom out functionality
                mapImage.setScaleX((float) (x - 1));
                mapImage.setScaleY((float) (y - 1));

                if(natural_x<mapImage.getScaleX() && natural_y<mapImage.getScaleY()){
                    room1098.setVisibility(View.GONE);
                    room1078.setVisibility(View.GONE);
                    room2077.setVisibility(View.GONE);
                }
                else {
                    if(mapImage.getDrawable().equals(R.drawable.groundfloor)){
                        room1098.setVisibility(View.VISIBLE);
                        room1098.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        room1078.setVisibility(View.VISIBLE);
                        room1078.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        room2077.setVisibility(View.GONE);
                    }
                    if(mapImage.getDrawable().equals(R.drawable.firstfloor)) {
                        room2077.setVisibility(View.VISIBLE);
                        room2077.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        room1098.setVisibility(View.GONE);
                        room1078.setVisibility(View.GONE);
                    }
                    if(mapImage.getDrawable().equals(R.drawable.secondfloor)){
                        room1098.setVisibility(View.GONE);
                        room1078.setVisibility(View.GONE);
                        room2077.setVisibility(View.GONE);
                    }
                    mapImage.setScaleX(natural_x);
                    mapImage.setScaleY(natural_y);
                }

                // display a toast to show Zoom Out Message on Screen

                if(mapImage.getScaleX()==natural_x && mapImage.getScaleY()==natural_y){

                    if(floors.getSelectedItemPosition()==0){
                        room1098.setVisibility(View.VISIBLE);
                        room1098.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        room1078.setVisibility(View.VISIBLE);
                        room1078.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        room2077.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Zoom Out",Toast.LENGTH_SHORT).show();
                    }
                    if(floors.getSelectedItemPosition()==1) {
                        room2077.setVisibility(View.VISIBLE);
                        room2077.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        room1098.setVisibility(View.GONE);
                        room1078.setVisibility(View.GONE);
                    }
                    if(floors.getSelectedItemPosition()==2){
                        room1098.setVisibility(View.GONE);
                        room1078.setVisibility(View.GONE);
                        room2077.setVisibility(View.GONE);
                    }
                }
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

    public void openList(){
        Intent intent=new Intent(this,FindRoom.class);
        startActivity(intent);
    }

    /*public void openMap() {
        Intent intent = new Intent(this, FindRoom_map.class);
        startActivity(intent);
    }*/

    public void openRoom(){
        Intent intent=new Intent(this,RoomInfo.class);
        intent.putExtra("RoomInfoTitle","1077");
        startActivity(intent);
    }
    public void openRoom2(){
        Intent intent=new Intent(this,RoomInfo.class);
        intent.putExtra("RoomInfoTitle","1078");
        startActivity(intent);
    }
    public void openRoom3(){
        Intent intent=new Intent(this,RoomInfo.class);
        intent.putExtra("RoomInfoTitle","2077");
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getSelectedItemPosition()==0){
            mapImage.setScaleX(natural_x);
            mapImage.setScaleY(natural_y);
            mapImage.setImageResource(R.drawable.groundfloor);
            room1098.setVisibility(View.VISIBLE);
            room1098.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            room1078.setVisibility(View.VISIBLE);
            room1078.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            room2077.setVisibility(View.GONE);
        }if(parent.getSelectedItemPosition()==1) {
            mapImage.setScaleX(natural_x);
            mapImage.setScaleY(natural_y);
            mapImage.setImageResource(R.drawable.firstfloor);
            room2077.setVisibility(View.VISIBLE);
            room2077.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            room1098.setVisibility(View.GONE);
            room1078.setVisibility(View.GONE);
        }if(parent.getSelectedItemPosition()==2){
            mapImage.setScaleX(natural_x);
            mapImage.setScaleY(natural_y);
            mapImage.setImageResource(R.drawable.secondfloor);
            room1098.setVisibility(View.GONE);
            room1078.setVisibility(View.GONE);
            room2077.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
