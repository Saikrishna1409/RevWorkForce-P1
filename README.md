# RevWorkforce

**RevWorkforce** is a robust, Java-based console application designed to streamline organizational workflows. By centralizing employee management, performance tracking, and leave administration, it provides a unified platform for employees, managers, and administrators.

---

## 🏗️ Architecture & Design
The system follows a modular **DAO-Service-Controller** (subset of MVC) pattern to ensure high maintainability and separation of concerns:

* **Controller Layer:** Handles user interaction and console input/output.
* **Service Layer:** Processes business logic, data validation, and role-based permissions.
* **DAO (Data Access Object) Layer:** Manages direct communication with the MySQL database.
* **Security Layer:** Implements **BCrypt** hashing for credential protection and **Log4j2** for comprehensive audit trails.

---

## 🛠️ Technology Stack
| Category | Technology |
| :--- | :--- |
| **Language** | Java 17 (LTS) |
| **Database** | MySQL 8.x |
| **Logging** | Log4j2 |
| **Security** | BCrypt Password Hashing |
| **Build Tool** | Apache Maven |
| **Version Control** | Git & GitHub |

---

## 🔑 Key Features

### 👤 Role-Based Functionalities
The system adapts its interface and permissions based on the logged-in user:
* **Admin:** Full system oversight, user provisioning, and access to secure audit logs.
* **Manager:** Performance evaluation, goal setting for teams, and leave request approvals.
* **Employee:** Personal profile management, leave application tracking, and goal monitoring.

### 📋 Core Modules
* **Employee Management:** CRUD operations for staff records and department assignments.
* **Leave Management:** Automated workflows for requesting and approving time off.
* **Performance Tracking:** Quantitative and qualitative performance reviews.
* **Goal Setting:** Strategic objective alignment at both individual and team levels.
* **Reporting:** Generation of organizational insights and activity reports.

---

## 📂 Project Structure
```text
src/main/java/com/revworkforce/
├── controller/    # Console menus and user input handling
├── service/       # Business logic and validation
├── dao/           # Database interaction logic
├── model/         # Data entities (POJOs)
├── util/          # Database connection and security utilities
└── exception/     # Custom application exceptions
