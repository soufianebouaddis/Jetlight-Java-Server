package org.os.dto;

public class RegisterDto {
    private String name;
    private String username;
    private String password;
    private String organization;
    private String role;

    public RegisterDto(){

    }

    public RegisterDto(String name, String username, String password, String organization, String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.organization = organization;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RegisterDto{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", organization='" + organization + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
