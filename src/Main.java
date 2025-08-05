import manager.*;
import core.*;
import model.*;
import util.*;

import java.util.*;
import java.io.IOException;

public class Main {
    private static UserManager userManager = new UserManager();
    private static ThreatManager threatManager = new ThreatManager();
    private static AlertManager alertManager = new AlertManager();
    private static FeedbackManager feedbackManager = new FeedbackManager();
    private static IDSCore idsCore = new IDSCore(threatManager, alertManager);

    public static void main(String[] args) {
        System.out.println("===== Welcome to Securanet IDS =====");
        User user = null;
        while (user == null) {
            String username = ConsoleUtils.readLine("Username: ");
            String password = ConsoleUtils.readPassword("Password: ");
            user = userManager.authenticate(username, password);
            if (user == null) {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
        System.out.println("Login successful!"+ "\n" +"Your role: " + user.getRole());
        userManager.incrementStreak(user.getUserId());

        if (user.getRole().equalsIgnoreCase("admin")) {
            adminMenu(user);
        } else {
            userMenu(user);
        }
    }

    private static void adminMenu(User user) {
        while (true) {
            System.out.println("\n----- Admin Dashboard -----");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Threats");
            System.out.println("3. View/Export Alerts");
            System.out.println("4. Simulate Scan");
            System.out.println("5. Change Password");
            System.out.println("6. View Feedback");
            System.out.println("7. Submit Feedback");
            System.out.println("8. View Profile Streak");
            System.out.println("9. Creator Credit");
            System.out.println("0. Logout");
            int choice = ConsoleUtils.readInt("Select your choice : ");
            switch (choice) {
                case 1: manageUsers(); break;
                case 2: manageThreats(); break;
                case 3: viewExportAlerts(); break;
                case 4: simulateScan(user); break;
                case 5: changePassword(user); break;
                case 6: viewFeedback(); break;
                case 7: submitFeedback(user); break;
                case 8: System.out.println("Profile streak: " + user.getStreak()); break;
                case 9: System.out.println("Created by RR"); break;
                case 0: System.out.println("Thank you!"); System.exit(0);
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void userMenu(User user) {
        while (true) {
            System.out.println("\n----- User Dashboard -----");
            System.out.println("1. View Threats");
            System.out.println("2. View/Acknowledge Alerts");
            System.out.println("3. Simulate Scan");
            System.out.println("4. Change Password");
            System.out.println("5. Submit Feedback");
            System.out.println("6. View Profile Streak");
            System.out.println("7. Creator Credit");
            System.out.println("0. Logout");
            int choice = ConsoleUtils.readInt("Select your choice: ");
            switch (choice) {
                case 1: viewThreats(); break;
                case 2: viewAcknowledgeAlerts(user); break;
                case 3: simulateScan(user); break;
                case 4: changePassword(user); break;
                case 5: submitFeedback(user); break;
                case 6: System.out.println("Profile streak: " + user.getStreak()); break;
                case 7: System.out.println("Created by RR"); break;
                case 0: System.out.println("Thank you!"); System.exit(0);
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void manageUsers() {
        while (true) {
            System.out.println("\n----- User Management -----");
            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. View Users");
            System.out.println("0. Back");
            int choice = ConsoleUtils.readInt("Select your choice: ");
            switch (choice) {
                case 1:
                    String uname = ConsoleUtils.readLine("Username: ");
                    String pwd = ConsoleUtils.readPassword("Password: ");
                    String role = ConsoleUtils.readLine("Role (admin/user): ");
                    if (userManager.addUser(uname, pwd, role)) System.out.println("User added.");
                    else System.out.println("Failed to add user.");
                    break;
                case 2:
                    int uid = ConsoleUtils.readInt("User ID: ");
                    String newUname = ConsoleUtils.readLine("New Username: ");
                    String newRole = ConsoleUtils.readLine("New Role (admin/user): ");
                    if (userManager.updateUser(uid, newUname, newRole)) System.out.println("User updated.");
                    else System.out.println("Failed to update user.");
                    break;
                case 3:
                    int delUid = ConsoleUtils.readInt("User ID: ");
                    if (userManager.deleteUser(delUid)) System.out.println("User deleted.");
                    else System.out.println("Failed to delete user.");
                    break;
                case 4:
                    for (User u : userManager.getAllUsers()) {
                        System.out.println("ID: " + u.getUserId() + " | " + u.getUsername() + " | Role: " + u.getRole() + " | Streak: " + u.getStreak());
                    }
                    break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void manageThreats() {
        while (true) {
            System.out.println("\n----- Threat Management -----");
            System.out.println("1. Add Threat");
            System.out.println("2. Update Threat");
            System.out.println("3. Delete Threat");
            System.out.println("4. View Threats");
            System.out.println("0. Back");
            int choice = ConsoleUtils.readInt("Select: ");
            switch (choice) {
                case 1:
                    String name = ConsoleUtils.readLine("Threat Name: ");
                    String desc = ConsoleUtils.readLine("Description: ");
                    String sev = ConsoleUtils.readLine("Severity (Low/Medium/High): ");
                    if (threatManager.addThreat(name, desc, sev)) System.out.println("Threat added.");
                    else System.out.println("Failed to add threat.");
                    break;
                case 2:
                    int tid = ConsoleUtils.readInt("Threat ID: ");
                    String nname = ConsoleUtils.readLine("New Name: ");
                    String ndesc = ConsoleUtils.readLine("New Description: ");
                    String nsev = ConsoleUtils.readLine("New Severity (Low/Medium/High): ");
                    if (threatManager.updateThreat(tid, nname, ndesc, nsev)) System.out.println("Threat updated.");
                    else System.out.println("Failed to update threat.");
                    break;
                case 3:
                    int delTid = ConsoleUtils.readInt("Threat ID: ");
                    if (threatManager.deleteThreat(delTid)) System.out.println("Threat deleted.");
                    else System.out.println("Failed to delete threat.");
                    break;
                case 4: viewThreats(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewExportAlerts() {
        List<AlertLog> alerts = alertManager.getAllAlerts();
        for (AlertLog a : alerts) {
            System.out.println("LogID: " + a.getLogId() + " | Time: " + a.getTimestamp() + " | Severity: " + a.getSeverity() +
                " | ThreatID: " + a.getThreatId() + " | Ack: " + a.isAcknowledged() + " | UserID: " + a.getUserId());
        }
        String export = ConsoleUtils.readLine("Export to CSV? (y/n): ");
        if (export.equalsIgnoreCase("y")) {
            List<String[]> data = new ArrayList<>();
            data.add(new String[]{"LogID", "Timestamp", "Severity", "ThreatID", "Acknowledged", "UserID"});
            for (AlertLog a : alerts) {
                data.add(new String[]{
                    String.valueOf(a.getLogId()),
                    a.getTimestamp().toString(),
                    a.getSeverity(),
                    String.valueOf(a.getThreatId()),
                    String.valueOf(a.isAcknowledged()),
                    String.valueOf(a.getUserId())
                });
            }
            try {
                CSVExporter.export("alerts.csv", data);
                System.out.println("Exported to alerts.csv");
            } catch (IOException e) {
                System.out.println("Export failed: " + e.getMessage());
            }
        }
    }

    private static void viewAcknowledgeAlerts(User user) {
        List<AlertLog> alerts = alertManager.getAllAlerts();
        for (AlertLog a : alerts) {
            if (a.getUserId() == user.getUserId()) {
                System.out.println("LogID: " + a.getLogId() + " | Time: " + a.getTimestamp() + " | Severity: " + a.getSeverity() +
                    " | ThreatID: " + a.getThreatId() + " | Ack: " + a.isAcknowledged());
            }
        }
        int ackId = ConsoleUtils.readInt("Enter LogID to acknowledge (0 to skip): ");
        if (ackId != 0) {
            if (alertManager.acknowledgeAlert(ackId)) System.out.println("Alert acknowledged.");
            else System.out.println("Failed to acknowledge alert.");
        }
    }

    private static void viewThreats() {
        for (Threat t : threatManager.getAllThreats()) {
            System.out.println("ID: " + t.getThreatId() + " | " + t.getName() + " | " + t.getDescription() + " | Severity: " + t.getSeverity());
        }
    }

    private static void simulateScan(User user) {
        String input = ConsoleUtils.readLine("Enter data to scan: ");
        idsCore.simulateScan(input, user);
    }

    private static void changePassword(User user) {
        String newPwd = ConsoleUtils.readPassword("New Password: ");
        if (userManager.changePassword(user.getUserId(), newPwd)) System.out.println("Password changed.");
        else System.out.println("Failed to change password.");
    }

    private static void submitFeedback(User user) {
        String msg = ConsoleUtils.readLine("Enter feedback: ");
        if (feedbackManager.addFeedback(user.getUserId(), msg)) System.out.println("Feedback submitted.");
        else System.out.println("Failed to submit feedback.");
    }

    private static void viewFeedback() {
        for (Feedback f : feedbackManager.getAllFeedback()) {
            System.out.println("ID: " + f.getFeedbackId() + " | UserID: " + f.getUserId() + " | " + f.getMessage() + " | " + f.getTimestamp());
        }
    }
}