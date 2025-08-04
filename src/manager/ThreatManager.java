package manager;

import db.DatabaseManager;
import model.Threat;
import java.sql.*;
import java.util.*;

public class ThreatManager {
    public boolean addThreat(String name, String description, String severity) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO threats (name, description, severity) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, severity);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Add threat error: " + e.getMessage());
            return false;
        }
    }

    public boolean updateThreat(int threatId, String name, String description, String severity) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE threats SET name=?, description=?, severity=? WHERE threat_id=?");
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, severity);
            ps.setInt(4, threatId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Update threat error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteThreat(int threatId) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM threats WHERE threat_id=?");
            ps.setInt(1, threatId);
            ps.executeUpdate();
            reorderThreatIdsAndUpdateReferences();
            return true;
        } catch (SQLException e) {
            System.err.println("Delete threat error: " + e.getMessage());
            return false;
        }
    }

    private void reorderThreatIdsAndUpdateReferences() {
        try (Connection conn = DatabaseManager.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT threat_id FROM threats ORDER BY threat_id ASC");
            List<Integer> oldIds = new ArrayList<>();
            while (rs.next()) {
                oldIds.add(rs.getInt("threat_id"));
            }

            Map<Integer, Integer> idMap = new HashMap<>();
            for (int i = 0; i < oldIds.size(); i++) {
                idMap.put(oldIds.get(i), i + 1);
            }

            for (Map.Entry<Integer, Integer> entry : idMap.entrySet()) {
                PreparedStatement ps = conn.prepareStatement(
                    "UPDATE alerts_log SET threat_id=? WHERE threat_id=?");
                ps.setInt(1, entry.getValue());
                ps.setInt(2, entry.getKey());
                ps.executeUpdate();
            }

            for (Map.Entry<Integer, Integer> entry : idMap.entrySet()) {
                PreparedStatement ps = conn.prepareStatement(
                    "UPDATE threats SET threat_id=? WHERE threat_id=?");
                ps.setInt(1, entry.getValue());
                ps.setInt(2, entry.getKey());
                ps.executeUpdate();
            }

            st.execute("ALTER TABLE threats AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            System.err.println("Reorder threat IDs error: " + e.getMessage());
        }
    }

    public List<Threat> getAllThreats() {
        List<Threat> threats = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM threats");
            while (rs.next()) {
                threats.add(new Threat(
                    rs.getInt("threat_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("severity")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Get threats error: " + e.getMessage());
        }
        return threats;
    }

    public Threat getThreatByName(String name) {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM threats WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Threat(
                    rs.getInt("threat_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("severity")
                );
            }
        } catch (SQLException e) {
            System.err.println("Get threat by name error: " + e.getMessage());
        }
        return null;
    }
}