package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.DeletedBankAccess;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeletedBankAccessesRepository extends CrudRepository<DeletedBankAccess, String> {

    @Query("SELECT d FROM DeletedBankAccess d LEFT JOIN FETCH d.users AS u WHERE u.email = (:email)")
    List<DeletedBankAccess> findAllByUserEmail(@Param("email") String email);
}
