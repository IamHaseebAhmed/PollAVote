package com.haseebahmedsaeed.pollavote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListOfVoters extends AppCompatActivity {

    // Widgets Declaration
    ListView simpleListView;
    Button rfButton;
    ProgressBar simpleProgressBar;

    // Firebase Database Root Reference
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference itemRef = database.child("Employee");

    // To save all user's name from Firebase init.
    List<String> itemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_voters);

        simpleListView = findViewById(R.id.simpleListView);
        simpleProgressBar = findViewById(R.id.simpleProgressBar);
        rfButton = findViewById(R.id.rfButton);
        itemlist = new ArrayList<>();

        // This will read data from Firebase and present it in SimpleListView.
        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<String> list) {
                ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_listview,R.id.VoterName,list);
                simpleListView.setAdapter(adapter);
            }
        });

        // When Refresh Button clicked
        rfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                simpleProgressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(getApplicationContext(), ListOfVoters.class));
            }
        });
    }

    private void readData(final FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("name").getValue(String.class);
                    itemlist.add(name);
                }
                firebaseCallback.onCallback(itemlist);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        itemRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private interface FirebaseCallback {
        void onCallback(List<String> list);
    }
}
