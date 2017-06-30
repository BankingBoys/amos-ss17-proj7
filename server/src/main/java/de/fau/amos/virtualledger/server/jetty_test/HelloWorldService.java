package de.fau.amos.virtualledger.server.jetty_test;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.server.banking.BankingOverviewController;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HelloWorldService {

	@Value("${name:World}")
	private String name;

	@Autowired
	BankingOverviewController bankingOverviewController;

	public List<BankAccess> getHelloMessage() {
		try {
			return bankingOverviewController.getBankingOverview("test@user.de");
		} catch (BankingException e) {
			return new ArrayList<BankAccess>();
		}
	}

}