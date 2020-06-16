package com.haseebahmedsaeed.pollavote;

public class Polls {

    public String timestamp, title, op1, op2, op3, op4;
    public int opNo1, opNo2, opNo3, opNo4;

    public Polls(){

    }

    public Polls(String timestamp, String title, String op1, String op2, String op3, String op4){

        this.timestamp = timestamp;
        this.title = title;
        this.op1 = op1;
        this.opNo1 = 0;

        this.op2 = op2;
        this.opNo2 = 0;

        this.op3 = op3;
        this.opNo3 = 0;

        this.op4 = op4;
        this.opNo4 = 0;

    }

}
