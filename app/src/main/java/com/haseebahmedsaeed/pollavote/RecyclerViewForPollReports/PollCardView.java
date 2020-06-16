package com.haseebahmedsaeed.pollavote.RecyclerViewForPollReports;


import android.app.AlertDialog;
import android.content.DialogInterface;

import com.google.firebase.database.DatabaseReference;

public class PollCardView {

    private DatabaseReference PollPath;
    private String name;
    private String timestamp;
    private String op1, op2, op3, op4;
    private int opNo1, opNo2, opNo3, opNo4;

    public PollCardView(DatabaseReference PollPath, String name, String surname, String op1, String op2, String op3, String op4, int opNo1, int opNo2, int opNo3, int opNo4) {
        this.PollPath = PollPath;
        this.name = name;
        this.timestamp = surname;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
        this.opNo1 = opNo1;
        this.opNo2 = opNo2;
        this.opNo3 = opNo3;
        this.opNo4 = opNo4;
    }


    public DatabaseReference getPollPath() {
        return PollPath;
    }

    public void setPollPath(DatabaseReference pollPath) {
        PollPath = pollPath;
    }

    public int getOpNo1() {
        return opNo1;
    }

    public void setOpNo1(int opNo1) {
        this.opNo1 = opNo1;
    }

    public int getOpNo2() {
        return opNo2;
    }

    public void setOpNo2(int opNo2) {
        this.opNo2 = opNo2;
    }

    public int getOpNo3() {
        return opNo3;
    }

    public void setOpNo3(int opNo3) {
        this.opNo3 = opNo3;
    }

    public int getOpNo4() {
        return opNo4;
    }

    public void setOpNo4(int opNo4) {
        this.opNo4 = opNo4;
    }

    public PollCardView() {

    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return timestamp;
    }

    public void setSurname(String surname) {
        this.timestamp = surname;
    }
}
