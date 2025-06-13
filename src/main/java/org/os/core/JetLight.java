package org.os.core;

import com.sun.net.httpserver.HttpServer;
import org.os.annotations.Controller;
import org.os.config.ComponentScan;
import org.os.config.IocContainer;
import org.os.middleware.DispatcherHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// CORE CLASS

public class JetLight {
    public static void runServer(String basePackage, int port) throws IOException {
        // Create container and scan
        IocContainer container = new IocContainer();
        Set<Class<?>> components = ComponentScan.scan(basePackage);

        for (Class<?> clazz : components) {
            container.register(clazz);
        }

        container.injectDependencies();

        // Initialize HTTP Server
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        Executor executor = Executors.newVirtualThreadPerTaskExecutor();
        DispatcherHandler dispatcher = new DispatcherHandler(container);

        // Register controllers dynamically
        for (Object bean : container.getAllBeans()) {
            if (bean.getClass().isAnnotationPresent(Controller.class)) {
                dispatcher.registerFromController(bean);
            }
        }

        server.createContext("/", dispatcher);
        server.setExecutor(executor);
        server.start();

        System.out.println("Server started on port " + port);
    }

}
