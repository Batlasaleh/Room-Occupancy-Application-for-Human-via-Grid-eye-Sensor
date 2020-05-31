package com.example.room_occupancy;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportProblem extends AppCompatActivity {
    private ImageView home;
    private ImageView info;
    private Button cancel;
    private Spinner chose;
    private EditText roomnum;
    private EditText problem;
    private Button send;
    DatabaseReference reff;
    sendproblemDB prosend;
    String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        Intent secondIntent = getIntent();
        num=secondIntent.getStringExtra("number");

        home=findViewById(R.id.homeButton2);
        chose=findViewById(R.id.ProblemsList);
        roomnum=findViewById(R.id.RoomNumber);
        problem=findViewById(R.id.problemText);
        send=findViewById(R.id.sendbutton2);
        roomnum.setEnabled(false);
        roomnum.setText(num);
        prosend = new sendproblemDB();
        reff= FirebaseDatabase.getInstance().getReference().child("problem");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type=chose.getSelectedItem().toString();
                String prob=problem.getText().toString().trim();

                if (TextUtils.isEmpty(prob)){
                    //is empty
                    Toast.makeText(ReportProblem.this,"Please Fill the Field",Toast.LENGTH_SHORT).show();
                    //stop the function
                    return;
                }
                // if ok
                prosend.setType(type);
                prosend.setRoomnum(num);
                prosend.setProblem(prob);

                String id=reff.push().getKey();
                prosend.setId(id);
                reff.child(id).setValue(prosend);
                Dialog();
            }
        });
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
                openCancel();
            }
        });
    }

    private void Dialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Report sent successfully!");
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

    public void openHome(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openInfo(){
        Intent intent=new Intent(this,Info.class);
        startActivity(intent);
    }

    public void openCancel(){
        /*Intent intent=new Intent(this,RoomInfo.class);
        startActivity(intent);*/
        finish();
    }

}
