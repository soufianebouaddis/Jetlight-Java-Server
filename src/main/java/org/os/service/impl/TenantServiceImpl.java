package org.os.service.impl;

import org.os.dto.TenantDTO;
import org.os.model.Tenant;
import org.os.repository.JpaRepository;
import org.os.service.TenantService;

import java.util.List;
import java.util.UUID;

public class TenantServiceImpl implements TenantService {
    private final JpaRepository jpaRepository;

    public TenantServiceImpl(JpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Tenant save(Tenant tenant) throws Exception {
        return jpaRepository.save(tenant);
    }

    @Override
    public List<Tenant> saveAll(List<Tenant> tenants) throws Exception {
        return jpaRepository.saveAll(tenants);
    }

    @Override
    public Tenant deleteById(UUID id) throws Exception {
        return jpaRepository.deleteById(id);
    }

    @Override
    public List<Tenant> findAll() throws Exception {
        return jpaRepository.findAll();
    }

    @Override
    public Tenant findById(UUID id) throws Exception {
        return jpaRepository.findById(id);
    }

    @Override
    public Tenant updateTenant(UUID id, TenantDTO tenant) throws Exception {
        return jpaRepository.updateTenant(id,tenant);
    }

    @Override
    public boolean validateUser(String username, String password) {
        return jpaRepository.validateUser(username,password);
    }

    @Override
    public Tenant getByUsername(String username) {
        return jpaRepository.getByUsername(username);
    }
}
