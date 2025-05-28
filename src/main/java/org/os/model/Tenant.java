package org.os.model;

import java.util.UUID;

public class Tenant {
    private UUID id;
    private String name;
    private String username;
    private String organization;
    private String password;
    private String role;
    public Tenant() {
    }

    public Tenant(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.username = builder.username;
        this.organization = builder.organization;
        this.password = builder.password;
        this.role = builder.role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", organization='" + organization + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String username;
        private String organization;
        private String password;
        private String role;
        public Builder() {

        }

        public Builder id() {
            this.id = UUID.randomUUID();
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder password(String password){
            this.password=password;
            return this;
        }
        public Builder organization(String organization) {
            this.organization = organization;
            return this;
        }
        public Builder role(String role){
            this.role = role;
            return this;
        }
        public Tenant build() {
            return new Tenant(this);
        }
    }
}
