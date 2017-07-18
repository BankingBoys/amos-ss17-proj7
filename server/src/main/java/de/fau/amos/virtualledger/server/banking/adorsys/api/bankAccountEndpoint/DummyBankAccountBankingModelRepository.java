package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DummyBankAccountBankingModelRepository extends JpaRepository<DummyBankAccountBankingModelEntity, String> {

    @Query("select case when count(account) > 0 then true else false end from DummyBankAccountBankingModelEntity account left join account.bankAccess as access where access.id=:accessId")
    boolean existBankAccountsForAccessId(@Param("accessId") String accessId);


    @Query("SELECT a FROM DummyBankAccountBankingModelEntity a WHERE a.bankAccess.id=:accessId")
    List<DummyBankAccountBankingModelEntity> findAllByAccessId(@Param("accessId") String accessId);
}
