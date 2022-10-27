package edu.uga.cs.capitalquiz;

/**
 * POJO to represent a state capital quiz
 */

public class Quiz {

    private long id;
    private String date;
    private double result;
    private int numAnswered;
    private Question q1;
    private Question q2;
    private Question q3;
    private Question q4;
    private Question q5;
    private Question q6;

    public Quiz() {
        this.id = -1;
        this.date = null;
        this.result = -1.0;
        this.numAnswered = -1;
        this.q1 = null;
        this.q2 = null;
        this.q3 = null;
        this.q4 = null;
        this.q5 = null;
        this.q6 = null;
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getDate() {return date;}
    public void setDate(String inDate) {this.date = inDate;}

    public double getResult() {return result;}
    public void setResult(double inResult) {this.result = inResult;}

    public int getNumAnswered() {return numAnswered;}
    public void setNumAnswered(int inNum) {this.numAnswered = inNum;}

    public Question getQ1() {return this.q1;}
    public void setQ1(Question q) {this.q1 = q;}

    public Question getQ2() {return this.q2;}
    public void setQ2(Question q) {this.q2 = q;}

    public Question getQ3() {return this.q3;}
    public void setQ3(Question q) {this.q3 = q;}

    public Question getQ4() {return this.q4;}
    public void setQ4(Question q) {this.q4 = q;}

    public Question getQ5() {return this.q5;}
    public void setQ5(Question q) {this.q5 = q;}

    public Question getQ6() {return this.q6;}
    public void setQ6(Question q) {this.q6 = q;}


} // Quiz
