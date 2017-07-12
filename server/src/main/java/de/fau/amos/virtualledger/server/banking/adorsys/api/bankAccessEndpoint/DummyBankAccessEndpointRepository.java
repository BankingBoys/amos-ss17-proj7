package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyBankAccessEndpointRepository extends JpaRepository<BankAccessBankingModel, String> {
}
