package com.example.room_occupancy;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contact extends AppCompatActivity {
    private Button send;
    private Button cancel;
    private TextView title;
    private TextView message;
    private ImageView home;
    private ImageView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        send=(Button)findViewById(R.id.sendbutton);
        title=(EditText)findViewById(R.id.Messagetitle);
        message=(EditText)findViewById(R.id.message);
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

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmail();
            }
        });

        cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCancel();
            }
        });
    }
    public void sendmail(){
        if (message.getText().toString().length() == 0|| title.getText().toString().length() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please fill all the fields correctly!",Toast.LENGTH_SHORT);
            View toastView = toast.getView();
            toastView.setBackgroundResource(R.color.colorAccent);
            toast.show();
        }else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "example@gmail.com" });
            intent.putExtra(Intent.EXTRA_SUBJECT, title.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
            intent.setType("message/rfc822");
            startActivity(intent);


        }

    }

    public void OpenCancel(){
        Intent intent=new Intent(this,MainActivity.class);
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
}
