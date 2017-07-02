package de.fau.amos.virtualledger.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VirtualLedgerJettyApplication {

    private VirtualLedgerJettyApplication() {
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(VirtualLedgerJettyApplication.class, args);
    }
}
