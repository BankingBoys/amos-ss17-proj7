package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.SavingsAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Integer> {

    @Query("SELECT saving FROM SavingsAccount saving LEFT JOIN FETCH saving.userRelations AS relation JOIN relation.user AS user WHERE user.email = (:email)")
    List<SavingsAccount> findByUserEmailAndLoadUserRelations(@Param("email") String email);
}
