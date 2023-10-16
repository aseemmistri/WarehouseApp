package in.nareshit.raghu.setup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.nareshit.raghu.model.User;
import in.nareshit.raghu.service.IUserService;

@Component
public class SetupAdmin implements CommandLineRunner {
	
	@Autowired
	private IUserService service;

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setName("NareshIT");
		user.setEmail("nit@gmail.com");
		user.setPwd("nit");
		user.setActive(true);
		user.setRoles(List.of("ADMIN","APPUSER"));
		if(service.findByEmail(user.getEmail()).isEmpty()) {
			service.saveUser(user);
		}
		
		
		

	}

}
