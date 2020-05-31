package com.example.room_occupancy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditAdmin extends AppCompatActivity implements View.OnClickListener {
    private Button cancel;
    private ImageView home;
    private ImageView info;
    private EditText email;
    private EditText password;
    private Button save;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        user=FirebaseAuth.getInstance().getCurrentUser();

        email=findViewById(R.id.emailtext);
        password=findViewById(R.id.newpasstext);
        save=findViewById(R.id.saveButton);

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
        email.setText(user.getEmail());

        save.setOnClickListener(this);
    }
    private void Dialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Password is changed successfully!");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Cancel();
                    }
                });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
    public void change(){
        String pass=password.getText().toString();
        if (TextUtils.isEmpty(pass)){
            //email or password is empty
            Toast.makeText(this,"Please enter the password",Toast.LENGTH_SHORT).show();
            //stop the function
            return;
        }
        progressDialog.setMessage("Updating...");
        progressDialog.show();
        if (user != null){

            user.updatePassword(password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Dialog();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(EditAdmin.this,"Try Again, make sure the password is complex",Toast.LENGTH_LONG).show();
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
        change();
    }
}
