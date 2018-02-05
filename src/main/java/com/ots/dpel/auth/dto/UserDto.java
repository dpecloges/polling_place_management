package com.ots.dpel.auth.dto;

public class UserDto {
    
    private Long id;
    
    private String username;
    
    private String email;
    
    private String lastName;
    
    private String firstName;
    
    private Long electionDepartmentId;
    
    private Long roleId;
    
    private Boolean willChangePassword;
    
    private String password;
    
    private String passwordRepeat;
    
    public UserDto() {
    
    }
    
    public UserDto(Long id, String username, String email, String lastName, String firstName,
                   Long electionDepartmentId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.electionDepartmentId = electionDepartmentId;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }
    
    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
    }
    
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public Boolean getWillChangePassword() {
        return willChangePassword;
    }
    
    public void setWillChangePassword(Boolean willChangePassword) {
        this.willChangePassword = willChangePassword;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPasswordRepeat() {
        return passwordRepeat;
    }
    
    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}
