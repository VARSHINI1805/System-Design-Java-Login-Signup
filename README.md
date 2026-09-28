# System Design Java - Login & Signup

A Java-based Login and Signup System developed to understand **System Design, Object-Oriented Programming, Layered Architecture, Design Patterns, and Authentication concepts**.

This project starts with a simple in-memory implementation using `HashMap` and is designed to be extended gradually with advanced system design concepts such as Redis, Kafka, Rate Limiting, CQRS, Circuit Breaker, and Microservices.

---

## 📌 Project Overview

This project implements a basic user authentication system with the following features:

- User Signup
- User Login
- Email Validation
- Password Validation
- Duplicate User ID Prevention
- In-memory user storage using `HashMap`
- Singleton Pattern
- Repository Pattern
- Controller-Service-Repository architecture
- Input validation
- Role-based user information

The current implementation does not use MySQL, JPA, Hibernate, or any external database. User data is temporarily stored in a Java `HashMap`.

---

## 🏗️ Architecture

The application follows a layered architecture:

```text
                    Main
                     |
                     v
              UserController
                     |
                     v
                UserService
                     |
                     v
              UserRepository
                     |
                     v
             HashMap<Integer, User>
```

### Request Flow

```text
Client / Main
     |
     v
Controller
     |
     v
Service
     |
     v
Repository
     |
     v
HashMap
```

Each layer has a separate responsibility.

---

## 📁 Project Structure

```text
System-Design-Java-Login-Signup
|
|-- src
|   |
|   |-- main
|       |
|       |-- java
|           |
|           |-- org.example
|               |
|               |-- Main.java
|               |
|               |-- controller
|               |   |
|               |   |-- UserController.java
|               |
|               |-- dto
|               |   |
|               |   |-- User.java
|               |
|               |-- repository
|               |   |
|               |   |-- UserRepository.java
|               |
|               |-- service
|                   |
|                   |-- UserService.java
|
|-- pom.xml
|-- .gitignore
|-- README.md
```

---

# 👤 User Model

The `User` class contains the following fields:

```text
id
email
password
role
```

Example:

```java
User user = new User(
        1,
        "varshini@gmail.com",
        "Varshini@123",
        "USER"
);
```

### User Fields

| Field | Description |
|---|---|
| `id` | Unique identifier for the user |
| `email` | User email address |
| `password` | User password |
| `role` | User role such as USER or ADMIN |

---

# 📝 Signup

The signup process validates the user details before storing the user.

### Signup Flow

```text
User Signup
     |
     v
UserController
     |
     v
UserService
     |
     +----------------------+
     |                      |
     v                      v
Email Validation      Password Validation
     |                      |
     +----------+-----------+
                |
                v
         UserRepository
                |
                v
        HashMap<Integer, User>
```

If the email and password are valid, the user is added to the repository.

If validation fails, signup returns `false`.

---

# 📧 Email Validation

The application validates Gmail addresses using a regular expression.

```text
^[A-Za-z0-9_.-]+@gmail\.com$
```

### Explanation

```text
^
```

Start of the string.

```text
[A-Za-z0-9_.-]+
```

Allows letters, numbers, underscore, dot, and hyphen in the username.

```text
@gmail
```

Requires the Gmail domain.

```text
\.com
```

Requires the actual `.com` extension.

```text
$
```

End of the string.

### Examples

```text
varshini@gmail.com        -> Valid
varshini123@gmail.com     -> Valid
varshini.s@gmail.com      -> Valid
varshini_s@gmail.com      -> Valid
varshini@yahoo.com        -> Invalid
varshini@gmailcom         -> Invalid
@gmail.com                -> Invalid
```

---

# 🔐 Password Validation

The password validation checks the following conditions:

- Minimum 8 characters
- At least one letter
- At least one number
- At least one special character

The regular expression used is:

```text
^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$
```

### Password Examples

```text
Varshini@123     -> Valid
Java#2026        -> Valid
Hello@123        -> Valid
Password123      -> Invalid
password@123     -> Valid
Pass@12          -> Invalid
```

---

# 🔓 Login

The login process uses the email to find the corresponding user and then compares the entered password with the stored password.

### Login Flow

```text
Login Request
      |
      v
UserController
      |
      v
UserService
      |
      v
Find User By Email
      |
      v
User Found?
   /       \
 No         Yes
 |           |
 v           v
False    Compare Password
             |
        +----+----+
        |         |
     Match     Not Match
        |         |
        v         v
      True      False
```

---

# 🗄️ Data Storage

Instead of using a database, this project currently uses:

```java
HashMap<Integer, User>
```

The user ID is used as the key and the `User` object is stored as the value.

Example:

```text
HashMap

1 -> User
2 -> User
3 -> User
```

Conceptually:

```text
+-----+----------------------+
| ID  | User                 |
+-----+----------------------+
| 1   | varshini@gmail.com   |
| 2   | user2@gmail.com      |
| 3   | user3@gmail.com      |
+-----+----------------------+
```

### Important

