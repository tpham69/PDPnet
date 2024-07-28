package thaiph.ph48495.pdpnet.models;

public class User {
    private String email;
    private int streakBietOn;
    private int streakChayBo;
    private float todayDistance;
    private float totalDistance;

    public User(String email, int streakBietOn, int streakChayBo, float todayDistance, float totalDistance) {
        this.email = email;
        this.streakBietOn = streakBietOn;
        this.streakChayBo = streakChayBo;
        this.todayDistance = todayDistance;
        this.totalDistance = totalDistance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStreakBietOn() {
        return streakBietOn;
    }

    public void setStreakBietOn(int streakBietOn) {
        this.streakBietOn = streakBietOn;
    }

    public int getStreakChayBo() {
        return streakChayBo;
    }

    public void setStreakChayBo(int streakChayBo) {
        this.streakChayBo = streakChayBo;
    }

    public float getTodayDistance() {
        return todayDistance;
    }

    public void setTodayDistance(float todayDistance) {
        this.todayDistance = todayDistance;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }
}