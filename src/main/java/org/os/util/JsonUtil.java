package org.os.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.os.common.Response;
import org.os.dto.LoginDto;
import org.os.dto.RegisterDto;
import org.os.dto.TenantDTO;
import org.os.model.Tenant;

import java.util.*;
import java.util.stream.Collectors;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Convert Response object to JSON string
    public static <T> String toJson(Response<T> response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert Response to JSON", e);
        }
    }

    // Convert single tenant to Response and then to JSON
    public static String toJson(Tenant tenant) {
        Response<Tenant> response = new Response<>("200", "Success", tenant);
        return toJson(response);
    }

    // Convert list of tenants to Response and then to JSON
    public static String toJson(List<Tenant> tenants) {
        Response<List<Tenant>> response = new Response<>("200", "FindAll", tenants);
        return toJson(response);
    }

    // Convert token to Response and then to JSON
    public static String tokenToJson(String statusCode, String message, String token) {
        // Create a simple token wrapper or use Map
        Map<String, String> tokenData = new HashMap<>();
        tokenData.put("token", token);
        Response<?> response = new Response<>(statusCode, message, tokenData);
        return toJson(response);
    }

    // Generic method to create JSON response
    public static <T> String createJsonResponse(String statusCode, String message, T data) {
        Response<T> response = new Response<>(statusCode, message, data);
        return toJson(response);
    }

    // Parse JSON to RegisterDto (unchanged)
    public static RegisterDto fromJsonToRegister(String json) {
        try {
            return objectMapper.readValue(json, RegisterDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    // Parse JSON to Tenant (unchanged)
    public static TenantDTO fromJsonToTenant(String json) {
        try {
            return objectMapper.readValue(json, TenantDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
    // Generic method to parse JSON to any class
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON to " + clazz.getSimpleName(), e);
        }
    }
}


