package uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.quiz.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

}
