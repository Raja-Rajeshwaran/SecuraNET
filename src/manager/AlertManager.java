package manager;

import db.DatabaseManager;
import model.AlertLog;
import java.sql.*;
import java.util.*;

public class AlertManager {
    public boolean logAlert(String severity, int threatId, int userId) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO alerts_log (severity, threat_id, user_id) VALUES (?, ?, ?)");
            ps.setString(1, severity);
            ps.setInt(2, threatId);
            ps.setInt(3, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Log alert error: " + e.getMessage());
            return false;
        }
    }

    public List<AlertLog> getAllAlerts() {
        List<AlertLog> alerts = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM alerts_log");
            while (rs.next()) {
                alerts.add(new AlertLog(
                    rs.getInt("log_id"),
                    rs.getTimestamp("timestamp"),
                    rs.getString("severity"),
                    rs.getInt("threat_id"),
                    rs.getBoolean("acknowledged"),
                    rs.getInt("user_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Get alerts error: " + e.getMessage());
        }
        return alerts;
    }

    public boolean acknowledgeAlert(int logId) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE alerts_log SET acknowledged=TRUE WHERE log_id=?");
            ps.setInt(1, logId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Acknowledge alert error: " + e.getMessage());
            return false;
        }
    }
}