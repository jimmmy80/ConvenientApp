package com.example.user1.projectfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity  {
    private Button btnRegister;
    private EditText edtEmail;
    private EditText editPassw;
    private TextView tvSign;
    private ProgressDialog progress;
    private FirebaseAuth fireAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        fireAuth = FirebaseAuth.getInstance();
       if(fireAuth.getCurrentUser() != null ){
              finish();
              startActivity(new Intent(getApplicationContext(),Login.class));
       }
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edtEmail = (EditText) findViewById(R.id.EDTEmail);
        editPassw = (EditText) findViewById(R.id.editPass);

        tvSign = (TextView) findViewById(R.id.textSignUp);
        progress = new ProgressDialog(this);

        tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
     }
   private  void  Register() {
       String emailadress = edtEmail.getText().toString().trim();
       String userpassword = editPassw.getText().toString().trim();

       if (TextUtils.isEmpty(emailadress)) {
           Toast.makeText(this, "Please enter you email ...", Toast.LENGTH_SHORT).show();
           return;
       }
       if (TextUtils.isEmpty(userpassword)) {
           Toast.makeText(this, "Please enter you password ...", Toast.LENGTH_SHORT).show();
           return;
       }
        //to show that the email and password are correct and show that the user is currently being registered
       progress.setMessage("Still completing...");
       progress.show();

       //if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
       //Log.d(TAG, "createAccount:" + email);
          fireAuth.createUserWithEmailAndPassword(emailadress, userpassword)
                   .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               //it means the user is Registered successfully and has logged in,a message should alert the user
                               finish();
                               startActivity(new Intent(getApplicationContext(),CurrentAuth.class));
                           } else {
                               Toast.makeText(MainActivity.this,"Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                           }
                          progress.dismiss();
                       }
                   });
            }
   }
