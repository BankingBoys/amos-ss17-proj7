package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyBankAccountBanlanceBankingModelRepository extends JpaRepository<DummyBankAccountBalanceBankingModelEntity, Integer> {
}
