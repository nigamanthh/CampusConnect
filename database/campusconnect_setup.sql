-- =====================================================================
-- CampusConnect Complete Database Setup Script
-- Recreates database, tables, relationships, indexes, and sample data.
-- =====================================================================

CREATE DATABASE IF NOT EXISTS `campusconnect`;
USE `campusconnect`;

SET FOREIGN_KEY_CHECKS = 0;

-- ---------------------------------------------------------------------
-- 1. CLUBS TABLE
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS `clubs`;
CREATE TABLE `clubs` (
  `club_id` INT NOT NULL,
  `club_name` VARCHAR(100) DEFAULT NULL,
  `description_of_club` VARCHAR(500) DEFAULT NULL,
  `contact_email` VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`club_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- 2. EVENTS TABLE
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS `events`;
CREATE TABLE `events` (
  `event_id` INT NOT NULL,
  `club_id` INT DEFAULT NULL,
  `event_name` VARCHAR(100) DEFAULT NULL,
  `title` VARCHAR(50) DEFAULT NULL,
  `description_of_event` TEXT,
  `category` VARCHAR(50) DEFAULT NULL,
  `event_date` DATETIME DEFAULT NULL,
  `venue` VARCHAR(150) DEFAULT NULL,
  `capacity` INT NOT NULL,
  `status_of_event` VARCHAR(50) DEFAULT 'open',
  PRIMARY KEY (`event_id`),
  KEY `club_id` (`club_id`),
  CONSTRAINT `events_ibfk_1` FOREIGN KEY (`club_id`) REFERENCES `clubs` (`club_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- 3. REGISTRATIONS TABLE
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS `registrations`;
CREATE TABLE `registrations` (
  `registration_id` INT NOT NULL,
  `event_id` INT NOT NULL,
  `student_name` VARCHAR(100) NOT NULL,
  `student_email` VARCHAR(100) NOT NULL,
  `registration_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `status` VARCHAR(30) DEFAULT 'REGISTERED',
  PRIMARY KEY (`registration_id`),
  KEY `idx_registrations_event_id` (`event_id`),
  CONSTRAINT `registrations_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =====================================================================
-- SAMPLE DATA
-- =====================================================================

-- Clubs Seed Data
INSERT INTO `clubs` (`club_id`, `club_name`, `description_of_club`, `contact_email`) VALUES
(1, 'SNUC Coding Club', 'Technical workshops and coding competitions', 'coding@snuchennai.edu.in'),
(2, 'SNUC Capturesque', 'Photography events and exhibitions', 'capturesque@snuchennai.edu.in'),
(3, 'SNUC Business Club', 'Entrepreneurship and management events', 'businessclub@snuchennai.edu.in'),
(4, 'SNUC Aura', 'Creative design, UI/UX and graphic design activities', 'aura@snuchennai.edu.in'),
(5, 'SNUC Ameya', 'Classical dance performances and workshops', 'ameya@snu.edu.in');

-- Events Seed Data
INSERT INTO `events` (`event_id`, `club_id`, `event_name`, `title`, `description_of_event`, `category`, `event_date`, `venue`, `capacity`, `status_of_event`) VALUES
(101, 1, 'Codeathon 2026', 'Codeathon 2026', 'A coding competition for students', 'Technical', '2026-11-20 10:00:00', 'Auditorium', 100, 'open'),
(102, 1, 'Web Development Workshop', 'Web Development', 'Hands-on HTML, CSS and JavaScript workshop', 'Workshop', '2026-11-20 10:00:00', 'Computer Lab 1', 50, 'open'),
(103, 2, 'Campus Photography Contest', 'Photography Contest', 'Photography competition based on campus life', 'Competition', '2026-11-18 11:00:00', 'Seminar Hall', 40, 'open'),
(104, 3, 'Startup Pitch Day', 'Startup Pitch', 'Present innovative startup ideas', 'Business', '2026-11-25 14:00:00', 'Conference Hall', 60, 'open'),
(105, 4, 'Design Thinking Workshop', 'UI UX Design', 'A workshop on creative problem solving and UI UX design', 'Workshop', '2026-11-22 10:00:00', 'Design Lab', 50, 'open'),
(106, 4, 'Poster Design Competition', 'Graphic Design', 'A creative graphic design competition', 'Competition', '2026-11-27 11:00:00', 'Seminar Hall', 40, 'open'),
(107, 5, 'Classical Dance Showcase', 'Dance Performance', 'A showcase of classical dance performances', 'Cultural', '2026-12-01 17:00:00', 'Open Auditorium', 200, 'open'),
(108, 5, 'Classical Dance Workshop', 'Dance Workshop', 'A beginner friendly classical dance workshop', 'Workshop', '2026-12-05 10:00:00', 'Dance Studio', 30, 'open');

-- Registrations Seed Data
INSERT INTO `registrations` (`registration_id`, `event_id`, `student_name`, `student_email`, `registration_date`, `status`) VALUES
(1, 101, 'Arun Kumar', 'arun@gmail.com', '2026-10-31 21:14:36', 'REGISTERED'),
(2, 101, 'Rahul Sharma', 'rahul@gmail.com', '2026-10-31 21:14:36', 'REGISTERED'),
(3, 102, 'Priya S', 'priya@gmail.com', '2026-10-31 21:14:36', 'REGISTERED'),
(4, 103, 'Karthik R', 'karthik@gmail.com', '2026-10-31 21:14:36', 'REGISTERED'),
(5, 104, 'Sneha M', 'sneha@gmail.com', '2026-10-31 21:14:36', 'REGISTERED'),
(6, 105, 'Vikram N', 'vikram@gmail.com', '2026-10-31 21:14:36', 'REGISTERED'),
(7, 106, 'Ananya R', 'ananya@gmail.com', '2026-10-31 21:14:36', 'REGISTERED'),
(8, 107, 'Divya R', 'divya@gmail.com', '2026-10-31 21:14:36', 'REGISTERED'),
(9, 108, 'Harish V', 'harish@gmail.com', '2026-10-31 21:14:36', 'REGISTERED'),
(10, 101, 'Rahul', 'rahul_test@gmail.com', '2026-11-02 21:44:40', 'CANCELLED'),
(11, 101, 'Rahul', 'rahul_test@gmail.com', '2026-11-02 21:50:43', 'REGISTERED'),
(12, 101, 'Test Student', 'capacity_test@gmail.com', '2026-11-02 21:54:07', 'REGISTERED');

SET FOREIGN_KEY_CHECKS = 1;

