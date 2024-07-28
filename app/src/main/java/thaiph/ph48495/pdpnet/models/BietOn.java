package thaiph.ph48495.pdpnet.models;

import java.util.List;

public class BietOn {
    private int id;
    private List<String> contents;
    private String date;
    private int userID;

    public BietOn(int id, List<String> contents, String date, int userID) {
        this.id = id;
        this.contents = contents;
        this.date = date;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}