-- =====================================================================
-- CampusConnect Database Schema
-- Database: campusconnect
-- =====================================================================

CREATE DATABASE IF NOT EXISTS `campusconnect`;
USE `campusconnect`;

-- Disable foreign key checks during creation
SET FOREIGN_KEY_CHECKS = 0;

-- ---------------------------------------------------------------------
-- Table: clubs
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
-- Table: events
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
-- Table: registrations
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

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;
