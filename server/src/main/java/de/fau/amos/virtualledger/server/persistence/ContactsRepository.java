package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.ContactsEntity;
import de.fau.amos.virtualledger.server.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsRepository extends CrudRepository<ContactsEntity, Integer> {

    @Query(value = "select case when count(c) > 0 then true else false end from ContactsEntity c where c.owner=:owner AND c.contact=:contact")
    boolean existsContactwithEmail(@Param("owner") User owner, @Param("contact") User contact);

    @Query("SELECT c FROM ContactsEntity c LEFT JOIN FETCH c.owner AS u WHERE c.owner = (:owner)")
    List<ContactsEntity> findAllByOwner(@Param("owner") User owner);

    @Query("SELECT c FROM ContactsEntity c WHERE c.owner = (:owner) AND c.contact = (:contact)")
    ContactsEntity findEntityByOwnerAndContact(@Param("owner") User owner, @Param("contact") User contact);
}
