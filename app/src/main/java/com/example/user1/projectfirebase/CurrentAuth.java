package com.example.user1.projectfirebase;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzalt;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CurrentAuth extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth fireb;
    private Button btnLogOut;
    private TextView textView;
    private DatabaseReference databaseref;
    private EditText edtTextName, editTextAddress;
    private Button btnSave;
    /*** This was auto-generated to implement the App Indexing API.*/
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_auth);

        fireb = FirebaseAuth.getInstance();
        FirebaseUser user = fireb.getCurrentUser();
        if (fireb.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));
        }

       databaseref = FirebaseDatabase.getInstance().getReference();

         edtTextName = (EditText) findViewById(R.id.editName);
         editTextAddress = (EditText) findViewById(R.id.editAdress);

        btnSave = (Button) findViewById(R.id.btnSaveInfo);
        textView = (TextView) findViewById(R.id.textViewEmail);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);

        //display logged in User
        textView.setText("Welcome " + user.getEmail());

       //Setting the onclick listener to buttons
        btnLogOut.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
       //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void saveInformation() {
        String nam = edtTextName.getText().toString().trim();
        String add = editTextAddress.getText().toString().trim();

        ClientInformation client = new ClientInformation(nam,add);
        FirebaseUser user = fireb.getCurrentUser();
        databaseref.child(user.getUid()).setValue(client);
               //if(user == fireb.getCurrentUser()) {
                   Toast.makeText(this, "User information saved", Toast.LENGTH_SHORT).show();
               //} else
                  // Toast.makeText(this, "User Information was not Saved", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View view) {
        if (view == btnLogOut) {
           fireb.signOut();
            finish();
          startActivity(new Intent(this,Login.class));
          }
        if (view == btnSave) {
            saveInformation();
        }
    }

 }


