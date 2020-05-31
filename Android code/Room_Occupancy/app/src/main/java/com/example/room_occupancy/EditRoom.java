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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditRoom extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button cancel;
    private Button lectures;
    private ImageView home;
    private ImageView info;
    private Spinner roomSize;
    private Spinner roomNumber;
    private Spinner facilities;
    private Spinner floors;
    private TextView chairs;
    DatabaseReference reff;
    private String r;
    private String f;
    private String s;
    private String c;
    private String fl;
    Query query;
    private Button save;
    addroomDB addroom;
    ArrayList<String> rooms;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    String num;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        facilities=findViewById(R.id.facilitiesList);
        floors=findViewById(R.id.floorList);

        save=findViewById(R.id.SaveButton);
        chairs=findViewById(R.id.chairsText);

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

        cancel=findViewById(R.id.cancel2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel();
            }
        });

        lectures=findViewById(R.id.LecturesButton);
        lectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLectures();
            }
        });

        roomSize=findViewById(R.id.roomSizeList);
        roomSize.setOnItemSelectedListener(this);

        facilities=findViewById(R.id.facilitiesList);
        facilities.setOnItemSelectedListener(this);

        floors=findViewById(R.id.floorList);
        floors.setOnItemSelectedListener(this);

        roomNumber=findViewById(R.id.roomsNumberList);
        roomNumber.setOnItemSelectedListener(this);
        addroom=new addroomDB();
        reff= FirebaseDatabase.getInstance().getReference("room");
        rooms=new ArrayList<>();
        adapter= new ArrayAdapter<String>(EditRoom.this,android.R.layout.simple_spinner_dropdown_item,rooms);

        roomNumber.setAdapter(adapter);
        reciver();
        roomNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                num= roomNumber.getSelectedItem().toString();
                query = FirebaseDatabase.getInstance().getReference("room").orderByChild("sensorID").equalTo(num);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()){
                            Dialog();
                        }
                        for (DataSnapshot ds : dataSnapshot.getChildren()){

                            c=ds.child("chairs").getValue().toString();
                            f=ds.child("facilities").getValue().toString();
                            s=ds.child("roomSize").getValue().toString();
                            fl=ds.child("").getValue().toString();

                            ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(EditRoom.this, R.array.FloorItemsEdit, android.R.layout.simple_spinner_item);
                            adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            floors.setAdapter(adapter0);
                            if (fl != null) {
                                int spinnerPosition = adapter0.getPosition(fl);
                                floors.setSelection(spinnerPosition);
                            }
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EditRoom.this, R.array.RoomSizeItemsEdit, android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            roomSize.setAdapter(adapter);
                            if (s != null) {
                                int spinnerPosition = adapter.getPosition(s);
                                roomSize.setSelection(spinnerPosition);
                            }
                            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(EditRoom.this, R.array.FacilitiesItemsEdit, android.R.layout.simple_spinner_item);
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            facilities.setAdapter(adapter2);
                            if (f != null) {
                                int spinnerPosition = adapter2.getPosition(f);
                                facilities.setSelection(spinnerPosition);
                            }

                            chairs.setText(c);

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flors=floors.getSelectedItem().toString();
                String facili=facilities.getSelectedItem().toString();
                String size=roomSize.getSelectedItem().toString();
                String charir=chairs.getText().toString().trim();

                addroom.setChairs(charir);
                addroom.setFacilities(facili);
                addroom.setFloor(flors);
                addroom.setRoomnum(num);
                addroom.setRoomSize(size);
                addroom.setSensorID(num);

                reff.child(num).setValue(addroom);
                Dialog();
            }
        });
    }
    private void Dialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Room is updated successfully!");
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

    public void Cancel(){
        Intent intent=new Intent(this,ModifyRooms.class);
        startActivity(intent);
    }

    public void OpenLectures(){
        Intent intent=new Intent(this,LecturesTable.class);
        intent.putExtra("roomNum",num);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
