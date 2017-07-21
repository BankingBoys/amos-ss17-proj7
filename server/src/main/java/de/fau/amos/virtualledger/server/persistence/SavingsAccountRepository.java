package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.SavingsAccountEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountUserRelation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SavingsAccountRepository extends CrudRepository<SavingsAccountEntity, Integer> {

    @Query("SELECT saving FROM SavingsAccountEntity saving LEFT JOIN saving.userRelations AS relation INNER JOIN relation.user AS user WHERE user.email = (:email)")
    List<SavingsAccountEntity> findByUserEmail(@Param("email") String email);

    @Query("SELECT relation FROM SavingsAccountEntity saving LEFT JOIN saving.userRelations AS relation INNER JOIN relation.user AS user WHERE saving = (:saving)")
    Set<SavingsAccountUserRelation> findUserRelationsBySaving(@Param("saving") SavingsAccountEntity saving);

}
