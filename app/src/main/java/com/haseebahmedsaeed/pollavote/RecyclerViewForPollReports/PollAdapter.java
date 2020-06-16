package com.haseebahmedsaeed.pollavote.RecyclerViewForPollReports;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.haseebahmedsaeed.pollavote.R;
import com.haseebahmedsaeed.pollavote.ShareReport.ShareReports;
import com.haseebahmedsaeed.pollavote.reportGenerate;

import java.util.ArrayList;

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.ViewHolder> {

    // Declaration of Different DataTypes
    public static ArrayList<PollCardView> people;
    static final String TAG="PollAdapter";
    public static int position;
    final Context context = reportGenerate.context;
    DatabaseReference databaseReference;

    public PollAdapter(ArrayList<PollCardView> list) {
        people = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Declaration of the Fields on Reports Activity
        private TextView NumberofPoll, tvName, tvSurname, Option1, Option2, Option3, Option4;
        private TextView percent1, percent2, percent3, percent4;
        private ProgressBar progbar1, progbar2, progbar3, progbar4;
        private Button deletePollButton, sharePollButton;

        // Initialization of the Fields on Reports Activity
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NumberofPoll = itemView.findViewById(R.id.NumberofPoll);
            tvName = itemView.findViewById(R.id.tvName);
            tvSurname = itemView.findViewById(R.id.tvSurname);

            Option1 = itemView.findViewById(R.id.Option1);
            Option2 = itemView.findViewById(R.id.Option2);
            Option3 = itemView.findViewById(R.id.Option3);
            Option4 = itemView.findViewById(R.id.Option4);

            progbar1 = itemView.findViewById(R.id.progbar1);
            progbar2 = itemView.findViewById(R.id.progbar2);
            progbar3 = itemView.findViewById(R.id.progbar3);
            progbar4 = itemView.findViewById(R.id.progbar4);

            percent1 = itemView.findViewById(R.id.percent1);
            percent2 = itemView.findViewById(R.id.percent2);
            percent3 = itemView.findViewById(R.id.percent3);
            percent4 = itemView.findViewById(R.id.percent4);

            deletePollButton = itemView.findViewById(R.id.deletePollButton);
            sharePollButton = itemView.findViewById(R.id.sharePollButton);
        }
    }

    @Override
    public PollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pollslist_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PollAdapter.ViewHolder viewHolder, final int i) {

        String num = (i+1)+".";

        // Binding data with respective fields in Reports Activity
        viewHolder.NumberofPoll.setText(num);
        viewHolder.tvName.setText(people.get(i).getName());
        viewHolder.tvSurname.setText(people.get(i).getSurname());

        viewHolder.Option1.setText(people.get(i).getOp1());
        viewHolder.Option2.setText(people.get(i).getOp2());
        viewHolder.Option3.setText(people.get(i).getOp3());
        viewHolder.Option4.setText(people.get(i).getOp4());

        int num1 = people.get(i).getOpNo1();
        int num2 = people.get(i).getOpNo2();
        int num3 = people.get(i).getOpNo3();
        int num4 = people.get(i).getOpNo4();
        int totalVotes = num1+num2+num3+num4;

        viewHolder.percent1.setText(String.valueOf(num1));
        viewHolder.percent2.setText(String.valueOf(num2));
        viewHolder.percent3.setText(String.valueOf(num3));
        viewHolder.percent4.setText(String.valueOf(num4));

        if (totalVotes != 0) {
            viewHolder.progbar1.setProgress(((num1*100)/totalVotes));
            viewHolder.progbar2.setProgress(((num2*100)/totalVotes));
            viewHolder.progbar3.setProgress(((num3*100)/totalVotes));
            viewHolder.progbar4.setProgress(((num4*100)/totalVotes));
        }

        // Delete Button Clicked!
        viewHolder.deletePollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDeleteCall(i);
            }
        });

        // Share Button Clicked!
        viewHolder.sharePollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = i;
                Intent intent = new Intent(context.getApplicationContext(), ShareReports.class);
                context.startActivity(intent);
            }
        });
    }

    // Delete Button Function
    public void GetDeleteCall(int index){
        String pTitle = people.get(index).getName();
        databaseReference = people.get(index).getPollPath();
        AlertDialog(pTitle);
    }

    // Alert Dialog after Delete Button Clicked
    public void AlertDialog(String pTitle){
        final AlertDialog.Builder ui = new AlertDialog.Builder(context);

        ui.setTitle("Delete the Poll");
        ui.setIcon(R.drawable.ic_delete_black);
        ui.setMessage("Are you sure you want to delete: \n\n"+"'"+pTitle+"'");
        ui.setCancelable(true);

        ui.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        databaseReference.removeValue();
                        Toast.makeText(context, "Success: Poll is deleted", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        ui.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog malert = ui.create();
        malert.show();
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

}
