package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select count(u)>0 from User u where u.email=:email")
    public boolean existsUserWithEmail(@Param("email") String email);
}
