/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndegwa.employee;

import java.io.Serializable;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.component.UIComponent;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author ADMIN
 */
@Named("employeeBean")
@ViewScoped
public class EmployeeBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private EmployeeDAO employeeDAO;
    
    private List<Employee> employees;
    
    private Employee selectedEmployee;
    
    private Employee newEmployee;
    
    // Show dialog
    private boolean showAddDialog;
    private boolean showEditDialog;
    private boolean showDeleteDialog;
    
    /**
     * Initialize the bean
     */
    @PostConstruct
    public void init() {
        employeeDAO = new EmployeeDAO();
        newEmployee = new Employee();
        loadEmployees();
    }
    
    /**
     * Load all employees from the database
     */
    public void loadEmployees() {
        employees = employeeDAO.getAllEmployees();
    }
    
    /**
     * Open dialog to add a new employee
     */
    public void openAddDialog() {
        newEmployee = new Employee();
        showAddDialog = true;
    }
    
    /**
     * Save a new employee
     */
    public void saveNewEmployee() {
        try {
            if (employeeDAO.addEmployee(newEmployee)) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Employee added successfully");
                loadEmployees();
                newEmployee = new Employee();
                showAddDialog = false;
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to add employee. Email may already exist.");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Open dialog to edit an employee
     * @param employee Employee to edit
     */
    public void openEditDialog(Employee employee) {
        selectedEmployee = new Employee();
        // Create a copy to avoid modifying the original until save
        selectedEmployee.setId(employee.getId());
        selectedEmployee.setFirstName(employee.getFirstName());
        selectedEmployee.setLastName(employee.getLastName());
        selectedEmployee.setEmail(employee.getEmail());
        selectedEmployee.setDepartment(employee.getDepartment());
        selectedEmployee.setSalary(employee.getSalary());
        showEditDialog = true;
    }
    
    /**
     * Save edited employee
     */
    public void saveEditedEmployee() {
        try {
            if (employeeDAO.updateEmployee(selectedEmployee)) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Employee updated successfully");
                loadEmployees();
                showEditDialog = false;
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update employee. Email may already exist.");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Open dialog to confirm delete
     * @param employee Employee to delete
     */
    public void openDeleteDialog(Employee employee) {
        selectedEmployee = employee;
        showDeleteDialog = true;
    }
    
    /**
     * Delete employee
     */
    public void deleteEmployee() {
        try {
            if (employeeDAO.deleteEmployee(selectedEmployee.getId())) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Employee deleted successfully");
                loadEmployees();
                showDeleteDialog = false;
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete employee");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Cancel operation and close dialog
     */
    public void cancel() {
        showAddDialog = false;
        showEditDialog = false;
        showDeleteDialog = false;
    }
    
    /**
     * Email validator
     * @param context FacesContext
     * @param component UIComponent
     * @param value Object to validate
     * @throws ValidatorException if validation fails
     */
    public void validateEmail(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String email = (String) value;
        
        // Basic email validation using regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        
        if (!matcher.matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email format", null));
        }
    }
    
    /**
     * Add message to JSF context
     * @param severity Message severity
     * @param summary Message summary
     * @param detail Message detail
     */
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    // Getters and Setters
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }
    
    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }
    
    public Employee getNewEmployee() {
        return newEmployee;
    }
    
    public void setNewEmployee(Employee newEmployee) {
        this.newEmployee = newEmployee;
    }
    
    public boolean isShowAddDialog() {
        return showAddDialog;
    }
    
    public void setShowAddDialog(boolean showAddDialog) {
        this.showAddDialog = showAddDialog;
    }
    
    public boolean isShowEditDialog() {
        return showEditDialog;
    }
    
    public void setShowEditDialog(boolean showEditDialog) {
        this.showEditDialog = showEditDialog;
    }
    
    public boolean isShowDeleteDialog() {
        return showDeleteDialog;
    }
    
    public void setShowDeleteDialog(boolean showDeleteDialog) {
        this.showDeleteDialog = showDeleteDialog;
    }
    
}
