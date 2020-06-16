package com.haseebahmedsaeed.pollavote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Adashboard extends AppCompatActivity {

    public static FirebaseAuth mAuth;

    // Widgets Declaration Here
    Button cpButton, epButton, rpButton, lvButton, aSignOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adashboard);

        mAuth = FirebaseAuth.getInstance();

        // Widgets Initializations Here
        cpButton = findViewById(R.id.cpButton);
        epButton = findViewById(R.id.epButton);
        rpButton = findViewById(R.id.rpButton);
        lvButton = findViewById(R.id.lvButton);
        aSignOutButton = findViewById(R.id.aSignOutButton);

        cpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Adashboard.this, createPolls.class));
            }
        });

        epButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Adashboard.this, editPolls.class));
            }
        });

        rpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Adashboard.this, reportGenerate.class));
            }
        });

        lvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Adashboard.this, ListOfVoters.class));
            }
        });

        aSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Adashboard.this, "Signing Out Successfully!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}
