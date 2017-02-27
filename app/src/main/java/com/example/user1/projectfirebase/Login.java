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

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button btnSignUp;
    private EditText Email;
    private EditText Pass;

    private TextView textSignUp;
    private ProgressDialog progress;
    private FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        progress = new ProgressDialog(this);
        fireAuth = FirebaseAuth.getInstance();

             if(fireAuth.getCurrentUser() != null) {
                 //Current profile activity should start
               finish();
              startActivity(new Intent(getApplicationContext(),CurrentAuth.class));
                }
        btnSignUp = (Button) findViewById(R.id.btnSignIn);
        Email = (EditText) findViewById(R.id.edtSign_Email);
        Pass = (EditText) findViewById(R.id.editPassword);
        textSignUp = (TextView) findViewById(R.id.textSignIn);

        btnSignUp.setOnClickListener(this);
        textSignUp.setOnClickListener(this);

    }

    private void signUser() {
        String email = Email.getText().toString().trim();
        String password = Pass.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        //use the return to stop the function execution
        return;
    }

    progress.setMessage("Registering Author...");
        progress.show();


        fireAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress.dismiss();
                        if (task.isSuccessful()) {
                          startActivity(new Intent(getApplicationContext(),CurrentAuth.class));
                        }
                        else {
                           Toast.makeText(Login.this, "Please enter email", Toast.LENGTH_SHORT).show();
                        }
                     }
               });
    }
  @Override
    public void onClick(View view) {
        if (view == btnSignUp) {
            signUser();
        }
        if (view == textSignUp) {
            //make use of the ProgressDialog
          finish();
           startActivity(new Intent(this, MainActivity.class));
        }
    }
}