package uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.quiz.Quiz;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Integer> {

	List<Quiz> findByUserUserID(long userID);
	
	List<Quiz> findByUserUserIDIsNull();

}
