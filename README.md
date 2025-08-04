# 🔐 Securanet IDS - Signature-Based Intrusion Detection System

A comprehensive, modular Java console application that simulates a signature-based Intrusion Detection System (IDS) with MySQL backend, role-based access control, and real-time threat detection capabilities.

## 🚀 Features

### 🔐 Authentication & Security
- **Simple Username/Password Authentication** (no complex salt/hash)
- **Role-Based Access Control** (Admin/User)
- **Password Masking** (asterisks when typing)
- **Session Management** with profile streak tracking

### ⚠️ Threat Management
- **Complete CRUD Operations** for threats
- **Auto-ID Reordering** after deletions (sequential IDs)
- **Severity Levels**: Low, Medium, High
- **Threat Signature Matching** against user inputs

### 🚨 Alert System
- **Real-time Alert Generation** when threats are detected
- **Alert Acknowledgment** by users
- **CSV Export** functionality for all alerts
- **Comprehensive Logging** with timestamps

### 👥 User Management (Admin Only)
- **Add/Update/Delete Users**
- **Role Assignment** (admin/user)
- **Profile Streak Counter** (login tracking)
- **Password Change** functionality

### 📊 Additional Features
- **Feedback System** for user comments
- **Creator Credit** display
- **Modular Architecture** for easy expansion
- **Future-Ready** for ML, web dashboard, email alerts

## 🛠️ Tech Stack

- **Language**: Java (SE 8+)
- **Database**: MySQL
- **Driver**: MySQL Connector/J
- **UI**: Console-based interface
- **Architecture**: Modular, MVC pattern


## ⚙️ Installation & Setup

### Prerequisites
- Java JDK 8 or higher
- MySQL Server 5.7 or higher
- MySQL Connector/J (JDBC driver)

### Step 1: Database Setup
1. Start your MySQL server
2. Import the database schema:
   ```bash
   mysql -u root -p < db/securanet_schema.sql
   ```
3. Update database credentials in `src/db/DatabaseManager.java`:
   ```java
   private static final String USER = "your_mysql_username";
   private static final String PASSWORD = "your_mysql_password";
   ```

### Step 2: Add MySQL Driver
1. Download MySQL Connector/J from [MySQL website](https://dev.mysql.com/downloads/connector/j/)
2. Place `mysql-connector-java.jar` in the `lib/` directory

### Step 3: Compile and Run
```bash
# Navigate to project directory
cd Securanet

# Compile all Java files
javac -cp "lib/*;src" src/*.java src/*/*.java src/*/*/*.java

# Run the application
java -cp "lib/*;src" Main
```

## �� Default Login Credentials

| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin123 | admin |

## 🎯 Usage Guide

### Admin Features
1. **Manage Users**: Add, update, delete users and assign roles
2. **Manage Threats**: Add, edit, delete threat signatures
3. **View/Export Alerts**: Monitor all system alerts and export to CSV
4. **Simulate Scans**: Test threat detection with custom input
5. **System Administration**: Change passwords, view feedback, check streaks

### User Features
1. **View Threats**: Browse available threat signatures
2. **Acknowledge Alerts**: Mark alerts as reviewed
3. **Simulate Scans**: Test threat detection
4. **Submit Feedback**: Send comments to administrators
5. **Profile Management**: Change password, view streak

### Threat Detection
- Enter any text in the "Simulate Scan" feature
- System matches input against stored threat signatures
- If match found: Alert generated and logged
- If no match: "No threats detected" message

## 📊 CSV Export

CSV files are stored in the `exports/` directory and contain:

### alerts.csv
```csv
LogID,Timestamp,Severity,ThreatID,Acknowledged,UserID
1,2024-01-15 10:30:45,High,2,false,1
2,2024-01-15 11:15:22,Medium,1,true,2
```

## �� Configuration

### Database Connection
Edit `src/db/DatabaseManager.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/securanet";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

### Adding New Users
1. Login as admin
2. Navigate to "Manage Users" → "Add User"
3. Enter username, password, and role
4. User is immediately available for login

## 🚀 Future Enhancements

The modular architecture supports easy integration of:

- **Machine Learning Detection**: Anomaly-based threat detection
- **Web Dashboard**: Browser-based interface
- **Email Alerts**: Automated email notifications
- **Scheduled Scans**: Background threat monitoring
- **API Integration**: RESTful API endpoints
- **Real-time Monitoring**: Live threat detection

## 🐛 Troubleshooting

### Common Issues

1. **"MySQL JDBC Driver not found"**
   - Ensure `mysql-connector-java.jar` is in `lib/` directory
   - Check classpath includes `lib/*`

2. **"Invalid credentials"**
   - Verify username/password are correct
   - Check database connection settings
   - Ensure user exists in database

3. **"Database connection failed"**
   - Verify MySQL server is running
   - Check database credentials in `DatabaseManager.java`
   - Ensure database `securanet` exists

4. **"Permission denied" for CSV export**
   - Check write permissions in project directory
   - Ensure `exports/` folder can be created

### Debug Mode
Enable debug output by checking the authentication method in `UserManager.java` for detailed login information.

## 📝 Development

### Adding New Features
1. Create new model classes in `src/model/`
2. Add manager classes in `src/manager/`
3. Update `Main.java` with new menu options
4. Test thoroughly with different user roles

### Code Style
- Follow Java naming conventions
- Use meaningful variable names
- Add comments for complex logic
- Maintain modular structure

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is for educational purposes. Feel free to modify and extend for your learning needs.

## 👨‍�� Creator

**Created by [Your Name]**

A comprehensive Java console application demonstrating:
- Object-Oriented Programming principles
- Database integration with MySQL
- Role-based access control
- Modular software architecture
- Real-world application development

---

**🎉 Enjoy exploring Securanet IDS! ��**
