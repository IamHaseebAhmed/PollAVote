package com.haseebahmedsaeed.pollavote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editPolls extends AppCompatActivity {

    public FirebaseAuth mAuth = Adashboard.mAuth;

    public final String TAG="";
    public static String poll_id="";

    DatabaseReference database;

    // Widgets Declarations Here
    EditText cp_title, cp_opt1, cp_opt2, cp_opt3, cp_opt4;
    Button ed_poll;
    ProgressBar editProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_polls);

        // Widgets Initializations Here
        cp_title = findViewById(R.id.cp_title);
        cp_opt1 = findViewById(R.id.cp_opt1);
        cp_opt2 = findViewById(R.id.cp_opt2);
        cp_opt3 = findViewById(R.id.cp_opt3);
        cp_opt4 = findViewById(R.id.cp_opt4);
        ed_poll = findViewById(R.id.ed_poll);
        editProgressBar = findViewById(R.id.editProgressBar);

        mAuth = FirebaseAuth.getInstance();

        // Will Retrive Current Values from Firebase DB
        editProgressBar.setVisibility(View.VISIBLE);
        getCurrentValuesFromFirebase();

        ed_poll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewValuesInFirebase();
                Toast.makeText(editPolls.this, "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCurrentValuesFromFirebase(){

        // Firebase Database Root Reference
        database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Polls");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()){

                    String ptitle = data.child("title").getValue(String.class);
                    String op1 = data.child("op1").getValue(String.class);
                    String op2 = data.child("op2").getValue(String.class);
                    String op3 = data.child("op3").getValue(String.class);
                    String op4 = data.child("op4").getValue(String.class);

                    if (!ptitle.equals("") && !op1.equals("") && !op2.equals("") && !op3.equals("") && !op4.equals("")){
                        poll_id = data.getKey();
                        cp_title.setText(ptitle);
                        cp_opt1.setText(op1);
                        cp_opt2.setText(op2);
                        cp_opt3.setText(op3);
                        cp_opt4.setText(op4);
                        editProgressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(editPolls.this, "Error: Failed to retrive current values!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveNewValuesInFirebase(){

        // Firebase Database Root Reference
        database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Polls/"+poll_id);

        myRef.child("title").setValue(cp_title.getText().toString());
        myRef.child("op1").setValue(cp_opt1.getText().toString());
        myRef.child("op2").setValue(cp_opt2.getText().toString());
        myRef.child("op3").setValue(cp_opt3.getText().toString());
        myRef.child("op4").setValue(cp_opt4.getText().toString());
    }
}
