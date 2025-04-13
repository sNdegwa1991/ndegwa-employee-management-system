# Employee Management System

A Java web application that provides CRUD (Create, Read, Update, Delete) operations for managing employee records. Built with Jakarta EE, PrimeFaces, and MySQL and running on a Payara Server.

## Features

- View a list of all employees with sorting and filtering
- Add new employees with validation
- Edit existing employee information
- Delete employees with confirmation
- Data persistence using MySQL

## Technologies

- Jakarta EE 9
- PrimeFaces 13.0
- MySQL Database
- JDBC for database connectivity
- Payara Server 6
- NetBeans IDE 25

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- NetBeans IDE 15 or higher
- Payara Server 6.x
- MySQL 8.0 or higher

## Setup Instructions

### Database Setup

1. Create a MySQL database with name 'employeedb' and and import the employee_schema database in folder 'database'
   

2. Configure your database credentials in the `com.ndegwa.employee.EmployeeDAO` class:
   - Update the `DB_URL`, `DB_USER`, and `DB_PASSWORD` constants to match your MySQL setup

### Project Setup

1. Clone this repository:
   ```
   git clone https://github.com/sNdegwa1991/employee-management-system.git
   ```

2. Open the project in NetBeans IDE:
   - Go to File > Open Project
   - Navigate to the cloned repository directory
   - Select the project and click Open

3. Configure Payara Server in NetBeans:
   - Right-click on the project and select Properties
   - Go to Run > Server and select Payara Server
   - Configure any needed deployment settings

4. Resolve dependencies:
   - Make sure all Jakarta EE and PrimeFaces dependencies are properly resolved
   - If needed, right-click the project and select "Resolve Missing Server Problem"

### Running the Application

1. Right-click on the project in NetBeans
2. Select "Run" or press F6
3. The application will be deployed to Payara Server
4. Open your browser and go to `http://localhost:8080/EmployeeApp/`

## Project Structure

```
EmployeeApp/
├── src/
│   ├── java/
│   │   └── com/
│   │       └── ndegwa/
│   │           └── employee/
│   │               ├── Employee.java
│   │               ├── EmployeeBean.java
│   │               ├── EmployeeDAO.java
│   ├── web/
│   │   ├── index.xhtml
│   │   ├── WEB-INF/
│   │       ├── web.xml
│   │       └── faces-config.xml
│   ├── resources/
│       └── META-INF/
│           └── beans.xml
```

## License

This project is developed by Samuel Ndegwa

## Acknowledgments

- Jakarta EE community
- PrimeFaces team
- Payara Server team
