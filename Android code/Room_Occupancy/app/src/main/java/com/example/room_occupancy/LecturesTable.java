package com.example.room_occupancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class LecturesTable extends AppCompatActivity implements View.OnClickListener {
    private ImageView home;
    private ImageView info;
    private CheckBox sun7, sun9, sun10, sun1;
    private CheckBox mon7, mon9, mon10, mon1;
    private CheckBox tue7, tue9, tue10, tue1;
    private CheckBox wen7, wen9, wen10, wen1;
    private CheckBox thr7, thr9, thr10, thr1;
    private Button save;
    private Button cancel;
    private String roomnum;
    AddLecturesDB add;
    DatabaseReference reff;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures_table);

        firebaseAuth = FirebaseAuth.getInstance();
        sun7=findViewById(R.id.sun7);
        sun9=findViewById(R.id.sun9);
        sun10=findViewById(R.id.sun10);
        sun1=findViewById(R.id.sun1);
        mon7=findViewById(R.id.mon7);
        mon9=findViewById(R.id.mon9);
        mon10=findViewById(R.id.mon10);
        mon1=findViewById(R.id.mon1);
        tue7=findViewById(R.id.tue7);
        tue9=findViewById(R.id.tue9);
        tue10=findViewById(R.id.tue10);
        tue1=findViewById(R.id.tue1);
        wen7=findViewById(R.id.wed7);
        wen9=findViewById(R.id.wed9);
        wen10 =findViewById(R.id.wed10);
        wen1=findViewById(R.id.wed1);
        thr7=findViewById(R.id.thr7);
        thr9=findViewById(R.id.thr9);
        thr10=findViewById(R.id.thr10);
        thr1=findViewById(R.id.thr1);

        save=findViewById(R.id.saveButton2);
        cancel=findViewById(R.id.cancelbutton2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel();
            }
        });

        Intent intent=getIntent();
        roomnum=intent.getStringExtra("roomNum");

        add= new AddLecturesDB();

        reff=FirebaseDatabase.getInstance().getReference("lectures");

        save.setOnClickListener(this);

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

        reff= FirebaseDatabase.getInstance().getReference("lectures").child(roomnum);
        reff.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> times=dataSnapshot.getChildren().iterator();
                while (times.hasNext()){
                    DataSnapshot time=times.next();
                    String t=time.getKey();

                    switch(t){
                        case "sunday7":
                            sun7.setChecked(true);
                            break;
                        case "sunday9":
                            sun9.setChecked(true);
                            break;
                        case "sunday10":
                            sun10.setChecked(true);
                            break;
                        case "sunday1":
                            sun1.setChecked(true);
                            break;
                        case "monday7":
                            mon7.setChecked(true);
                            break;
                        case "monday9":
                            mon9.setChecked(true);
                            break;
                        case "monday10":
                            mon10.setChecked(true);
                            break;
                        case "monday1":
                            mon1.setChecked(true);
                            break;
                        case "tueday7":
                            tue7.setChecked(true);
                            break;
                        case "tueday9":
                            tue9.setChecked(true);
                            break;
                        case "tueday10":
                            tue10.setChecked(true);
                            break;
                        case "tueday1":
                            tue1.setChecked(true);
                            break;
                        case "wenday7":
                            wen7.setChecked(true);
                            break;
                        case "wenday9":
                            wen9.setChecked(true);
                            break;
                        case "wenday10":
                            wen10.setChecked(true);
                            break;
                        case "wenday1":
                            wen1.setChecked(true);
                            break;
                        case "thrday7":
                            thr7.setChecked(true);
                            break;
                        case "thrday9":
                            thr9.setChecked(true);
                            break;
                        case "thrday10":
                            thr10.setChecked(true);
                            break;
                        case "thrday1":
                            thr1.setChecked(true);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private  void lecture(){
            if (sun7.isChecked()) {
                add.setSunday7("SUN, 7:30");
                reff.child(roomnum).setValue(add);
                 ;
            }

            if (sun9.isChecked()) {
                add.setSunday9("SUN, 9:00");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (sun10.isChecked()) {
                add.setSunday10("SUN, 10:30");
                reff.child(roomnum).setValue(add);
                 ;

            }
            if (sun1.isChecked()) {
                add.setSunday1("SUN, 1:00");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (mon7.isChecked()) {
                add.setMonday7("MON, 7:30");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (mon9.isChecked()) {
                add.setMonday9("MON, 9:00");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (mon10.isChecked()) {
                add.setMonday10("MON, 10:30");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (mon1.isChecked()) {
                add.setMonday1("MON, 1:00");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (tue7.isChecked()) {
                add.setTueday7("TUE, 7:30");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (tue9.isChecked()) {
                add.setTueday9("TUE, 9:00");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (tue10.isChecked()) {
                add.setTueday10("TUE, 10:30");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (tue1.isChecked()) {
                add.setTueday1("TUE, 1:00");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (wen7.isChecked()) {
                add.setWenday7("WEN, 7:30");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (wen9.isChecked()) {
                add.setWenday9("WEN, 9:00");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (wen10.isChecked()) {
                add.setWenday10("WEN, 10:30");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (wen1.isChecked()) {
                add.setWenday1("WEN, 1:00");
                reff.child(roomnum).setValue(add);
                 ;
            }
            if (thr7.isChecked()) {
                add.setThrday7("THR, 7:30");
                reff.child(roomnum).setValue(add);
                
            }
            if (thr9.isChecked()) {
                add.setThrday9("THR, 9:00");
                reff.child(roomnum).setValue(add);
                
            }
            if (thr10.isChecked()) {
                add.setThrday10("THR, 10:30");
                reff.child(roomnum).setValue(add);
            }
            if (thr1.isChecked()) {
                add.setThrday1("THR, 1:00");
                reff.child(roomnum).setValue(add);
            }
            Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show();
            finish();
        }

    public void Cancel(){
        finish();
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

    @Override
    public void onClick(View v) {
        lecture();
    }
}
