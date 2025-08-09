-- Create the database
CREATE DATABASE IF NOT EXISTS securanet;
USE securanet;

-- Create the users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'user') NOT NULL DEFAULT 'user',
    streak INT DEFAULT 0,
    password_length INT
);

-- Create the threats table
CREATE TABLE threats (
    threat_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    severity ENUM('Low', 'Medium', 'High') NOT NULL
);

-- Create the alerts_log table
CREATE TABLE alerts_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    severity ENUM('Low', 'Medium', 'High') NOT NULL,
    threat_id INT,
    acknowledged BOOLEAN DEFAULT FALSE,
    user_id INT,
    FOREIGN KEY (threat_id) REFERENCES threats(threat_id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

-- Create the feedback table
CREATE TABLE feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    message TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

-- Insert sample users
-- Password: 1234567890
INSERT INTO users (username, password, role, streak, password_length) VALUES 
('admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPkU2Cslq', 'admin', 5, 10),
('vishal', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPkU2Cslq', 'user', 3, 10),
('john', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPkU2Cslq', 'user', 7, 10),
('alice', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPkU2Cslq', 'user', 2, 10),
('bob', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPkU2Cslq', 'user', 4, 10);

-- Insert sample threats
INSERT INTO threats (name, description, severity) VALUES 
('SQL Injection', 'An attacker can manipulate SQL queries by injecting malicious code', 'High'),
('Cross-Site Scripting (XSS)', 'An attacker can inject malicious scripts into web pages viewed by other users', 'Medium'),
('Phishing Attack', 'An attacker attempts to obtain sensitive information by disguising as a trustworthy entity', 'Medium'),
('Denial of Service (DoS)', 'An attacker makes a machine or network resource unavailable to users', 'High'),
('Malware', 'Software specifically designed to disrupt, damage, or gain unauthorized access to a system', 'High'),
('Brute Force Attack', 'An attacker uses trial and error to guess login info, encryption keys, etc.', 'Low'),
('Man-in-the-Middle', 'An attacker secretly relays and possibly alters the communication between two parties', 'Medium'),
('Zero-Day Exploit', 'An attack that takes advantage of a security vulnerability on the same day it becomes known', 'High');

-- Insert sample alerts log
INSERT INTO alerts_log (timestamp, severity, threat_id, acknowledged, user_id) VALUES 
('2023-01-15 10:30:45', 'High', 1, FALSE, 1),
('2023-01-16 14:22:33', 'Medium', 2, FALSE, 2),
('2023-01-17 09:15:12', 'Medium', 3, FALSE, 3),
('2023-01-18 16:45:21', 'High', 4, FALSE, 1),
('2023-01-19 11:33:44', 'High', 5, FALSE, 4),
('2023-01-20 13:12:56', 'Low', 6, FALSE, 2),
('2023-01-21 15:40:23', 'Medium', 7, FALSE, 5),
('2023-01-22 10:05:17', 'High', 8, FALSE, 3),
('2023-01-23 12:28:39', 'Medium', 1, TRUE, 1),
('2023-01-24 14:55:12', 'Low', 2, TRUE, 2),
('2023-01-25 09:48:33', 'High', 3, TRUE, 4),
('2023-01-26 11:20:45', 'Medium', 4, TRUE, 5),
('2023-01-27 13:37:28', 'Low', 5, FALSE, 1),
('2023-01-28 15:12:54', 'High', 6, FALSE, 3),
('2023-01-29 10:45:21', 'Medium', 7, FALSE, 2),
('2023-01-30 12:33:47', 'High', 8, FALSE, 4);

-- Insert sample feedback
INSERT INTO feedback (user_id, message, timestamp) VALUES 
(2, 'The system is very effective in detecting threats. Great job!', '2023-01-20 14:30:22'),
(3, 'I would like to see more detailed reports in the future.', '2023-01-21 10:15:45'),
(4, 'The user interface could be more intuitive. Overall satisfied with the functionality.', '2023-01-22 16:45:33'),
(5, 'Excellent threat detection capabilities. Helped us prevent several security incidents.', '2023-01-23 11:20:17'),
(1, 'As an admin, I find the system very useful for monitoring security threats.', '2023-01-24 13:55:42);

-- Verify all data was inserted correctly
SELECT 'Users Table:' as table_name;
SELECT * FROM users;

SELECT 'Threats Table:' as table_name;
SELECT * FROM threats;

SELECT 'Alerts Log Table:' as table_name;
SELECT * FROM alerts_log;

SELECT 'Feedback Table:' as table_name;
SELECT * FROM feedback;