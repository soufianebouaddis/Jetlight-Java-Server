package org.os.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtUtil {
    private static final String SECRET = "IocDI";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public static String generate(String username, String role) {
        return JWT.create()
                .withClaim("username", username)
                .withClaim("role", role)
                .sign(algorithm);
    }

    public static DecodedJWT verify(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}