This is an **in-memory implementation**.

Therefore, the data is not permanently stored and can be lost when the application stops.

---

# 🧩 Design Patterns

## 1. Singleton Pattern

The `UserRepository` uses the Singleton Pattern.

```java
UserRepository.getInstance();
```

The repository constructor is private and only one repository instance is created.

```text
UserService
     |
     v
UserRepository.getInstance()
     |
     v
Single Repository Instance
     |
     v
HashMap
```

### Purpose

The Singleton Pattern ensures that the application uses a single repository instance.

---

## 2. Repository Pattern

The Repository layer is responsible for managing user data.

```text
UserService
     |
     v
UserRepository
     |
     v
HashMap
```

The Service layer does not directly manage the HashMap.

The Repository provides methods for adding, finding, deleting, and retrieving users.

---

## 3. Layered Architecture

The project separates responsibilities into different layers.

```text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
Data Storage
```

### Controller

Handles requests and communicates with the Service layer.

### Service

Contains business logic and validation.

### Repository

Manages data storage and retrieval.

### DTO

Contains the user data structure.

---

# 🔄 Complete Signup Flow

```text
Main
 |
 v
UserController.signup(user)
 |
 v
UserService.signup(user)
 |
 +--> validateEmail()
 |
 +--> validatePassword()
 |
 v
UserRepository.add(user)
 |
 v
HashMap.put(id, user)
 |
 v
Signup Result
```

---

# 🔄 Complete Login Flow

```text
Main
 |
 v
UserController.login(email, password)
 |
 v
UserService.login(email, password)
 |
 v
UserRepository.findEmail(email)
 |
 v
Find User
 |
 +---- User Not Found ------> false
 |
 v
Compare Password
 |
 +---- Wrong Password ------> false
 |
 v
Login Success
 |
 v
true
```

---

# 🛠️ Technologies Used

- Java
- Maven
- IntelliJ IDEA
- Git
- GitHub
- HashMap
- Object-Oriented Programming
- Regular Expressions

---

# 💻 Java Concepts Used

This project demonstrates several Java concepts:

- Classes and Objects
- Constructors
- Encapsulation
- Private fields
- Getters and Setters
- Methods
- Packages
- Interfaces and classes
- HashMap
- Singleton Pattern
- Regular Expressions
- Conditional Statements
- Loops
- Method Calls
- Layered Architecture

---

# 🧠 System Design Concepts

This project is being developed step by step to understand system design.

## Current Concepts

```text
Layered Architecture
Singleton Pattern
Repository Pattern
Authentication
Input Validation
In-Memory Storage
```

## Concepts to be Added

```text
Rate Limiting
Redis
Kafka
Publisher-Subscriber
CQRS
Memcached
DragonflyDB
Sidecar Pattern
Circuit Breaker
Caching
Database Integration
REST API
API Gateway
Microservices
```

---

# 🚦 Rate Limiting

Rate limiting can be added to control how many requests a user can make within a specific time period.

Example:

```text
5 login attempts per minute
```

Flow:

```text
Login Request
      |
      v
Rate Limiter
      |
      v
Request Allowed?
   /          \
 Yes           No
 |             |
 v             v
Login        Reject
```

Redis can later be used to implement distributed rate limiting.

---

# 🔴 Redis

Redis is an in-memory data store that can be used for:

- Caching
- Rate Limiting
- Session Management
- Fast data access
- Temporary data storage

Example rate-limiting concept:

```text
login:user@gmail.com -> 5
TTL -> 60 seconds
```

---

# 📨 Kafka

Apache Kafka can be added for event-driven communication.

Example:

```text
User Signup
     |
     v
User Service
     |
     v
Kafka Producer
     |
     v
user-created Topic
     |
     +------------+-------------+
     |            |             |
     v            v             v
Email Service  Analytics    Notification
```

Kafka allows services to communicate using events.

---

# 📢 Publisher-Subscriber

The Publisher-Subscriber pattern allows one service to publish an event while multiple subscribers can consume it.

Example:

```text
Publisher
    |
    v
user-created
    |
    +----------+----------+
    |          |          |
    v          v          v
 Email      Analytics   Notification
Service      Service      Service
```

---

# 🔀 CQRS

CQRS stands for:

```text
Command Query Responsibility Segregation
```

The main idea is to separate operations that change data from operations that read data.

```text
                 Application
                      |
             +--------+--------+
             |                 |
             v                 v
          Command            Query
          (Write)             (Read)
             |                 |
             v                 v
        Write Model        Read Model
```

Examples:

```text
Signup       -> Command
Update User  -> Command
Delete User  -> Command

Get User     -> Query
Search User  -> Query
```

---

# ⚡ Memcached

Memcached is a lightweight in-memory caching system.

It can be used to reduce database load by storing frequently accessed data in memory.

```text
Application
     |
     v
Memcached
     |
     +---- Cache Hit ----> Return Data
     |
     +---- Cache Miss ---> Database
```

---

# 🚀 DragonflyDB

