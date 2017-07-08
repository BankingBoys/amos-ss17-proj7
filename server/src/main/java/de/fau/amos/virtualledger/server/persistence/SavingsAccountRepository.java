package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.SavingsAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Integer> {

}
