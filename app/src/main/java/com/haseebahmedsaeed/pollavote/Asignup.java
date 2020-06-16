package com.haseebahmedsaeed.pollavote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Asignup extends AppCompatActivity {

    private String groupcode = "";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignup);

        // Database Instance
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

        // Widgets Declaration and Initializations
        final EditText sa_username, sa_email, sa_phone, sa_password;
        final Button scodeGen_Button, sa_Button;
        final TextView scodeGen_TV;

        sa_username = findViewById(R.id.sa_username);
        sa_email = findViewById(R.id.sa_email);
        sa_phone = findViewById(R.id.sa_phone);
        sa_password = findViewById(R.id.sa_password);

        scodeGen_Button = findViewById(R.id.scodeGen_Button);
        sa_Button = findViewById(R.id.sa_Button);

        scodeGen_TV = findViewById(R.id.scodeGen_TV);

        // When Admin Register Button is Clicked
        sa_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = (String) sa_username.getText().toString().trim();
                final String email = (String) sa_email.getText().toString().trim();
                final String phone = (String) sa_phone.getText().toString().trim();
                final String password = (String) sa_password.getText().toString().trim();
                final String scode = groupcode;

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Asignup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Map<String, String> userData = new HashMap<String, String>();

                                    userData.put("name", name);
                                    userData.put("email", email);
                                    userData.put("phone", phone);
                                    userData.put("password", password);
                                    userData.put("gcode", scode);

                                    FirebaseDatabase.getInstance().getReference("Admin").child(FirebaseAuth.getInstance()
                                            .getCurrentUser().getUid()).setValue(userData);

                                    Toast.makeText(Asignup.this, "Sign Up Success!", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                } else {
                                    Toast.makeText(Asignup.this, "Error: Sign Up Failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // When Secret Group Code Button is Clicked
        scodeGen_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scode = scodeGenerator();

                groupcode = scode;
                scodeGen_TV.setText(scode);
                scodeGen_Button.setEnabled(false);
                scodeGen_Button.setBackgroundColor(getResources().getColor(R.color.DisabledButton));
                return;
            }
        });
    }

    public String scodeGenerator() {

        String scode = "";

        for (int i = 0; i < 4; i++) {
            scode += (char) ('A' + Math.random() * ('Z' - 'A' + 1));
        }
        for (int i = 0; i < 4; i++) {
            scode += (char) ('0' + Math.random() * ('9' - '0' + 1));
        }
        return scode;
    }
}
