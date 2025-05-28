package org.os.repository.impl;

import org.os.dto.TenantDTO;
import org.os.exception.DataAccessException;
import org.os.model.Tenant;
import org.os.repository.JpaRepository;

import java.util.*;

public class JpaRepositoryImpl implements JpaRepository {
    private List<Tenant> db = new ArrayList<>();

    public JpaRepositoryImpl() {
    }

    @Override
    public Tenant save(Tenant tenant) throws Exception {
        try {
            db.add(tenant);
            return tenant;
        } catch (Exception e) {
            throw new DataAccessException("Error occurred during save to db: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Tenant> saveAll(List<Tenant> tenants) throws Exception {
        try {
            db.addAll(tenants);
            return tenants;
        } catch (Exception e) {
            throw new DataAccessException("Error occurred during save all data to db: " + e.getMessage(), e);
        }
    }

    @Override
    public Tenant deleteById(UUID id) throws DataAccessException {
        try {
            Optional<Tenant> deletedTenant = db.stream()
                    .filter(t -> t.getId().equals(id))
                    .findFirst();
            if (deletedTenant.isPresent()) {
                db.remove(deletedTenant.get());
                return deletedTenant.get();
            } else {
                throw new DataAccessException("Tenant with id " + id + " not found");
            }
        } catch (Exception e) {
            throw new DataAccessException("Error occurred during delete by id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Tenant> findAll() throws Exception {
        try {
            return db;
        } catch (Exception e) {
            throw new DataAccessException("Error occurred during find all: " + e.getMessage(), e);
        }
    }

    @Override
    public Tenant findById(UUID id) throws Exception {
        try {
            var tenantobj = db.stream()
                    .filter(t -> t.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Tenant not found with id : " + id));
            return tenantobj;
        } catch (Exception e) {
            throw new DataAccessException("Error occurred during find by id: " + e.getMessage(), e);
        }
    }

    @Override
    public Tenant updateTenant(UUID id, TenantDTO tenant) throws Exception {
        try {
            var getTenant = db.stream().filter(i->i.getId().equals(id)).findFirst();
            if(getTenant.isEmpty()){
                throw new Exception("Tenant not found with id : "+id);
            }
            Tenant updateTenant = getTenant.get();
            updateTenant.setName(tenant.getName());
            updateTenant.setUsername(tenant.getUsername());
            updateTenant.setOrganization(tenant.getOrganization());
            return updateTenant;
        } catch (Exception e) {
            throw new DataAccessException("Error during update : " + e.getMessage());
        }
    }

    @Override
    public boolean validateUser(String username, String password) {
        return db.stream()
                .anyMatch(t -> t.getUsername().equals(username) && t.getPassword().equals(password));
    }

    @Override
    public Tenant getByUsername(String username) {
        return db.stream()
                .filter(t->t.getUsername().equals(username))
                .findFirst()
                .get();
    }
}
