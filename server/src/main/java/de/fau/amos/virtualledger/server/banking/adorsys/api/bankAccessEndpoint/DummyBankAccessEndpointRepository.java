package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyBankAccessEndpointRepository extends JpaRepository<DummyBankAccessBankingModelEntity, String> {
}
