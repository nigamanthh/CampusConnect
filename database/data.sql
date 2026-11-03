-- =====================================================================
-- CampusConnect Seed / Sample Data
-- Database: campusconnect
-- =====================================================================

USE `campusconnect`;

-- Disable foreign key checks during data insertion
SET FOREIGN_KEY_CHECKS = 0;

-- ---------------------------------------------------------------------
-- Seed Data: clubs
-- ---------------------------------------------------------------------
INSERT INTO `clubs` (`club_id`, `club_name`, `description_of_club`, `contact_email`) VALUES
(1, 'SNUC Coding Club', 'Technical workshops and coding competitions', 'coding@snuchennai.edu.in'),
(2, 'SNUC Capturesque', 'Photography events and exhibitions', 'capturesque@snuchennai.edu.in'),
(3, 'SNUC Business Club', 'Entrepreneurship and management events', 'businessclub@snuchennai.edu.in'),
(4, 'SNUC Aura', 'Creative design, UI/UX and graphic design activities', 'aura@snuchennai.edu.in'),
(5, 'SNUC Ameya', 'Classical dance performances and workshops', 'ameya@snu.edu.in')
ON DUPLICATE KEY UPDATE `club_name`=VALUES(`club_name`), `description_of_club`=VALUES(`description_of_club`), `contact_email`=VALUES(`contact_email`);

-- ---------------------------------------------------------------------
-- Seed Data: events
-- ---------------------------------------------------------------------
INSERT INTO `events` (`event_id`, `club_id`, `event_name`, `title`, `description_of_event`, `category`, `event_date`, `venue`, `capacity`, `status_of_event`) VALUES
(101, 1, 'Codeathon 2026', 'Codeathon 2026', 'A coding competition for students', 'Technical', '2026-11-20 10:00:00', 'Auditorium', 100, 'open'),
(102, 1, 'Web Development Workshop', 'Web Development', 'Hands-on HTML, CSS and JavaScript workshop', 'Workshop', '2026-11-20 10:00:00', 'Computer Lab 1', 50, 'open'),
(103, 2, 'Campus Photography Contest', 'Photography Contest', 'Photography competition based on campus life', 'Competition', '2026-11-18 11:00:00', 'Seminar Hall', 40, 'open'),
(104, 3, 'Startup Pitch Day', 'Startup Pitch', 'Present innovative startup ideas', 'Business', '2026-11-25 14:00:00', 'Conference Hall', 60, 'open'),
(105, 4, 'Design Thinking Workshop', 'UI UX Design', 'A workshop on creative problem solving and UI UX design', 'Workshop', '2026-11-22 10:00:00', 'Design Lab', 50, 'open'),
(106, 4, 'Poster Design Competition', 'Graphic Design', 'A creative graphic design competition', 'Competition', '2026-11-27 11:00:00', 'Seminar Hall', 40, 'open'),
(107, 5, 'Classical Dance Showcase', 'Dance Performance', 'A showcase of classical dance performances', 'Cultural', '2026-12-01 17:00:00', 'Open Auditorium', 200, 'open'),
(108, 5, 'Classical Dance Workshop', 'Dance Workshop', 'A beginner friendly classical dance workshop', 'Workshop', '2026-12-05 10:00:00', 'Dance Studio', 30, 'open')
ON DUPLICATE KEY UPDATE `event_name`=VALUES(`event_name`), `title`=VALUES(`title`), `description_of_event`=VALUES(`description_of_event`), `category`=VALUES(`category`), `event_date`=VALUES(`event_date`), `venue`=VALUES(`venue`), `capacity`=VALUES(`capacity`), `status_of_event`=VALUES(`status_of_event`);

-- ---------------------------------------------------------------------
-- Seed Data: registrations
-- ---------------------------------------------------------------------
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
(12, 101, 'Test Student', 'capacity_test@gmail.com', '2026-11-02 21:54:07', 'REGISTERED')
ON DUPLICATE KEY UPDATE `student_name`=VALUES(`student_name`), `student_email`=VALUES(`student_email`), `status`=VALUES(`status`);

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

