package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.SavingsAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Integer> {

    @Query("SELECT s FROM SavingsAccount s LEFT JOIN FETCH s.users AS u WHERE u.email = (:email)")
    List<SavingsAccount> findByUserEmail(@Param("email") String email);
}
