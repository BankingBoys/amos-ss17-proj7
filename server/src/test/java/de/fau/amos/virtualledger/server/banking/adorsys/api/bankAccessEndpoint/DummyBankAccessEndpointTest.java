package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

public class DummyBankAccessEndpointTest {

    @Test
    public void addRepositoryCalled() throws Exception {

        // SETUP
        String testUser = "user";
        String testBankCode = "testCode";
        String testBankLogin = "testLogin";
        BankAccessBankingModel bankAccessBankingModel = new BankAccessBankingModel();
        bankAccessBankingModel.setUserId(testUser);
        bankAccessBankingModel.setBankLogin(testBankLogin);
        bankAccessBankingModel.setBankCode(testBankCode);

        DummyBankAccessEndpointRepository dummyBankAccessEndpointRepository = mock(DummyBankAccessEndpointRepository.class);

        DummyBankAccessEndpoint dummyBankAccessEndpoint = new DummyBankAccessEndpoint(dummyBankAccessEndpointRepository);

        // ACT
        dummyBankAccessEndpoint.addBankAccess(testUser, bankAccessBankingModel);

        // ASSERT
        verify(dummyBankAccessEndpointRepository, times(1)).save(any(DummyBankAccessBankingModelEntity.class));
    }

    @Test
    public void existsRepositoryCalled() throws Exception {

        // SETUP
        String testId = "test";

        DummyBankAccessEndpointRepository dummyBankAccessEndpointRepository = mock(DummyBankAccessEndpointRepository.class);
        when(dummyBankAccessEndpointRepository.exists(anyString())).thenReturn(true);

        DummyBankAccessEndpoint dummyBankAccessEndpoint = new DummyBankAccessEndpoint(dummyBankAccessEndpointRepository);

        // ACT
        boolean existsPreChange = dummyBankAccessEndpoint.existsBankAccess(testId);

        // ASSERT
        verify(dummyBankAccessEndpointRepository, times(1)).exists(anyString());
    }

    @Test
    public void getRepositoryCalled() throws Exception {

        // SETUP
        String testId = "test";

        DummyBankAccessEndpointRepository dummyBankAccessEndpointRepository = mock(DummyBankAccessEndpointRepository.class);
        when(dummyBankAccessEndpointRepository.findAll()).thenReturn(new ArrayList<DummyBankAccessBankingModelEntity>());

        DummyBankAccessEndpoint dummyBankAccessEndpoint = new DummyBankAccessEndpoint(dummyBankAccessEndpointRepository);

        // ACT
        List<BankAccessBankingModel> bankAccessBankingModelList = dummyBankAccessEndpoint.getBankAccesses(testId);

        // ASSERT
        verify(dummyBankAccessEndpointRepository, times(1)).findAll();
    }

}
