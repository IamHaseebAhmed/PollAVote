package com.haseebahmedsaeed.pollavote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Database Instance
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Widgets Declaration and Initializations
        final EditText su_username, su_email, su_phone, su_password, su_gcode;
        final Button su_Button;

        su_username = (EditText) findViewById(R.id.su_username);
        su_email = (EditText) findViewById(R.id.su_email);
        su_phone = (EditText) findViewById(R.id.su_phone);
        su_password = (EditText) findViewById(R.id.su_password);
        su_gcode = (EditText) findViewById(R.id.su_gcode);
        su_Button = findViewById(R.id.su_Button);

        // If Sign Up Button is clicked
        su_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = (String) su_username.getText().toString().trim();
                final String email = (String) su_email.getText().toString().trim();
                final String phone = (String) su_phone.getText().toString().trim();
                final String password = (String) su_password.getText().toString().trim();
                final String gcode = (String) su_gcode.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                
                                if (task.isSuccessful()){
                                    Map<String, String> userData = new HashMap<String, String>();

                                    userData.put("name", name);
                                    userData.put("email", email);
                                    userData.put("phone", phone);
                                    userData.put("password", password);
                                    userData.put("gcode", gcode);

                                    FirebaseDatabase.getInstance().getReference("Employee").child(FirebaseAuth.getInstance()
                                            .getCurrentUser().getUid()).setValue(userData);

                                    Toast.makeText(signup.this, "Sign Up Success!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }
                                else{
                                    Toast.makeText(signup.this, "Error: Sign Up Failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }
}
