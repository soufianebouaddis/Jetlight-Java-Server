package org.os;

import com.sun.net.httpserver.HttpServer;
import org.os.config.BeanConfig;
import org.os.config.IocContainer;
import org.os.middleware.AuthHandler;
import org.os.middleware.DispatcherHandler;
import org.os.middleware.HealthHandler;
import org.os.middleware.TenantHandler;
import org.os.security.SecurityHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        // Create IoC container
        IocContainer container = new IocContainer();
        // Initialize BeanConfig with IoC container
        BeanConfig beanConfig = new BeanConfig(container);
        // Initialize Http server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        // using virtual threads to handle concurrency of request
        Executor virtualThreads = Executors.newVirtualThreadPerTaskExecutor();

        // Register endpoint into dispatcher
        DispatcherHandler dispatcherHandler = new DispatcherHandler();
        Set<String> allowedIps = Set.of("127.0.0.1", "0:0:0:0:0:0:0:1");
        dispatcherHandler.register("/register", new AuthHandler(beanConfig.tenantService()));
        dispatcherHandler.register("/login", new AuthHandler(beanConfig.tenantService()));
        dispatcherHandler.register("/tenants",
                new SecurityHandler(new TenantHandler(beanConfig.tenantService()), "ADMIN", allowedIps)); // secured
        dispatcherHandler.register("/health", new HealthHandler()); // public endpoint
        // set the virtual threads so each http request is handled by its own
        // lightweight virtual thread
        server.createContext("/", dispatcherHandler);
        server.setExecutor(virtualThreads);
        server.start();
        System.out.println("Server started on port 8080");

        // Try to make it more sophisticated => make it as spring boot

        // TODO make server where to upload itil files into personal laptop (personal
        // laptop is the server)
    }

}