# Voll.Med API 🩺

![Status](https://img.shields.io/badge/status-completed-success?style=for-the-badge)
![Language](https://img.shields.io/badge/java-17-blue?style=for-the-badge)
![Framework](https://img.shields.io/badge/spring_boot-3-green?style=for-the-badge)

A complete RESTful API for a fictional medical clinic called Voll.Med. This project was developed as part of Alura's Spring Boot course to practice and demonstrate key concepts in building robust backend applications. The API handles the management of doctors, patients, and appointments, including business rule validations and secure authentication.

---

## ✨ Key Features

- **Doctor Management:** CRUD operations for doctors, with the ability to register, update, list, and delete records.
- **Patient Management:** CRUD operations for patients.
- **Appointment Scheduling:** An endpoint to schedule new appointments, including several business rule validations (e.g., doctor availability, advance notice).
- **Secure Authentication:** JWT (JSON Web Token) based authentication to secure the API endpoints.
- **Database Migrations:** Use of Flyway for managing database schema evolution in a safe and versioned manner.

---

## 🛠️ Tech Stack

This project was built with a modern and robust set of technologies:

| Backend                                                                                                  | Database                                                                                                   | Tools & Others                                                                                                  |
| -------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="24"/> **Java 17** | <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" width="24"/> **MySQL** | <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/maven/maven-original.svg" width="24"/> **Maven** |
| <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="24"/> **Spring Boot 3** | <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/hibernate/hibernate-original.svg" width="24"/> **JPA/Hibernate** | <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" width="24"/> **Git & GitHub** |
| <img src="https://img.icons8.com/color/48/spring-security.png" width="24"/> **Spring Security** | <img src="https://flywaydb.org/assets/flyway-logo.png" width="24"/> **Flyway** | <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg" width="24"/> **IntelliJ** |
|                                                                                                          |                                                                                                            |                                                                                                                   |

---

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
- [Maven 3.8](https://maven.apache.org/download.cgi) or higher
- A running instance of [MySQL](https://dev.mysql.com/downloads/mysql/)

### Installation & Setup

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/Sarah-Ribeiro/api-voll-med-main.git
    cd api-voll-med-main
    ```

2.  **Configure Environment Variables:**
    This project requires the following environment variables to connect to the database. You can set them in your operating system or directly in your IDE's run configuration.

    - `DATASOURCE_URL`: The JDBC URL for your MySQL database (e.g., `jdbc:mysql://localhost/vollmed_api`)
    - `DATASOURCE_USERNAME`: Your MySQL username (e.g., `root`)
    - `DATASOURCE_PASSWORD`: Your MySQL password

3.  **Run the application:**
    Use the Spring Boot Maven plugin to start the application.
    ```sh
    mvn spring-boot:run
    ```
    The API will be available at `http://localhost:8080`.

---

## 📝 API Endpoints

A brief overview of the available endpoints:

- `POST /login` - Authenticate a user and receive a JWT token.
- `GET /medicos` - List all active doctors with pagination.
- `POST /medicos` - Register a new doctor.
- `PUT /medicos` - Update an existing doctor's information.
- `DELETE /medicos/{id}` - Logically delete a doctor (inactive).
- `POST /appointments` - Schedule a new appointment.

... and many more.

---

*Project developed based on the Alura course "Spring Boot 3: desenvolva uma API Rest em Java".*
