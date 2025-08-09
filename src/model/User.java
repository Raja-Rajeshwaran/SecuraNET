package model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String role;
    private int streak;
    private int passwordLength; 

    public User(int userId, String username, String password, String role, int streak, int passwordLength) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.streak = streak;
        this.passwordLength = passwordLength;
    }

    public int getUserId() { 
        return userId; 
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public String getRole() { 
        return role; 
    }
    
    public int getStreak() { 
        return streak; 
    }
    
    public int getPasswordLength() { 
        return passwordLength; 
    }
    
    public void setStreak(int streak) { 
        this.streak = streak; 
    }
    
    public void setPasswordLength(int passwordLength) { 
        this.passwordLength = passwordLength; 
    }
}