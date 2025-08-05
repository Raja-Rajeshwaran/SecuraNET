package core;

import manager.ThreatManager;
import manager.AlertManager;
import model.Threat;
import model.User;

public class IDSCore {
    private ThreatManager threatManager;
    private AlertManager alertManager;

    public IDSCore(ThreatManager threatManager, AlertManager alertManager) {
        this.threatManager = threatManager;
        this.alertManager = alertManager;
    }

    public Threat scan(String input) {
        for (Threat threat : threatManager.getAllThreats()) {
            if (input.toLowerCase().contains(threat.getName().toLowerCase())) {
                return threat;
            }
        }
        return null;
    }

    public void simulateScan(String input, User user) {
        Threat threat = scan(input);
        if (threat != null) {
            System.out.println("ALERT: Threat detected! [" + threat.getName() + "] Severity: " + threat.getSeverity());
            alertManager.logAlert(threat.getSeverity(), threat.getThreatId(), user.getUserId());
        } else {
            System.out.println("No threats detected.");
        }
    }
}