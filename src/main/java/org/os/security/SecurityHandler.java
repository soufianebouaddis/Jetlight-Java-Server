package org.os.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.os.util.JwtUtil;

import java.io.IOException;
import java.util.Set;

public class SecurityHandler implements HttpHandler {
    private final HttpHandler next;
    private final String requiredRole;
    private final Set<String> allowedIps;

    public SecurityHandler(HttpHandler next, String requiredRole, Set<String> allowedIps) {
        this.next = next;
        this.requiredRole = requiredRole;
        this.allowedIps = allowedIps;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String ip = exchange.getRemoteAddress().getAddress().getHostAddress();

        if (!allowedIps.isEmpty() && !allowedIps.contains(ip)) {
            exchange.sendResponseHeaders(403, 0);
            exchange.getResponseBody()
                    .close();
            return;
        }

        String auth = exchange.getRequestHeaders().getFirst("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            exchange.sendResponseHeaders(401, 0);
            exchange.getResponseBody()
                    .close();
            return;
        }

        try {
            String token = auth.substring("Bearer ".length());
            System.out.println("Token security handler: "+token);
            DecodedJWT jwt = JwtUtil.verify(token);
            String role = jwt.getClaim("role").asString();
            if (requiredRole != null && !requiredRole.equals(role)) {
                exchange.sendResponseHeaders(403, 0);
                exchange.getResponseBody()
                        .close();
                return;
            }
            exchange.setAttribute("username", jwt.getClaim("username").asString());
            exchange.setAttribute("role", role);
            next.handle(exchange);
        } catch (Exception e) {
            exchange.sendResponseHeaders(401, 0);
            exchange.getResponseBody()
                    .close();
        }
    }
}

