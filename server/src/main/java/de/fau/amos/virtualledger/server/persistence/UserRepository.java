package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "select case when count(u) > 0 then true else false end from User u where u.email=:email")
    boolean existsUserWithEmail(@Param("email") String email);
}
