package model;

import java.sql.Timestamp;

public class AlertLog {
    private int logId;
    private Timestamp timestamp;
    private String severity;
    private int threatId;
    private boolean acknowledged;
    private int userId;

    public AlertLog(int logId, Timestamp timestamp, String severity, int threatId, boolean acknowledged, int userId) {
        this.logId = logId;
        this.timestamp = timestamp;
        this.severity = severity;
        this.threatId = threatId;
        this.acknowledged = acknowledged;
        this.userId = userId;
    }

    // Getters and setters
    public int getLogId() { return logId; }
    public Timestamp getTimestamp() { return timestamp; }
    public String getSeverity() { return severity; }
    public int getThreatId() { return threatId; }
    public boolean isAcknowledged() { return acknowledged; }
    public int getUserId() { return userId; }
}