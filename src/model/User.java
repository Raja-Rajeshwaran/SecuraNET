package model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String role;
    private int streak;

    public User(int userId, String username, String password, String role, int streak) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.streak = streak;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public int getStreak() { return streak; }
    public void setStreak(int streak) { this.streak = streak; }
}