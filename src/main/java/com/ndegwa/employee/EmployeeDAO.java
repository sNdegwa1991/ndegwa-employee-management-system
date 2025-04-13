/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndegwa.employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class EmployeeDAO {
    
    private static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());
    
    // Database connection
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeedb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; 
    
    // Queries
    private static final String SQL_SELECT_ALL = "SELECT * FROM employees";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM employees WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO employees (first_name, last_name, email, department, salary) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM employees WHERE id = ?";
    private static final String SQL_CHECK_EMAIL = "SELECT COUNT(*) FROM employees WHERE email = ? AND id != ?";
    
    // JDBC driver Loading
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found", e);
        }
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error closing database resources", e);
        }
    }
    
  // Get all employees from the database
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Employee employee = mapResultSetToEmployee(rs);
                employees.add(employee);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all employees", e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return employees;
    }
    
   // Get employee by ID
    public Employee getEmployeeById(int id) {
        Employee employee = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                employee = mapResultSetToEmployee(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving employee with ID: " + id, e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return employee;
    }
    
    // Add a new employee
    public boolean addEmployee(Employee employee) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            // Check if email already exists
            if (isEmailDuplicate(employee.getEmail(), 0)) {
                LOGGER.log(Level.WARNING, "Email already exists: " + employee.getEmail());
                return false;
            }
            
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getDepartment());
            stmt.setBigDecimal(5, employee.getSalary());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                // Get the generated ID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding employee", e);
        } finally {
            closeResources(conn, stmt, null);
        }
        
        return false;
    }
    
    //  Update an existing employee
    public boolean updateEmployee(Employee employee) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            // Check if email already exists for another employee
            if (isEmailDuplicate(employee.getEmail(), employee.getId())) {
                LOGGER.log(Level.WARNING, "Email already exists: " + employee.getEmail());
                return false;
            }
            
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getDepartment());
            stmt.setBigDecimal(5, employee.getSalary());
            stmt.setInt(6, employee.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating employee with ID: " + employee.getId(), e);
        } finally {
            closeResources(conn, stmt, null);
        }
        
        return false;
    }
    
    // Delete an employee by ID
    public boolean deleteEmployee(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting employee with ID: " + id, e);
        } finally {
            closeResources(conn, stmt, null);
        }
        
        return false;
    }
    
    // Check if an email exists
    private boolean isEmailDuplicate(String email, int excludeId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_CHECK_EMAIL);
            stmt.setString(1, email);
            stmt.setInt(2, excludeId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking email duplicate: " + email, e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return false;
    }
    
  
    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setEmail(rs.getString("email"));
        employee.setDepartment(rs.getString("department"));
        employee.setSalary(rs.getBigDecimal("salary"));
        return employee;
    }
    
}
