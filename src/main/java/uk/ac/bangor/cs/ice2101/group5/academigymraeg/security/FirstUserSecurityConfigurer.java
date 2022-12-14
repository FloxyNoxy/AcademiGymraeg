package uk.ac.bangor.cs.ice2101.group5.academigymraeg.security;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.UserRepository;

/**
 * First User configuration setup
 * 
 * @author johnn
 * @author owenw
 * @author ethan Quilter
 *
 */

@Component
public class FirstUserSecurityConfigurer {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder pwEncoder;
	
	@PostConstruct // Run after all injection
	public void checkAndCreateFirstUser() {
		
		Logger LOG = LoggerFactory.getLogger(FirstUserSecurityConfigurer.class);
		
		if(repo.count() == 0) {
			LOG.warn("No users found in DB, creating default user.");
			User u = new User();
			u.setPassword(pwEncoder.encode("AcademiGymraeg"));
			u.setUsername("admin");
			u.setRole("ADMIN");
			
			repo.save(u);
			LOG.warn("Default user created");
		}
		
	}
	
}