DragonflyDB is a high-performance, Redis-compatible in-memory data store.

It can be explored as an alternative for:

- Caching
- Rate Limiting
- Session Storage
- Fast key-value operations

---

# 🛵 Sidecar Pattern

The Sidecar Pattern places a helper component alongside the main application.

```text
+--------------------------------+
|            Server              |
|                                |
|  +-------------+ +-----------+ |
|  | Application | |  Sidecar  | |
|  |             | |           | |
|  +-------------+ +-----------+ |
|                                |
+--------------------------------+
```

A sidecar can handle supporting functionality such as:

- Logging
- Monitoring
- Security
- Networking
- Service communication

---

# 🔌 Circuit Breaker

The Circuit Breaker pattern prevents repeated requests to a failing service.

It has three main states:

```text
CLOSED
   |
   | Too many failures
   v
OPEN
   |
   | Wait
   v
HALF-OPEN
   |
   +------ Success ------> CLOSED
   |
   +------ Failure ------> OPEN
```

### States

**CLOSED**

Requests are allowed normally.

**OPEN**

Requests are temporarily blocked.

**HALF-OPEN**

A test request is allowed to check whether the service has recovered.

---

# ▶️ How to Run

## Prerequisites

Install:

- Java JDK
- IntelliJ IDEA
- Maven
- Git

## Run Using IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Open `Main.java`.
3. Locate the `main()` method.
4. Click the green Run button.
5. Check the Run console.

---

# 🧪 Example Main Program

```java
UserController controller = new UserController();

User user = new User(
        1,
        "varshini@gmail.com",
        "Varshini@123",
        "USER"
);

boolean signupResult = controller.signup(user);

System.out.println("Signup: " + signupResult);

boolean loginResult = controller.login(
        "varshini@gmail.com",
        "Varshini@123"
);

System.out.println("Login: " + loginResult);
```

---

# ✅ Expected Output

```text
Signup: true
Login: true

Process finished with exit code 0
```

---

# 🔮 Future Improvements

The project can gradually be extended from a simple Java application into a more complete system design implementation.

### Phase 1 - Basic Authentication

```text
Java
  |
  +-- Controller
  +-- Service
  +-- Repository
  +-- HashMap
```

### Phase 2 - Database

```text
Java Application
      |
      v
Database
```

### Phase 3 - REST API

```text
Client
  |
  v
REST API
  |
  v
Service
  |
  v
Repository
```

### Phase 4 - Caching and Rate Limiting

```text
Client
  |
  v
API
  |
  +----> Redis
  |
  v
Service
```

### Phase 5 - Event Driven Architecture

```text
Service
   |
   v
Kafka
   |
   +----> Email Service
   |
   +----> Notification Service
   |
   +----> Analytics Service
```

### Phase 6 - Distributed System

```text
                    API Gateway
                         |
          +--------------+--------------+
          |              |              |
          v              v              v
      User Service   Auth Service   Other Service
          |              |
          v              v
        Redis          Database
          |
          v
        Kafka
```

---

# 🔐 Security Improvements

The current project is designed for learning purposes.

For a production-ready authentication system, the following improvements should be considered:

- Password hashing using BCrypt or Argon2
- JWT or secure session-based authentication
- HTTPS
- Rate limiting
- Account lockout policies
- Secure password storage
- Input sanitization
- Secure session management
- Proper authorization and role checking
- Database persistence
- Logging and monitoring

---

# 📊 Current Project Status

```text
User DTO                  [Completed]
User Fields               [Completed]
Signup                    [Completed]
Login                     [Completed]
Email Validation          [Completed]
Password Validation       [Completed]
HashMap Repository        [Completed]
Singleton Repository      [Completed]
Controller Layer          [Completed]
Service Layer             [Completed]
Repository Layer          [Completed]
```

---

# 📌 Learning Roadmap

```text
Java OOP
   |
   v
Layered Architecture
   |
   v
Repository Pattern
   |
   v
Singleton Pattern
   |
   v
Authentication
   |
   v
HashMap Storage
   |
   v
Database
   |
   v
Redis
   |
   v
Rate Limiting
   |
   v
Kafka
   |
   v
Publisher-Subscriber
   |
   v
CQRS
   |
   v
Circuit Breaker
   |
   v
Sidecar
   |
   v
Microservices
```

---

# 📚 Key Takeaways

This project helps understand how a simple authentication application can evolve into a distributed system.

The main concepts demonstrated are:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Data Storage
```

and gradually:

```text
Authentication
      ↓
Caching
      ↓
Rate Limiting
      ↓
Event Streaming
      ↓
Distributed Communication
      ↓
Fault Tolerance
      ↓
Microservices
```

---

# 👨‍💻 Project

**System Design Java - Login & Signup**

A learning project focused on understanding **Java, Object-Oriented Programming, software architecture, design patterns, authentication, and system design concepts**.

---

## ⭐ Project Goal

The goal of this project is to start with a simple Java Login and Signup application and gradually understand how real-world systems are designed, scaled, secured, and made fault tolerant.
