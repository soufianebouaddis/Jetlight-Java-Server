package org.os.middleware;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherHandler implements HttpHandler {
    private final Map<String, HttpHandler> routes = new HashMap<>();

    public void register(String path, HttpHandler handler) {
        routes.put(path, handler);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String basePath = getBasePath(path);

        HttpHandler handler = routes.get(basePath);
        if (handler != null) {
            handler.handle(exchange);
        } else {
            exchange.sendResponseHeaders(404, -1);
        }
    }

    private String getBasePath(String path) {
        String[] parts = path.split("/");
        return parts.length > 1 ? "/" + parts[1] : "/";
    }
}

