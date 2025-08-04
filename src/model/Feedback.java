package model;

import java.sql.Timestamp;

public class Feedback {
    private int feedbackId;
    private int userId;
    private String message;
    private Timestamp timestamp;

    public Feedback(int feedbackId, int userId, String message, Timestamp timestamp) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public int getFeedbackId() { return feedbackId; }
    public int getUserId() { return userId; }
    public String getMessage() { return message; }
    public Timestamp getTimestamp() { return timestamp; }
}