package manager;

import db.DatabaseManager;
import model.Feedback;
import java.sql.*;
import java.util.*;

public class FeedbackManager {
    public boolean addFeedback(int userId, String message) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO feedback (user_id, message) VALUES (?, ?)");
            ps.setInt(1, userId);
            ps.setString(2, message);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Add feedback error: " + e.getMessage());
            return false;
        }
    }

    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbacks = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM feedback");
            while (rs.next()) {
                feedbacks.add(new Feedback(
                    rs.getInt("feedback_id"),
                    rs.getInt("user_id"),
                    rs.getString("message"),
                    rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Get feedback error: " + e.getMessage());
        }
        return feedbacks;
    }
}