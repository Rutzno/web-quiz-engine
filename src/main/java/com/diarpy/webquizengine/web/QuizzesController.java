package com.diarpy.webquizengine.web;

import com.diarpy.webquizengine.dao.CompletionRepository;
import com.diarpy.webquizengine.dao.QuizRepository;
import com.diarpy.webquizengine.dao.UserRepository;
import com.diarpy.webquizengine.dao.UserRoleRepository;
import com.diarpy.webquizengine.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 12/11/2020
 */

@RestController
@RequestMapping("/api")
public class QuizzesController {

    private static final String NOT_FOUND_MESSAGE = "No such quiz";
    private static final String FORBIDDEN_MESSAGE = "You're not allowed";
    private final QuizRepository quizRepository;
    private final CompletionRepository completionRepository;

    private static final String BAD_REQUEST_MESSAGE = "This email is already taken";
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;


    public QuizzesController(QuizRepository quizRepository, CompletionRepository completionRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.quizRepository = quizRepository;
        this.completionRepository = completionRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        quiz.setUser(user);
        quizRepository.save(quiz);
        return quiz;
    }

    @GetMapping(path = "/quizzes/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        if (quizRepository.findById(id).isPresent()) {
            return quizRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    NOT_FOUND_MESSAGE);
        }
    }

    @GetMapping(path = "/quizzes")
    public Page<Quiz> getQuizzes(@RequestParam(defaultValue = "0") int page) {
        int size = 10;
        Pageable paging = PageRequest.of(page, size);
        return quizRepository.findAll(paging);
    }

    @GetMapping(path = "/quizzes/completed")
    public Page<Quiz> getCompletedQuizzes(@RequestParam(defaultValue = "0") int page,
                                          Authentication authentication) {
        int size = 10;
        User user = userRepository.findByEmail(authentication.getName());
        Pageable paging = PageRequest.of(page, size);

        return quizRepository.findCompletedQuizzesById(user, paging);
    }

    @PostMapping(path = "/quizzes/{id}/solve")
    public Answer solveQuiz(@PathVariable long id,
                            @RequestBody Answer answer,
                            Authentication authentication) {
        Quiz quiz = getQuiz(id);
        User user = userRepository.findByEmail(authentication.getName());
        if (Arrays.equals(answer.getAnswer(), quiz.getAnswer())) {
            completionRepository.save(new Completion(user, id, new Date()));
            return new Answer(true, "Congratulations, you're right!");
        }
        return new Answer(false, "Wrong answer! Please, try again.");
    }


    @DeleteMapping(path = "/quizzes/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable long id, Authentication authentication) {
        Quiz quiz = getQuiz(id);
        User user = userRepository.findByEmail(authentication.getName());
        if (quiz.getUser() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    FORBIDDEN_MESSAGE);
        }
        quizRepository.delete(quiz);
    }


    @PutMapping(path = "/quizzes/{id}")
    public Quiz updateQuiz(@PathVariable long id, @Valid @RequestBody UpdateQuiz quizDetails, Authentication authentication) {
        Quiz storedQuiz = getQuiz(id);
        User user = userRepository.findByEmail(authentication.getName());
        if (storedQuiz.getUser() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    FORBIDDEN_MESSAGE);
        }

        storedQuiz.setText(quizDetails.getText());
        storedQuiz.setTitle(quizDetails.getTitle());
        storedQuiz.setOptions(quizDetails.getOptions());
        storedQuiz.setAnswer(quizDetails.getAnswer());

        return quizRepository.save(storedQuiz);
    }


    @PostMapping(path = "/register")
    public void register(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE);
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());

            user.setPassword(encodedPassword);
            user.setActive(true);
            userRepository.save(user);

            UserRole userRole = new UserRole();
            userRole.setEmail(user.getEmail());
            userRole.setRole("USER");
            userRoleRepository.save(userRole);
        }
    }
}
