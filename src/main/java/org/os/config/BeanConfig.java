package org.os.config;

import org.os.controller.TenantController;
import org.os.middleware.AuthHandler;
import org.os.middleware.TenantHandler;
import org.os.repository.JpaRepository;
import org.os.repository.impl.JpaRepositoryImpl;
import org.os.service.TenantService;
import org.os.service.impl.TenantServiceImpl;

public class BeanConfig {
    private IocContainer container;

    public BeanConfig(IocContainer container) {
        this.container = container;
        registerBeans();
    }

    private void registerBeans() {
        container.registerBean(JpaRepository.class, new JpaRepositoryImpl());
        container.registerBean(TenantService.class, new TenantServiceImpl(container.getBean(JpaRepository.class)));
        container.registerBean(TenantController.class, new TenantController(container.getBean(TenantService.class)));
        container.registerBean(TenantHandler.class, new TenantHandler(container.getBean(TenantService.class)));
        container.registerBean(AuthHandler.class, new AuthHandler(container.getBean(TenantService.class)));
    }

    public JpaRepository jpaRepository() {
        return container.getBean(JpaRepository.class);
    }

    public TenantService tenantService() {
        return container.getBean(TenantService.class);
    }
    public TenantController tenantController(){
        return container.getBean(TenantController.class);
    }
    public TenantHandler tenantHandler(){
        return container.getBean(TenantHandler.class);
    }
    public AuthHandler authHandler(){
        return container.getBean(AuthHandler.class);
    }
}


