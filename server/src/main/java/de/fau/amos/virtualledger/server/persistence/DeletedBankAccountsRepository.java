package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.DeletedBankAccount;
import de.fau.amos.virtualledger.server.model.DeletedBankAccountId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeletedBankAccountsRepository extends CrudRepository<DeletedBankAccount, DeletedBankAccountId> {

    @Query("SELECT d FROM DeletedBankAccount d LEFT JOIN FETCH d.users AS u WHERE u.email = (:email)")
    List<DeletedBankAccount> findAllByUserEmail(@Param("email") String email);

    @Query("SELECT d FROM DeletedBankAccount d LEFT JOIN FETCH d.users AS u WHERE u.email = (:email) AND d.id.bankAccessId = (:accessId)")
    List<DeletedBankAccount> findAllByUserEmailAndAccessId(@Param("email") String email, @Param("accessId") String accessId);
}
