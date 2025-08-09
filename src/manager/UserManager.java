package manager;
import db.DatabaseManager;
import model.User;
import util.PasswordUtils;
import java.sql.*;
import java.util.*;

public class UserManager {
    public User authenticate(String username, String password) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                int userId = rs.getInt("user_id");
                String role = rs.getString("role");
                int streak = rs.getInt("streak");
                int passwordLength = rs.getInt("password_length");
                
                if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$")) {
                    if (PasswordUtils.verifyPassword(password, storedPassword)) {
                        return new User(userId, username, storedPassword, role, streak, passwordLength);
                    }
                } else {
                    if (password.equals(storedPassword)) {
                        String hashedPassword = PasswordUtils.hashPassword(password);
                        PreparedStatement updatePs = conn.prepareStatement(
                            "UPDATE users SET password=?, password_length=? WHERE user_id=?");
                        updatePs.setString(1, hashedPassword);
                        updatePs.setInt(2, password.length());
                        updatePs.setInt(3, userId);
                        updatePs.executeUpdate();
                        
                        return new User(userId, username, hashedPassword, role, streak, passwordLength);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Auth error: " + e.getMessage());
        }
        return null;
    }
    
    public boolean addUser(String username, String password, String role) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String hashedPassword = PasswordUtils.hashPassword(password);
            
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (username, password, role, password_length) VALUES (?, ?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, role);
            ps.setInt(4, password.length());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Add user error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateUser(int userId, String username, String role) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE users SET username=?, role=? WHERE user_id=?");
            ps.setString(1, username);
            ps.setString(2, role);
            ps.setInt(3, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Update user error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteUser(int userId) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE user_id=?");
            ps.setInt(1, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Delete user error: " + e.getMessage());
            return false;
        }
    }
    
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getInt("streak"),
                    rs.getInt("password_length")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Get users error: " + e.getMessage());
        }
        return users;
    }
    
    public boolean changePassword(int userId, String newPassword) {
        try (Connection conn = DatabaseManager.getConnection()) {
         
            String hashedPassword = PasswordUtils.hashPassword(newPassword);
            
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE users SET password=?, password_length=? WHERE user_id=?");
            ps.setString(1, hashedPassword);
            ps.setInt(2, newPassword.length());
            ps.setInt(3, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Change password error: " + e.getMessage());
            return false;
        }
    }
    
    public void incrementStreak(int userId) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE users SET streak = streak + 1 WHERE user_id=?");
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Increment streak error: " + e.getMessage());
        }
    }
}