package uk.ac.bangor.cs.ice2101.group5.academigymraeg.web;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.quiz.Question;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.quiz.Quiz;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.NounRequestRepository;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.QuestionRepository;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.QuizRepository;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.security.User;

@Controller
@RequestMapping(value = "/quiz")
public class QuizController {

	@Autowired // instance injected into MathsRequest Class
	private NounRequestRepository nounRepo;
	
	@Autowired
	private QuestionRepository questionRepo;
	
	@Autowired
	private QuizRepository quizRepo;
	
	@RequestMapping(value="/question/generate", method = RequestMethod.GET)
	public @ResponseBody Question generateQuestion() {
		
		long repoSize = nounRepo.count() - 10;
		int repoIndex = new Random().nextInt((int)repoSize) + 1;
		Optional<NounRequest> noun = nounRepo.findById(repoIndex);
		
		Question question = new Question(noun.get());
		questionRepo.save(question);
		return question;
	}
	
	@RequestMapping(value="/generate", method = RequestMethod.GET)
	public @ResponseBody Quiz generateQuiz() {
		int amountOfQuestions = 20;
		
		List<Question> questionList = new LinkedList<Question>();
		
		for (int i = 0; i < amountOfQuestions; i++) {
			questionList.add(generateQuestion());
		}
		
		Quiz quiz = new Quiz(questionList);
		quizRepo.save(quiz);
		
		return quiz;
	}
	
	  @RequestMapping(value = "/user", method = RequestMethod.GET)
	  @ResponseBody
	  public User currentUser() {
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     User user = (User)authentication.getPrincipal();
	     return user;
	  }
	
	@RequestMapping(value="/take", method = RequestMethod.GET)
	public String viewQuiz(Model uiModel) {
		Quiz quiz = generateQuiz();
		
		uiModel.addAttribute("quiz", quiz);
		uiModel.addAttribute("quizID", quiz.getId());
		uiModel.addAttribute("questionslist", quiz.getQuestionList());
		
		List<String> inputAnswers = new LinkedList<>();
		uiModel.addAttribute("inputAnswers", inputAnswers);
		
		return "quiz/take";
	}
	
	/**
	 * Saves quiz to database using 
	 * @param uiModel
	 * @param formQuiz
	 * @return
	 */
	@RequestMapping(value="/take", method = RequestMethod.POST)
	public String resultsFromQuiz(Model uiModel, @ModelAttribute Quiz formQuiz) {
		
		Quiz quiz = quizRepo.findById(formQuiz.getId()).get();
		quiz.setUser(currentUser());
		List<Question> quizQuestions = quiz.getQuestionList();
		
		/**
		 * assigns the answers to the original quiz using the quiz submitted through the form.
		 * Removes the need to include <input type="hidden"> with answers and other details in html
		 */
		for (int i = 0; i < quizQuestions.size(); i++) {
			Question question = quizQuestions.get(i);
			question.setInputAnswer(formQuiz.getQuestionList().get(i).getInputAnswer());
			
			if(question.getInputAnswer().equalsIgnoreCase(question.getAnswer())) {
				question.setCorrect(true);
				quiz.incrementScore(1);
			}

		}
		
		
		quizRepo.save(quiz);
		
		/**
		 * deletes any blank unsubmitted quizzes
		 */
		quizRepo.deleteAll(quizRepo.findByUserUserIDIsNull());
		
		return "redirect:results/" + quiz.getId();
	}
	

	
	
	
	@RequestMapping(value="/results/{quizid}", method = RequestMethod.GET)
	@PreAuthorize("@AuthenticationService.hasAccess(authentication, #quizid)")
	public String viewQuizResults(Model uiModel, @PathVariable("quizid") int quizid, Principal principal) {
		
		Quiz quiz = quizRepo.findById(quizid).get();
		uiModel.addAttribute(quiz);

		return "quiz/results";
	}
	
	
	@RequestMapping(value = "/results/list", method = RequestMethod.GET)
	public String NounsListFindWelsh(Model uiModel) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		
		
		uiModel.addAttribute("quizResultList", quizRepo.findByUserUserID(user.getUserID()));

		return "quiz/resultList";

	}
	

}
