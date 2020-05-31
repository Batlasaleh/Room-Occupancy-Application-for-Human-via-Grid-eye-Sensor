package com.example.room_occupancy;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class ViewLectures extends AppCompatActivity {
    private Button back;
    private ImageView home;
    private ImageView info;
    private CheckBox sun7,sun9,sun10,sun1;
    private CheckBox mon7,mon9,mon10,mon1;
    private CheckBox tus7,tus9,tus10,tus1;
    private CheckBox wen7,wen9,wen10,wen1;
    private CheckBox thu7,thu9,thu10,thu1;
    DatabaseReference reff;
    String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lectures);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        Intent intent=getIntent();
        num=intent.getStringExtra("number");
        reff= FirebaseDatabase.getInstance().getReference("lectures").child(num);

        sun7=findViewById(R.id.sun7);
        sun9=findViewById(R.id.sun9);
        sun10=findViewById(R.id.sun10);
        sun1=findViewById(R.id.sun1);
        mon7=findViewById(R.id.mon7);
        mon9=findViewById(R.id.mon9);
        mon10=findViewById(R.id.mon10);
        mon1=findViewById(R.id.mon1);
        mon7=findViewById(R.id.mon7);
        tus7=findViewById(R.id.tue7);
        tus9=findViewById(R.id.tue9);
        tus10=findViewById(R.id.tue10);
        tus1=findViewById(R.id.tue1);
        wen7=findViewById(R.id.wed7);
        wen9=findViewById(R.id.wed9);
        wen10 =findViewById(R.id.wed10);
        wen1=findViewById(R.id.wed1);
        thu7=findViewById(R.id.thr7);
        thu9=findViewById(R.id.thr9);
        thu10=findViewById(R.id.thr10);
        thu1=findViewById(R.id.thr1);

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

        back=findViewById(R.id.BackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRoomInfo();
            }
        });

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
                            tus7.setChecked(true);
                            break;
                        case "tueday9":
                            tus9.setChecked(true);
                            break;
                        case "tueday10":
                            tus10.setChecked(true);
                            break;
                        case "tueday1":
                            tus1.setChecked(true);
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
                            thu7.setChecked(true);
                            break;
                        case "thrday9":
                            thu9.setChecked(true);
                            break;
                        case "thrday10":
                            thu10.setChecked(true);
                            break;
                        case "thrday1":
                            thu1.setChecked(true);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
    public void openRoomInfo(){
        finish();
    }
}
