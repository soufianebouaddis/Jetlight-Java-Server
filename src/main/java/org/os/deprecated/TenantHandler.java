package org.os.oldMiddleWare;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.os.annotations.Component;
import org.os.annotations.Inject;
import org.os.controller.TenantController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Collectors;

public class TenantHandler implements HttpHandler {
    private TenantController tenantController;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath(); // /tenants or /tenants/{id}
        String[] segments = path.split("/");

        try {
            if ("/tenants".equals(path)) {
                switch (method) {
                    case "GET" -> respond(exchange, 200, tenantController.getAllTenants());
                    case "POST" -> {
                        String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                                .lines().collect(Collectors.joining("\n"));
                        respond(exchange, 201, tenantController.createTenant(body));
                    }
                    default -> respond(exchange, 405, "Method Not Allowed");
                }
            } else if (segments.length == 3 && segments[1].equals("tenants")) {
                UUID id = UUID.fromString(segments[2]);
                switch (method) {
                    case "GET" -> respond(exchange, 200, tenantController.getTenantById(id));
                    case "DELETE" -> respond(exchange, 200, tenantController.deleteTenantById(id));
                    case "PUT" -> {
                        String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                                .lines().collect(Collectors.joining("\n"));
                        respond(exchange,200, tenantController.updateTenant(id,body));
                    }
                    default -> respond(exchange, 405, "Method Not Allowed");
                }
            } else {
                respond(exchange, 404, "Not Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            respond(exchange, 500, "Internal Server Error: " + e.getMessage());
        }
    }

    private void respond(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}

