package de.fau.amos.virtualledger.server.auth;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
class SessionIdGenerator {

    private static final int RANDOM_BYTE_LENGTH = 128;
    private final SecureRandom secureRandom = new SecureRandom();
    private Base64.Encoder encoder = Base64.getEncoder();

    String generate() {
        final byte[] bytes = new byte[RANDOM_BYTE_LENGTH];
        secureRandom.nextBytes(bytes);
        return encoder.encodeToString(bytes);
    }
}
