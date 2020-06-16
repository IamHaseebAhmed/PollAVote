package com.haseebahmedsaeed.pollavote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class createPolls extends AppCompatActivity {

    public FirebaseAuth mAuth = Adashboard.mAuth;

    // Widgets Declarations Here
    EditText cp_title, cp_opt1, cp_opt2, cp_opt3, cp_opt4;
    Button cp_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpolls);

        // Widgets Initializations Here
        cp_title = findViewById(R.id.cp_title);
        cp_opt1 = findViewById(R.id.cp_opt1);
        cp_opt2 = findViewById(R.id.cp_opt2);
        cp_opt3 = findViewById(R.id.cp_opt3);
        cp_opt4 = findViewById(R.id.cp_opt4);
        cp_create = findViewById(R.id.cp_create);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // When Create Polling Button will be pressed
        cp_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = cp_title.getText().toString();
                String op1 = cp_opt1.getText().toString();
                String op2 = cp_opt2.getText().toString();
                String op3 = cp_opt3.getText().toString();
                String op4 = cp_opt4.getText().toString();

                if (!title.isEmpty() && !op1.isEmpty() && !op2.isEmpty() && !op3.isEmpty() && !op4.isEmpty()){

                    String timestamp = getTimestamp();
                    Polls np = new Polls(timestamp, title, op1, op2, op3, op4);
                    database.getReference("Polls").push().setValue(np);
                    Toast.makeText(createPolls.this, "Success!", Toast.LENGTH_SHORT).show();

                    cp_title.setText(""); cp_opt1.setText(""); cp_opt2.setText(""); cp_opt3.setText(""); cp_opt4.setText("");
                }else{
                    Toast.makeText(createPolls.this, "Error: Cannot Create Polls!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getTimestamp(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }
}
