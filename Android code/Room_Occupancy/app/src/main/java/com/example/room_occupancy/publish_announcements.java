package com.example.room_occupancy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class publish_announcements extends AppCompatActivity {
    private ImageView home;
    private ImageView info;
    private Spinner type;
    private TextView title;
    private TextView message;
    private Button send;
    private Button cancel;
    DatabaseReference reff;
    AddAnnouncementsDB announcements;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_announcements);

        firebaseAuth = FirebaseAuth.getInstance();
        type=findViewById(R.id.announcementsList);
        title=findViewById(R.id.announcementTitle);
        message=findViewById(R.id.announcementText);
        send=findViewById(R.id.sendbutton2);
        announcements=new AddAnnouncementsDB();
        reff= FirebaseDatabase.getInstance().getReference("announcements");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t=type.getSelectedItem().toString();
                String ti=title.getText().toString().trim();
                String m=message.getText().toString().trim();

                if (TextUtils.isEmpty(ti)||TextUtils.isEmpty(m)){
                    //is empty
                    Toast.makeText(publish_announcements.this,"Please Fill the Field",Toast.LENGTH_SHORT).show();
                    //stop the function
                    return;
                }
                announcements.setAnnouncementType(t);
                announcements.setTitle(ti);
                announcements.setMessage(m);

                String id=reff.push().getKey();
                announcements.setId(id);
                reff.child(id).setValue(announcements);
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
                openCancel();
            }
        });
    }

    private void Dialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Announcement sent successfully!");
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

    private void openCancel() {
        Intent intent=new Intent(this,AdminServices.class);
        startActivity(intent);
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
