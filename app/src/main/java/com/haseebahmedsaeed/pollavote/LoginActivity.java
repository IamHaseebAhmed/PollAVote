package com.haseebahmedsaeed.pollavote;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haseebahmedsaeed.pollavote.Employee.EDashboard;

public class LoginActivity extends AppCompatActivity {

    public static FirebaseAuth mAuth;
    DatabaseReference database;

    EditText l_email, l_password;
    Button l_button;
    RadioGroup radioGroup;
    RadioButton rd_admin, rd_employee;
    TextView l_signup, l_asignup;
    ProgressBar loadingProgress;

    String RadioButtomChoice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        radioGroup = findViewById(R.id.radioGroup);

        // Initialization of all Widgets
        l_email = (EditText) findViewById(R.id.l_email);
        l_password = (EditText) findViewById(R.id.l_password);
        l_button = (Button) findViewById(R.id.l_button);
        l_signup = (TextView) findViewById(R.id.l_signup);
        l_asignup = (TextView) findViewById(R.id.l_asignup);
        rd_admin = (RadioButton) findViewById(R.id.rd_admin);
        rd_employee = (RadioButton) findViewById(R.id.rd_employee);
        loadingProgress = findViewById(R.id.loadingProgress);

        // When Radio  Button will be clicked!
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rd_admin:
                        RadioButtomChoice = "Admin";
                        break;
                    case R.id.rd_employee:
                        RadioButtomChoice = "Empoloyee";
                        break;
                    default:
                        Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // If Login Button is clicked
        l_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailuser = l_email.getText().toString();
                final String password = l_password.getText().toString();
                loadingProgress.setVisibility(View.VISIBLE);
                userLogin(emailuser, password);
            }
        });

        // If Sign Up Button is clicked
        l_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, signup.class);
                startActivity(intent);
            }
        });


        // If Admin Sign Up Button is clicked
        l_asignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Asignup.class);
                startActivity(intent);
            }
        });
    }

    public void userLogin(final String username, final String password) {

        // Firebase Database Root Reference
        database = FirebaseDatabase.getInstance().getReference();

        if (RadioButtomChoice.equals("Admin")) {
            DatabaseReference myRef = database.child("Admin");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        String uname = data.child("email").getValue(String.class);
                        String upass = data.child("password").getValue(String.class);

                        if (uname.equals(username) && upass.equals(password)) {
                            Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Adashboard.class));
                            loadingProgress.setVisibility(View.INVISIBLE);
                            return;
                        }
                    }
                    Toast.makeText(LoginActivity.this, "No such User found in database!", Toast.LENGTH_SHORT).show();
                    loadingProgress.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(DatabaseError error) {}
            });
        } else {
            DatabaseReference myRef = database.child("Employee");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        String uname = data.child("email").getValue(String.class);
                        String upass = data.child("password").getValue(String.class);

                        if (uname.equals(username) && upass.equals(password)) {
                            Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), EDashboard.class));
                            loadingProgress.setVisibility(View.INVISIBLE);
                            return;
                        }
                    }
                    Toast.makeText(LoginActivity.this, "No such User found in database!", Toast.LENGTH_SHORT).show();
                    loadingProgress.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(DatabaseError error) {}
            });
        }
    }
}
