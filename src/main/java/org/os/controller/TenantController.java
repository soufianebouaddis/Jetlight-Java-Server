package org.os.controller;

import org.os.dto.TenantDTO;
import org.os.model.Tenant;
import org.os.service.TenantService;
import org.os.util.JsonUtil;

import java.util.UUID;

public class TenantController {
    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    public String createTenant(String requestBody) throws Exception {
        Tenant tenant = new Tenant.Builder()
                .id()
                .name(JsonUtil.fromJson(requestBody).getName())
                .username(JsonUtil.fromJson(requestBody).getUsername())
                .organization(JsonUtil.fromJson(requestBody).getOrganization())
                .build();
        tenantService.save(tenant);
        return JsonUtil.toJsonWithDetails("201","createTenant",tenant);
    }

    public String getAllTenants() throws Exception {
        return JsonUtil.toJson(tenantService.findAll());
    }

    public String getTenantById(UUID id) throws Exception {
        Tenant tenant = tenantService.findById(id);
        if (tenant != null) {
            return JsonUtil.toJsonWithDetails("200","getTenant",tenant);
        }
        return "{\"error\":\"Tenant not found\"}";
    }

    public String deleteTenantById(UUID id) throws Exception {
        return JsonUtil.toJsonWithDetails("200","deleteTenant",tenantService.deleteById(id));
    }
    public String updateTenant(UUID id,String request) throws Exception {
        Tenant updatedTenant = tenantService.updateTenant(
                id,
                new TenantDTO(
                        JsonUtil.fromJson(request).getName(),
                        JsonUtil.fromJson(request).getUsername(),
                        JsonUtil.fromJson(request).getOrganization()
                )
        );
        return JsonUtil.toJson(updatedTenant);
    }
}

