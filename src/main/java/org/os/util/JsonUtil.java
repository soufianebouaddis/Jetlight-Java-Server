package org.os.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.os.dto.LoginDto;
import org.os.dto.RegisterDto;
import org.os.dto.TenantDTO;
import org.os.model.Tenant;

import java.util.*;
import java.util.stream.Collectors;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static String toJson(List<Tenant> tenants) {
        return "{ \"status\": \"200\",\"message\": \"FindAll\",\"data\" : [" + tenants.stream()
                .map(JsonUtil::toJson)
                .collect(Collectors.joining(","))
                + "]"+"}";
    }

    public static String toJson(Tenant tenant) {
        return String.format(
                "{\"id\":\"%s\",\"name\":\"%s\",\"username\":\"%s\",\"organization\":\"%s\"}",
                tenant.getId(), tenant.getName(), tenant.getUsername(), tenant.getOrganization()
        );
    }
    public static String toJsonWithDetails(String statusCode,String message,Tenant tenant) {
        return String.format(
                "{ \"status\": \"%s\",\"message\": \"%s\",\"data\": {\"id\":\"%s\",\"name\":\"%s\",\"username\":\"%s\",\"organization\":\"%s\"}}",
                statusCode,message,tenant.getId(), tenant.getName(), tenant.getUsername(), tenant.getOrganization()
        );
    }
    public static TenantDTO fromJson(String json) {
        try {
            return objectMapper.readValue(json, TenantDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
    public static LoginDto fromJsonToLoginDto(String json) {
        try {
            return objectMapper.readValue(json, LoginDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
    public static RegisterDto fromJsonToRegister(String json) {
        try {
            return objectMapper.readValue(json, RegisterDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

}

