package com.haseebahmedsaeed.pollavote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haseebahmedsaeed.pollavote.RecyclerViewForPollReports.PollCardView;
import com.haseebahmedsaeed.pollavote.RecyclerViewForPollReports.PollAdapter;

public class reportGenerate extends AppCompatActivity {

    DatabaseReference database;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<PollCardView> PollMap;

    public static Context context;
    static final String TAG="reportGenerate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_generate);

        context = reportGenerate.this;

        recyclerView = findViewById(R.id.Polllist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PollMap = new ArrayList<>();

        // Firebase Database Root Reference
        database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Polls");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    DatabaseReference PollPath = data.getRef();

                    String ptitle = data.child("title").getValue(String.class);
                    String ptimestamp = data.child("timestamp").getValue(String.class);

                    String op1 = data.child("op1").getValue(String.class);
                    String op2 = data.child("op2").getValue(String.class);
                    String op3 = data.child("op3").getValue(String.class);
                    String op4 = data.child("op4").getValue(String.class);

                    int opNo1 = data.child("opNo1").getValue(Integer.class);
                    int opNo2 = data.child("opNo2").getValue(Integer.class);
                    int opNo3 = data.child("opNo3").getValue(Integer.class);
                    int opNo4 = data.child("opNo4").getValue(Integer.class);

                    PollMap.add(new PollCardView(PollPath, ptitle, ptimestamp, op1, op2, op3, op4, opNo1, opNo2, opNo3, opNo4));
                }
                myAdapter=new PollAdapter(PollMap);
                recyclerView.setAdapter(myAdapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}
