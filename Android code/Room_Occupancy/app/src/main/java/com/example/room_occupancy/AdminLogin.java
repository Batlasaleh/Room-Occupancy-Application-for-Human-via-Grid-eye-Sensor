package com.example.room_occupancy;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener {
        private Button button1;
        private Button cancel;
        private TextView useremail;
        private TextView pass;
        private ImageView home;
        private ImageView info;
        private ProgressDialog progressDialog;
        private FirebaseAuth firebaseAuth;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_login);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);

            progressDialog = new ProgressDialog(this);
            firebaseAuth=FirebaseAuth.getInstance();

            useremail=findViewById(R.id.loginemail);
            pass=findViewById(R.id.loginPassword);

            button1=findViewById(R.id.findRoomButton);
            button1.setOnClickListener(this);

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
                    openHome();
                }
            });



        }
        private void openAdminServices(){
            String email=useremail.getText().toString().trim();
            String password=pass.getText().toString().trim();
            if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                //email or password is empty
                Toast.makeText(this,"Please enter the email/password",Toast.LENGTH_SHORT).show();
                //stop the function
                return;
            }
            //if ok
            progressDialog.setMessage("Login...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()){
                                finish();
                                Intent intent=new Intent(AdminLogin.this,AdminServices.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(AdminLogin.this,"Try Again",Toast.LENGTH_SHORT).show();
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


    @Override
    public void onClick(View v) {
        openAdminServices();
    }
}
