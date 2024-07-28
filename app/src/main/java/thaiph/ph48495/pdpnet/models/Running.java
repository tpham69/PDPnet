package thaiph.ph48495.pdpnet.models;

public class Running {
    private int id;
    private int userID;
    private String date;
    private int steps;

    public Running(int id, int userID, String date, int steps) {
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
