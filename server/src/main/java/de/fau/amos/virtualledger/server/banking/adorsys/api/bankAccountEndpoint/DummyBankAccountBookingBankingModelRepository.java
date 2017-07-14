package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DummyBankAccountBookingBankingModelRepository extends JpaRepository<DummyBankAccountBookingModelEntity, Integer> {

    @Query("select case when count(b) > 0 then true else false end from DummyBankAccountBankingModelEntity a LEFT JOIN a.bookingsList AS b where a.id=:accountId")
    boolean existsForAccountId(@Param("accountId") String accountId);

    @Query("select b from DummyBankAccountBankingModelEntity a LEFT JOIN a.bookingsList AS b where a.id=:accountId")
    List<DummyBankAccountBookingModelEntity> findAllForAccountId(@Param("accountId") String accountId);
}
