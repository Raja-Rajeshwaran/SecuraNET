package model;

public class Threat {
    private int threatId;
    private String name;
    private String description;
    private String severity;

    public Threat(int threatId, String name, String description, String severity) {
        this.threatId = threatId;
        this.name = name;
        this.description = description;
        this.severity = severity;
    }

    // Getters and setters
    public int getThreatId() { return threatId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSeverity() { return severity; }
}