package uk.ac.bangor.cs.ice2101.group5.academigymraeg.web;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.quiz.Quiz;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.QuizRepository;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.security.User;

@Component(value="AuthenticationService")
public class AuthenticationService implements PermissionEvaluator {
	
	@Autowired
	private QuizRepository repo;

	public boolean hasAccess(Authentication auth, int quizid) {
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		Quiz quiz = repo.findById(quizid).get();
		
		if(user.getUsername().equals(quiz.getUser().getUsername())) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
	      if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)){
	            return false;
	        } else {
	        	return hasAccess(authentication, (int) targetDomainObject);
	        }
	      
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
        if ((authentication == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        } else {
        	return true;
        }
	}


}
