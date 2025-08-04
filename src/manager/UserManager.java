package manager;

import db.DatabaseManager;
import model.User;
import java.sql.*;
import java.util.*;

public class UserManager {
    public User authenticate(String username, String password) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getInt("streak")
                );
            }
        } catch (SQLException e) {
            System.err.println("Auth error: " + e.getMessage());
        }
        return null;
    }

    public boolean addUser(String username, String password, String role) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
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
            reorderUserIdsAndUpdateReferences();
            return true;
        } catch (SQLException e) {
            System.err.println("Delete user error: " + e.getMessage());
            return false;
        }
    }

    private void reorderUserIdsAndUpdateReferences() {
        try (Connection conn = DatabaseManager.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT user_id FROM users ORDER BY user_id ASC");
            List<Integer> oldIds = new ArrayList<>();
            while (rs.next()) {
                oldIds.add(rs.getInt("user_id"));
            }

            Map<Integer, Integer> idMap = new HashMap<>();
            for (int i = 0; i < oldIds.size(); i++) {
                idMap.put(oldIds.get(i), i + 1);
            }

            for (Map.Entry<Integer, Integer> entry : idMap.entrySet()) {
                PreparedStatement ps = conn.prepareStatement(
                    "UPDATE alerts_log SET user_id=? WHERE user_id=?");
                ps.setInt(1, entry.getValue());
                ps.setInt(2, entry.getKey());
                ps.executeUpdate();
            }

            for (Map.Entry<Integer, Integer> entry : idMap.entrySet()) {
                PreparedStatement ps = conn.prepareStatement(
                    "UPDATE users SET user_id=? WHERE user_id=?");
                ps.setInt(1, entry.getValue());
                ps.setInt(2, entry.getKey());
                ps.executeUpdate();
            }

            st.execute("ALTER TABLE users AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            System.err.println("Reorder user IDs error: " + e.getMessage());
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
                    rs.getInt("streak")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Get users error: " + e.getMessage());
        }
        return users;
    }

    public boolean changePassword(int userId, String newPassword) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE users SET password=? WHERE user_id=?");
            ps.setString(1, newPassword);
            ps.setInt(2, userId);
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