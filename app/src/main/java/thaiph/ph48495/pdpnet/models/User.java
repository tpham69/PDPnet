package thaiph.ph48495.pdpnet.models;

public class User {
    private String email;
    private int streak;
    private float todayDistance;
    private float totalDistance;

    public User(String email, int streak, float todayDistance, float totalDistance) {
        this.email = email;
        this.streak = streak;
        this.todayDistance = todayDistance;
        this.totalDistance = totalDistance;
    }

    public String getEmail() {
        return email;
    }

    public int getStreak() {
        return streak;
    }

    public float getTodayDistance() {
        return todayDistance;
    }

    public float getTotalDistance() {
        return totalDistance;
    }
}