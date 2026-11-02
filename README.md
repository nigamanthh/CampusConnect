# CampusConnect Backend

A robust, RESTful backend service for **CampusConnect**, a campus event and club management platform built with **Java 21**, **Spring Boot**, and **MySQL**.

---

## Table of Contents
1. [Overview](#overview)
2. [Tech Stack & Prerequisites](#tech-stack--prerequisites)
3. [Project Structure](#project-structure)
4. [Database Setup](#database-setup)
5. [Environment & Security Configuration](#environment--security-configuration)
6. [Building and Running the Application](#building-and-running-the-application)
7. [API Documentation](#api-documentation)
   - [Clubs Module](#1-clubs-module)
   - [Events Module](#2-events-module)
   - [Registrations Module](#3-registrations-module)
8. [Business Logic & Validations](#business-logic--validations)
9. [Testing with Postman](#testing-with-postman)
10. [Git & Branching Workflow](#git--branching-workflow)

---

## Overview

CampusConnect provides a centralized backend to power university campus activities. The backend manages:
- **Clubs**: Query campus clubs and contact details.
- **Events**: Full CRUD functionality for scheduling, updating, and tracking club events with category, venue, date, and seat capacities.
- **Registrations**: Event registration engine with validation logic for event existence, participant duplicate prevention, capacity enforcement, and registration cancellation.

---

## Tech Stack & Prerequisites

- **Java**: Version 21 (JDK 21)
- **Framework**: Spring Boot 4.1.2
  - `spring-boot-starter-webmvc` (REST APIs)
  - `spring-boot-starter-data-jdbc` (Spring Data JDBC / `JdbcTemplate`)
- **Database Driver**: `mysql-connector-j` (MySQL 8.0+)
- **Build Tool**: Apache Maven (Maven Wrapper included)
- **Database**: MySQL Server 8.0 or compatible instance

---

## Project Structure

```
campusconnect/
├── .mvn/wrapper/                 # Maven wrapper files
├── database/
│   ├── campusconnect_setup.sql   # All-in-one database creation & seed data script
│   ├── schema.sql                # Table definitions, keys, and indexes
│   └── data.sql                  # Seed data for clubs, events, and registrations
├── postman/
│   └── CampusConnect.postman_collection.json # Ready-to-import Postman test collection
├── src/
│   ├── main/
│   │   ├── java/com/campusconnect/campusconnect/
│   │   │   ├── controller/       # REST controllers (Club, Event, Registration)
│   │   │   ├── model/            # Domain models (Club, Event, Registration)
│   │   │   ├── repository/       # Data access repositories (JdbcTemplate)
│   │   │   └── CampusconnectApplication.java # Spring Boot entry point
│   │   └── resources/
│   │       └── application.properties # App configuration with environment variables
│   └── test/
│       └── java/com/campusconnect/campusconnect/
├── .env.example                  # Template for local environment variables
├── .gitignore                    # Comprehensive ignore rules (excluding secrets, build files)
├── mvnw / mvnw.cmd               # Maven wrapper executables
├── pom.xml                       # Maven project dependencies
└── README.md                     # Backend documentation
```

---

## Database Setup

The backend expects a MySQL database named `campusconnect`.

### Option A: Complete One-Step Setup (Recommended)
Execute `database/campusconnect_setup.sql` in MySQL CLI or MySQL Workbench:

```bash
# Using MySQL Command Line Client:
mysql -u <your_mysql_username> -p < database/campusconnect_setup.sql
```

### Option B: Step-by-Step Setup
1. Create schema, tables, foreign keys, and indexes:
   ```bash
   mysql -u <your_mysql_username> -p < database/schema.sql
   ```
2. Insert sample/seed data:
   ```bash
   mysql -u <your_mysql_username> -p < database/data.sql
   ```

### Schema Summary
- **`clubs`**: `club_id` (PK), `club_name`, `description_of_club`, `contact_email`.
- **`events`**: `event_id` (PK), `club_id` (FK to `clubs`), `event_name`, `title`, `description_of_event`, `category`, `event_date`, `venue`, `capacity`, `status_of_event`.
- **`registrations`**: `registration_id` (PK), `event_id` (FK to `events`), `student_name`, `student_email`, `registration_date` (DEFAULT CURRENT_TIMESTAMP), `status`. Index on `idx_registrations_event_id`.

---

## Environment & Security Configuration

No database passwords or sensitive credentials are stored in source code or `application.properties`. 

Credentials are read dynamically using environment variables:
- `DB_USERNAME`: Your MySQL username (defaults to `root` if unset).
- `DB_PASSWORD`: Your MySQL password.

### Setting Environment Variables

#### On Windows (PowerShell):
```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_actual_password"
```

#### On Windows (Command Prompt):
```cmd
set DB_USERNAME=root
set DB_PASSWORD=your_actual_password
```

#### On Linux / macOS (Bash / Zsh):
```bash
export DB_USERNAME="root"
export DB_PASSWORD="your_actual_password"
```

#### In IDEs:
- **IntelliJ IDEA**: Run > Edit Configurations > Application > Modify options > Environment variables > Enter `DB_USERNAME=root;DB_PASSWORD=your_password`.
- **VS Code**: In `.vscode/launch.json`, add `"env": { "DB_USERNAME": "root", "DB_PASSWORD": "your_password" }`.

---

## Building and Running the Application

### 1. Compile and Verify Build
Using Maven wrapper:
```bash
# On Windows:
.\mvnw.cmd clean compile

# On Linux / macOS:
./mvnw clean compile
```

### 2. Run the Application
```bash
# On Windows:
.\mvnw.cmd spring-boot:run

# On Linux / macOS:
./mvnw spring-boot:run
```

Once running, the backend server will listen on port **`8080`**:
`http://localhost:8080`

---

## API Documentation

### Base URL
`http://localhost:8080`

---

### 1. Clubs Module

#### `GET /api/clubs`
Fetch all registered campus clubs.

- **Method**: `GET`
- **Request Body**: None
- **Response**: `200 OK`
```json
[
  {
    "clubId": 1,
    "clubName": "SNUC Coding Club",
    "description": "Technical workshops and coding competitions",
    "contactEmail": "coding@snuchennai.edu.in"
  },
  {
    "clubId": 2,
    "clubName": "SNUC Capturesque",
    "description": "Photography events and exhibitions",
    "contactEmail": "capturesque@snuchennai.edu.in"
  }
]
```

---

### 2. Events Module

#### `GET /api/events`
Fetch all scheduled events across all clubs.

- **Method**: `GET`
- **Request Body**: None
- **Response**: `200 OK`
```json
[
  {
    "event_id": 101,
    "club_id": 1,
    "event_name": "Codeathon 2026",
    "title": "Codeathon 2026",
    "description_of_event": "A coding competition for students",
    "category": "Technical",
    "event_date": "2026-11-20T10:00:00",
    "venue": "Auditorium",
    "capacity": 100,
    "status_of_event": "open"
  }
]
```

#### `GET /api/events/{id}`
Fetch single event details by its ID.

- **Method**: `GET`
- **Path Variable**: `id` (integer, e.g., `101`)
- **Response**: `200 OK`
```json
{
  "event_id": 101,
  "club_id": 1,
  "event_name": "Codeathon 2026",
  "title": "Codeathon 2026",
  "description_of_event": "A coding competition for students",
  "category": "Technical",
  "event_date": "2026-11-20T10:00:00",
  "venue": "Auditorium",
  "capacity": 100,
  "status_of_event": "open"
}
```

#### `POST /api/events`
Create a new event.

- **Method**: `POST`
- **Headers**: `Content-Type: application/json`
- **Request Body**:
```json
{
  "event_id": 109,
  "club_id": 1,
  "event_name": "Hackathon 2026",
  "title": "Hackathon 2026",
  "description_of_event": "24-hour hackathon for building campus solutions",
  "category": "Technical",
  "event_date": "2026-12-10T09:00:00",
  "venue": "Main Lab",
  "capacity": 80,
  "status_of_event": "open"
}
```
- **Response**: `200 OK`
```
Event added successfully
```

#### `PUT /api/events/{id}`
Update an existing event.

- **Method**: `PUT`
- **Path Variable**: `id` (integer, e.g., `109`)
- **Headers**: `Content-Type: application/json`
- **Request Body**:
```json
{
  "club_id": 1,
  "event_name": "Hackathon 2026 - Updated",
  "title": "Hackathon 2026",
  "description_of_event": "Updated event description with cash prizes",
  "category": "Technical",
  "event_date": "2026-12-10T09:00:00",
  "venue": "Main Auditorium",
  "capacity": 100,
  "status_of_event": "open"
}
```
- **Response**: `200 OK`
```
Event updated successfully
```

#### `DELETE /api/events/{id}`
Delete an event by its ID.

- **Method**: `DELETE`
- **Path Variable**: `id` (integer, e.g., `109`)
- **Response**: `200 OK`
```
Event deleted successfully
```

---

### 3. Registrations Module

#### `GET /api/registrations`
Retrieve all student registrations.

- **Method**: `GET`
- **Request Body**: None
- **Response**: `200 OK`
```json
[
  {
    "registration_id": 1,
    "event_id": 101,
    "student_name": "Arun Kumar",
    "student_email": "arun@gmail.com",
    "registration_date": "2026-10-31T21:14:36",
    "status": "REGISTERED"
  }
]
```

#### `GET /api/registrations/event/{event_id}`
Retrieve all participants registered for a specific event.

- **Method**: `GET`
- **Path Variable**: `event_id` (integer, e.g., `101`)
- **Response**: `200 OK`
```json
[
  {
    "registration_id": 1,
    "event_id": 101,
    "student_name": "Arun Kumar",
    "student_email": "arun@gmail.com",
    "registration_date": "2026-10-31T21:14:36",
    "status": "REGISTERED"
  },
  {
    "registration_id": 2,
    "event_id": 101,
    "student_name": "Rahul Sharma",
    "student_email": "rahul@gmail.com",
    "registration_date": "2026-10-31T21:14:36",
    "status": "REGISTERED"
  }
]
```

#### `POST /api/registrations`
Register a student for an event.

- **Method**: `POST`
- **Headers**: `Content-Type: application/json`
- **Request Body**:
```json
{
  "registration_id": 13,
  "event_id": 101,
  "student_name": "Nigamanth",
  "student_email": "nigamanth@example.com"
}
```
- **Success Response**: `201 Created`
```
Registration successful
```
- **Error Responses**:
  - `400 Bad Request`: `"Student name and email are required"` (if either is missing or blank).
  - `404 Not Found`: `"Event does not exist"` (if `event_id` is invalid).
  - `409 Conflict`: `"Student already registered for this event"` (if student already has an active registration).
  - `409 Conflict`: `"Event is full"` (if active registrations reach or exceed capacity).

#### `PUT /api/registrations/cancel/{registration_id}`
Cancel an active registration.

- **Method**: `PUT`
- **Path Variable**: `registration_id` (integer, e.g., `13`)
- **Success Response**: `200 OK`
```
Registration cancelled successfully
```
- **Error Response**:
  - `404 Not Found`: `"Registration does not exist"` (if ID not found or already cancelled).

---

## Business Logic & Validations

1. **Event Existence Check**: Validates that target `event_id` exists before permitting registration.
2. **Input Validation**: Ensures `student_name` and `student_email` are not null and not blank.
3. **Duplicate Prevention**: Rejects duplicate registrations for the same student email and event where the status is active (`REGISTERED`).
4. **Capacity Enforcement**: Dynamically counts active registrations (`status = 'REGISTERED'`) and compares against the event's configured capacity. If `count >= capacity`, registration is blocked.
5. **Safe Cancellation**: Cancelling an event updates the status to `CANCELLED` and does not delete records, preserving audit history and freeing up capacity.

---

## Testing with Postman

A pre-configured Postman collection is included in:
`postman/CampusConnect.postman_collection.json`

To import and use:
1. Open **Postman**.
2. Click **Import** > Select `postman/CampusConnect.postman_collection.json`.
3. The collection includes a `baseUrl` collection variable configured to `http://localhost:8080`.
4. Run requests across Clubs, Events, and Registrations.

---

## Git & Branching Workflow

This backend is version-controlled on the **`backend`** branch:
- **Repository**: [https://github.com/nigamanthh/CampusConnect](https://github.com/nigamanthh/CampusConnect)
- **Branch**: `backend`

To pull and work on the backend branch:
```bash
git clone -b backend https://github.com/nigamanthh/CampusConnect.git
```
