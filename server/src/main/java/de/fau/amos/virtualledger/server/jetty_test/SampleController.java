package de.fau.amos.virtualledger.server.jetty_test;

import de.fau.amos.virtualledger.dtos.BankAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SampleController {

	@Autowired
	private HelloWorldService helloWorldService;

	@GetMapping("/")
	@ResponseBody
	public List<BankAccess> helloWorld() {
		return this.helloWorldService.getHelloMessage();
	}

}