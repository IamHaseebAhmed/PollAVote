package com.haseebahmedsaeed.pollavote.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.haseebahmedsaeed.pollavote.Adashboard;
import com.haseebahmedsaeed.pollavote.LoginActivity;
import com.haseebahmedsaeed.pollavote.R;

public class EDashboard extends AppCompatActivity {

    static FirebaseAuth mAuth = LoginActivity.mAuth;

    Button OpenPollsButton, UserProfileButton, AnnounceButton, SignOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edashboard);

        mAuth = FirebaseAuth.getInstance();

        OpenPollsButton = findViewById(R.id.OpenPollsButton);
        UserProfileButton = findViewById(R.id.UserProfileButton);
        AnnounceButton = findViewById(R.id.AnnounceButton);
        SignOutButton = findViewById(R.id.SignOutButton);


        OpenPollsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        UserProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        AnnounceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EDashboard.this, "Signing Out Successfully!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}
