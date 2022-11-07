package edu.uga.cs.capitalquiz;

/**
 * POJO to represent a quiz question
 */

public class Question {

    private long id;
    private String name;
    private String capital;
    private String additional1;
    private String additional2;

    public Question() {
        this.id = -1;
        this.name = null;
        this.capital = null;
        this.additional1 = null;
        this.additional2 = null;
    }

    public Question(String name, String capital, String additional1, String additional2) {
        this.name = name;
        this.capital = capital;
        this.additional1 = additional1;
        this.additional2 = additional2;
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String inName) {this.name = inName;}

    public String capital() {return capital;}
    public void setCapital(String inCapital) {this.capital = inCapital;}

    public String getAdditional1(){return additional1;}
    public void setAdditional1(String inAdd) {this.additional1 = inAdd;}

    public String getAdditional2(){return additional2;}
    public void setAdditional2(String inAdd) {this.additional2 = inAdd;}

    /**
     * This method will be used when storing questions into a quiz using setQ#(...)
     */
    public String toString() {
        return id + ": " + name + " " + capital + " " + additional1 +  " " + additional2;
    }


} // Question
