package org.os.service;

import org.os.dto.TenantDTO;
import org.os.model.Tenant;

import java.util.List;
import java.util.UUID;

public interface TenantService {
    Tenant save(Tenant tenant) throws Exception;
    List<Tenant> saveAll(List<Tenant> tenants) throws Exception;
    Tenant deleteById(UUID id) throws Exception;
    List<Tenant> findAll() throws Exception;
    Tenant findById(UUID id) throws Exception;
    Tenant updateTenant(UUID id, TenantDTO tenant) throws Exception;
    boolean validateUser(String username,String password);
    Tenant getByUsername(String username);
}
