package com.example.room_occupancy;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteAdmin extends AppCompatActivity implements View.OnClickListener{
    private Button cancel;
    private ImageView home;
    private ImageView info;
    private EditText admin;
    private EditText pass;
    private Button delete;
    FirebaseUser user;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_admin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        user= FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);

        admin=findViewById(R.id.emailtext2);
        pass=findViewById(R.id.pwtext2);
        delete=findViewById(R.id.deleteButton);

        admin.setText(user.getEmail());

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

        cancel=findViewById(R.id.cancelbutton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel();
            }
        });
        delete.setOnClickListener(this);
    }

    private void deleteadmin() {
        String password=pass.getText().toString();
        if (TextUtils.isEmpty(password)){
            //email or password is empty
            Toast.makeText(this,"Please enter the password",Toast.LENGTH_SHORT).show();
            //stop the function
            return;
        }
        progressDialog.setMessage("Deleting...");
        progressDialog.show();
        if(user != null){
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(DeleteAdmin.this, "Account is deleted", Toast.LENGTH_SHORT).show();
                        openHome();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(DeleteAdmin.this,"Try Again",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void openHome(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openInfo(){
        Intent intent=new Intent(this,Info.class);
        startActivity(intent);
    }
    public void Cancel(){
        Intent intent=new Intent(this,AdminInfo.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        deleteadmin();
    }
}
