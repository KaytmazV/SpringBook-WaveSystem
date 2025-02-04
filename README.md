# Library Management and Notification System

## Table of Contents
1. [About](#about)
2. [Features](#features)
   - [Administrator Features](#administrator-features)
   - [User Management Features](#user-management-features)
3. [Technologies Used](#technologies-used)
4. [Setup and Installation](#setup-and-installation)
5. [Environment Variables](#environment-variables)
6. [API Endpoints](#api-endpoints)

## About

This backend service, developed using Spring Boot, automates the management of library resources. It includes tracking books, managing user activities, and sending borrowing notifications. The system simplifies the borrowing and returning process, reduces errors, and enhances user experience by sending timely notifications about due dates, overdue items, and new arrivals. Using MySQL for data storage, it allows library staff to manage the catalog efficiently while improving communication with users. This solution reduces administrative burdens and enhances resource accessibility, addressing the growing demand for digital library management.

## Features

### Administrator Features:
- User Management: Allows administrators to create, update, and delete users. It also includes login authentication, ensuring secure access to the system.
- Genre Management: Enables the management of genres by adding, updating, deleting, and viewing all genres available in the library, helping users categorize and find books easily.
- Book Management: Facilitates the addition, update, deletion, and retrieval of book records, ensuring that the library collection is well-organized.
- Author Management: Allows administrators to manage author information, including creating, updating, and deleting author records.
- Fine Management: Manages fines associated with overdue books, including creating, updating, deleting fines, and checking if a book has fines associated with it.
- Transaction Management: Tracks the borrowing and returning of books, ensuring that each transaction is properly recorded for inventory and user tracking.
- Comment Management: Allows users to leave comments and reviews on books and authors, encouraging interaction within the system.
- Notification Management: Sends and manages notifications, alerting users about important events such as due dates, overdue books, or library activities.

### User Management Features:
- User Registration: Allows new users to create an account by providing necessary details such as username, password, and email.
- User Authentication: Supports secure login with a username and password, ensuring authorized access to the system.
- Manage Borrowed Books: Users can borrow books after browsing the collection provided by the library.
- Overdue Notifications: Sends notifications to users about overdue books and upcoming due dates.
- User Role Management: Differentiates between regular users and administrators, assigning appropriate access levels to each role.
- Password Reset: Provides users with an option to reset their password if they forget it, ensuring continued access to the system.

## Technologies Used

- Java (JDK 11 or later): Required to compile and run the application.
- Spring Boot: Framework for building Java-based web applications and APIs.
- Maven: Dependency management and project build tool.
- MySQL: Relational database for storing application data.
- Spring Data JPA: Simplifies database operations and data access.
- Spring Security: Provides authentication and authorization features.
- Spring Web: Handles HTTP requests and builds web applications.
- Validation: Ensures valid user inputs and maintains data integrity.

## Setup and Installation

### Installation Steps:

1. Download the Application:
   - Download the necessary installation files (IntelliJ).
   - Install required dependencies.

2. Set Up the Web Server:
   - Configure the server settings to point to the directory where the application files are stored.

3. Install the Database:
   - Install the required database software (MySQL).
   - Create a new database for the application to store all library-related data, including book details, user information, and transaction records.

4. Configure the Database:
   - Import the provided SQL schema (usually included in the application package) to create the necessary tables in your database.
   - Configure the application to connect to the database by editing the configuration file with the correct database credentials (host, username, password, etc.).

5. Run the Application:
   - Once setup is complete, navigate to the web application URL or run the local server to start the application.
   - Log in using the default admin credentials provided or set during the installation process.

6. Verify the Installation:
   - Test the application by adding books, registering users, and processing a test borrow/return transaction.
   - Ensure that notifications are being sent successfully for due dates and overdue items.

## Environment Variables

```properties
# Server settings
server.port=8080

# Database settings
spring.datasource.url=jdbc:mysql://localhost:3307/javaLibrary
spring.datasource.username=root
spring.datasource.password=root
```
6. API Endpoints

### 1. User Endpoints
These endpoints are used for managing user data, including creating, updating, deleting, and logging in users.

- `GET /Users`: Fetches all user details from the database.
- `GET /Users/{username}`: Retrieves a specific user based on their username.
- `POST /Users`: Creates a new user in the system by sending user data in the request body.
- `PUT /Users/{username}`: Updates user details. It requires user-specific information to modify the existing user data.
- `DELETE /Users/{username}`: Deletes a user from the database.
- `POST /Users/login`: Authenticates a user by validating the login credentials (username and password).

### 2. Genre Endpoints
These endpoints manage genres within the library, including retrieving, creating, updating, and deleting genres.

- `GET /Genres`: Retrieves a list of all genres available in the library.
- `GET /Genres/{id}`: Fetches details of a specific genre by its ID.
- `POST /Genres`: Adds a new genre to the library.
- `PUT /Genres/{id}`: Updates an existing genre by modifying its details.
- `DELETE /Genres/{id}`: Deletes a genre from the system.

### 3. Author Endpoints
These endpoints are used for managing authors in the system, allowing for viewing, creating, updating, and deleting author information.

- `GET /Authors`: Retrieves a list of all authors.
- `GET /Authors/{id}`: Fetches details of a specific author based on their ID.
- `POST /Authors`: Creates a new author entry in the system.
- `PUT /Authors/{id}`: Updates an existing author's details.
- `DELETE /Authors/{id}`: Deletes an author from the database.

### 4. Comment Endpoints
These endpoints allow users to interact with comments related to books, authors, or other library resources.

- `GET /Comments`: Retrieves a list of all comments.
- `GET /Comments/{id}`: Fetches details of a specific comment based on their ID.
- `POST /Comments`: Creates a new comment entry in the system.
- `PUT /Comments/{id}`: Updates an existing comment's details.
- `DELETE /Comments/{id}`: Deletes a comment.

### 5. Book Endpoints
The book-related endpoints manage the library's book collection, including adding, updating, deleting, and retrieving book information.

- `GET /Books`: Fetches all the books available in the library.
- `GET /Books/{id}`: Retrieves details of a specific book using its unique ID.
- `POST /Books`: Adds a new book to the library collection.
- `PUT /Books/{id}`: Updates the details of an existing book.
- `DELETE /Books/{id}`: Deletes a specific book from the collection.

### 6. Fine Endpoints
These endpoints handle fines associated with overdue books or other library infractions.

- `GET /Fines`: Retrieves all fines related to the library's users.
- `GET /Fines/{id}`: Fetches a specific fine by its ID.
- `POST /Fines`: Creates a new fine based on overdue books or other infractions.
- `PUT /Fines/{id}`: Updates the details of an existing fine.
- `DELETE /Fines/{id}`: Deletes a fine record from the database.
- `GET /Fines/checkFineByBook/{transactionId}`: Checks if a fine is associated with a specific transaction.

### 7. Transaction Endpoints
Transaction-related endpoints track borrowing and returning books and can manage the complete lifecycle of a transaction.

- `GET /Transactions`: Retrieves all transactions in the library system.
- `GET /Transactions/{id}`: Fetches a specific transaction based on its ID.
- `POST /Transactions/borrowBook`: Initiates a borrowing process for a book by a user, recording the details of the transaction.
- `PUT /Transactions/returnBook`: Marks a book as returned, completing the transaction.
- `DELETE /Transactions`: Deletes a transaction record from the system.

### 8. Notification Endpoints
These endpoints are used to manage notifications, which may be used for sending alerts about overdue books, due dates, or library events.

- `GET /Notifications`: Retrieves all notifications within the system.
- `GET /Notifications/{id}`: Fetches details about a specific notification.
- `POST /Notifications`: Creates a new notification, often used to alert users about overdue books or due dates.
- `DELETE /Notifications`: Deletes a notification from the system.
- `PUT /Notifications/{id}`: Updates notification details.

Note: For detailed examples of each endpoint's requirements, refer to the imported Postman testing endpoints provided.

