package org.os.oldMiddleWare;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.os.dto.LoginDto;
import org.os.dto.RegisterDto;
import org.os.model.Tenant;
import org.os.service.TenantService;
import org.os.util.JsonUtil;
import org.os.util.JwtUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class AuthHandler implements HttpHandler {

    private final TenantService tenantService;

    public AuthHandler(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

        if (path.equals("/register") && method.equals("POST")) {
            RegisterDto json = JsonUtil.fromJsonToRegister(body);
            try {
                tenantService.save(
                        new Tenant.Builder()
                                .id()
                                .name(json.getName())
                                .username(json.getUsername())
                                .password(json.getPassword())
                                .organization(json.getOrganization())
                                .role(json.getRole()).build()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            respond(exchange, 200, "User registered");
        } else if (path.equals("/login") && method.equals("POST")) {
            LoginDto json = JsonUtil.fromJson(body, LoginDto.class);
            System.out.println("login dto : "+json.toString());
            if (tenantService.validateUser(json.getUsername(), json.getPassword())) {
                Tenant tenant = tenantService.getByUsername(json.getUsername());
                String token = JwtUtil.generate(tenant.getUsername(), tenant.getRole());
                System.out.println("token Auth handler : "+token);
                respond(exchange, 200, "{\"token\":\"" + token + "\"}");
            } else {
                respond(exchange, 401, "Invalid credentials");
            }
        } else {
            respond(exchange, 404, "Not found");
        }
    }

    private void respond(HttpExchange exchange, int     code, String msg) throws IOException {
        exchange.sendResponseHeaders(code, msg.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(msg.getBytes(StandardCharsets.UTF_8));
        }
    }
}

