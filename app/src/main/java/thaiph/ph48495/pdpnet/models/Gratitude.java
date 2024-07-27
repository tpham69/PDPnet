package thaiph.ph48495.pdpnet.models;

public class Gratitude {
    private int ID;
    private String entry,date;

    public Gratitude(int ID, String entry, String date) {
        this.ID = ID;
        this.entry = entry;
        this.date = date;
    }

    public Gratitude() {
    }

    public Gratitude(String entry, String date) {
        this.entry = entry;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
