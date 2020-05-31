package com.example.room_occupancy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewAdmin extends AppCompatActivity implements View.OnClickListener {
    private Button cancel;
    private ImageView home;
    private ImageView info;
    private EditText newadmin;
    private EditText pass;
    private Button add;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_admin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        home = findViewById(R.id.homeButton2);
        newadmin = findViewById(R.id.loginemail2);
        pass = findViewById(R.id.loginPassword2);
        add = findViewById(R.id.addButton);


        add.setOnClickListener(this);

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

        cancel = findViewById(R.id.cancelbutton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel();
            }
        });
    }

    private void Dialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Admin is added successfully!");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        openHome();
                    }
                });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }


    public void openHome() {
        firebaseAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openInfo() {
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
    }

    public void Cancel() {
        Intent intent = new Intent(this, AdminInfo.class);
        startActivity(intent);
    }

    private void register() {
        String email=newadmin.getText().toString().trim();
        String password=pass.getText().toString().trim();

        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            //email or password is empty
            Toast.makeText(this,"Please enter the email/password",Toast.LENGTH_SHORT).show();
            //stop the function
            return;
        }
        //if ok
        progressDialog.setMessage("Registration User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            firebaseAuth.signOut();
                            Dialog();}
                        else{
                            Toast.makeText(AddNewAdmin.this,"Try Again, make sure the password is complex/ the email is not repeated",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }



    @Override
    public void onClick(View v) {
        register();
    }
}
