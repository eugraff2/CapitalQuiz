package edu.uga.cs.capitalquiz;

/**
 * POJO to represent a state capital quiz
 */

public class Quiz {

    private long id;
    private String date;
    private double result;
    private int numAnswered;
    private String q1;
    private String q2;
    private String q3;
    private String q4;
    private String q5;
    private String q6;

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

    public Quiz(String date, double result, int numAnswered, String q1, String q2,
                 String q3, String q4, String q5, String q6) {
        this.id = -1;
        this.result = result;
        this.numAnswered = numAnswered;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getDate() {return date;}
    public void setDate(String inDate) {this.date = inDate;}

    public double getResult() {return result;}
    public void setResult(double inResult) {this.result = inResult;}

    public int getNumAnswered() {return numAnswered;}
    public void setNumAnswered(int inNum) {this.numAnswered = inNum;}

    public String getQ1() {return this.q1;}
    public void setQ1(String q) {this.q1 = q;}

    public String getQ2() {return this.q2;}
    public void setQ2(String q) {this.q2 = q;}

    public String getQ3() {return this.q3;}
    public void setQ3(String q) {this.q3 = q;}

    public String getQ4() {return this.q4;}
    public void setQ4(String q) {this.q4 = q;}

    public String getQ5() {return this.q5;}
    public void setQ5(String q) {this.q5 = q;}

    public String getQ6() {return this.q6;}
    public void setQ6(String q) {this.q6 = q;}

    /**
     * This method will be used when displaying past quizzes in ReviewQuizFragment
     */
    public String toString() {
        return  "Date: " + this.getDate() + "\n" +
                "Questions Answered: " + this.getNumAnswered() + "\n" +
                "Result: " + this.getResult() + "\n" +
                "Question 1: " + this.getQ1() + "Question 2: " + this.getQ2() + "\n" +
                "Question 3: " + this.getQ3() + "\n" +
                "Question 4: " + this.getQ4() + "\n" +
                "Question 5: " + this.getQ5() + "\n" +
                "Question 6: " + this.getQ6() + "\n";
    } // toString


} // Quiz
