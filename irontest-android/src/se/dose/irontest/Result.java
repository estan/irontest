package se.dose.irontest;

import java.util.Date;

public class Result {
    public final static String RESULT_ID = "se.dose.irontest.RESULT_ID";
    
    long id;
    Date date;
    String comment;
    String result;
    String tester;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public String getTester() {
        return tester;
    }
    
    public void setTester(String tester) {
        this.tester = tester;
    }
}
