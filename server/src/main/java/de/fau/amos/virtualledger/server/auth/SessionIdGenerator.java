package de.fau.amos.virtualledger.server.auth;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
class SessionIdGenerator {

    private final SecureRandom secureRandom = new SecureRandom();
    private Base64.Encoder encoder = Base64.getEncoder();

    String generate() {
        final byte[] bytes = new byte[128];
        secureRandom.nextBytes(bytes);
        return encoder.encodeToString(bytes);
    }
}
