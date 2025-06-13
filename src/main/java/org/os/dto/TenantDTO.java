package org.os.dto;

import java.util.UUID;

public class TenantDTO {
    private String name;
    private String username;
    private String organization;
    private String password;

    public TenantDTO(){}

    public TenantDTO(String name, String username, String organization, String password) {
        this.name = name;
        this.username = username;
        this.organization = organization;
        this.password = password;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TenantDTO{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", organization='" + organization + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
