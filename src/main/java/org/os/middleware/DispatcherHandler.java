package org.os.middleware;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.os.annotations.Secured;
import org.os.config.IocContainer;
import org.os.security.SecurityHandler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class DispatcherHandler implements HttpHandler {
    private final Map<String, HttpHandler> routes = new HashMap<>();
    private final IocContainer container;

    public DispatcherHandler(IocContainer container) {
        this.container = container;
    }

    public void registerFromController(Object controller) {
        String basePath = "/" + controller.getClass().getSimpleName().toLowerCase().replace("controller", "");

        for (Method method : controller.getClass().getDeclaredMethods()) {
            String path = basePath + "/" + method.getName().toLowerCase();

            HttpHandler rawHandler = exchange -> {
                try {
                    Object result;

                    // Check if method requires parameters
                    if (method.getParameterCount() > 0) {
                        // Read request body for methods that need it
                        String requestBody = new String(exchange.getRequestBody().readAllBytes());
                        result = method.invoke(controller, requestBody);
                    } else {
                        // No parameters needed
                        result = method.invoke(controller);
                    }

                    byte[] response = result.toString().getBytes();
                    exchange.sendResponseHeaders(200, response.length);
                    exchange.getResponseBody().write(response);
                    exchange.getResponseBody().close();
                } catch (Exception e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, 0);
                    exchange.getResponseBody().close();
                }
            };

            // Wrap with SecurityHandler if @Secured is present
            if (method.isAnnotationPresent(Secured.class)) {
                Secured secured = method.getAnnotation(Secured.class);
                String requiredRole = secured.role();
                Set<String> allowedIps = new HashSet<>(Arrays.asList(secured.allowedIps()));
                rawHandler = new SecurityHandler(rawHandler, requiredRole, allowedIps);
            }

            routes.put(path, rawHandler);
        }
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        HttpHandler handler = routes.get(path);
        if (handler != null) {
            handler.handle(exchange);
        } else {
            exchange.sendResponseHeaders(404, 0);
            exchange.getResponseBody().write("Not Found Route".getBytes());
            exchange.getResponseBody().close();
        }
    }
}



