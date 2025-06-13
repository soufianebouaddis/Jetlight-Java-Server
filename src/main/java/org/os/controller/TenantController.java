package org.os.controller;

import org.os.annotations.Controller;
import org.os.annotations.Inject;
import org.os.annotations.Secured;
import org.os.dto.TenantDTO;
import org.os.model.Tenant;
import org.os.service.TenantService;
import org.os.util.JsonUtil;

import java.util.UUID;
@Controller
public class TenantController {
    @Inject
    private TenantService tenantService;

    @Secured(role = "ADMIN")
    public String createTenant(String requestBody) throws Exception {
        Tenant tenant = new Tenant.Builder()
                .id()
                .name(JsonUtil.fromJsonToRegister(requestBody).getName())
                .username(JsonUtil.fromJsonToRegister(requestBody).getUsername())
                .organization(JsonUtil.fromJsonToRegister(requestBody).getOrganization())
                .build();
        tenantService.save(tenant);
        return JsonUtil.toJson(tenant);
    }
    @Secured(role = "ADMIN", allowedIps = {"0:0:0:0:0:0:0:1"})
    public String getAllTenants() throws Exception {
        return JsonUtil.toJson(tenantService.findAll());
    }
    @Secured(role = "ADMIN")
    public String getTenantById(UUID id) throws Exception {
        Tenant tenant = tenantService.findById(id);
        if (tenant != null) {
            return JsonUtil.toJson(tenant);
        }
        return "{\"error\":\"Tenant not found\"}";
    }
    @Secured(role = "ADMIN")
    public String deleteTenantById(UUID id) throws Exception {
        return JsonUtil.toJson(tenantService.deleteById(id));
    }
    @Secured(role = "ADMIN")
    public String updateTenant(UUID id,String request) throws Exception {
        Tenant updatedTenant = tenantService.updateTenant(
                id,
                new TenantDTO(
                        JsonUtil.fromJsonToTenant(request).getName(),
                        JsonUtil.fromJsonToTenant(request).getUsername(),
                        JsonUtil.fromJsonToTenant(request).getOrganization(),
                        JsonUtil.fromJsonToTenant(request).getPassword()
                )
        );
        return JsonUtil.toJson(updatedTenant);
    }
}

