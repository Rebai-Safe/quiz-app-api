package com.sr.quiz.service;

import com.sr.quiz.dto.QuestionDto;
import com.sr.quiz.dto.UserResponse;
import com.sr.quiz.entity.Question;
import com.sr.quiz.entity.Quiz;
import com.sr.quiz.repository.QuestionRepository;
import com.sr.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<?> createQuiz(String category, String title, int numQ) {

        // get questions
        List<Question> questions = questionRepository.findRandomQuestionByCategory(category, numQ);

        // create quiz
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        try {
            return new ResponseEntity<>(quizRepository.save(quiz).getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("-1", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionDto>> getQuizQuestions(int id) {

        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Question> questions = quiz.get().getQuestions();

        List<QuestionDto> questionsDTOs = questions.stream().map(question -> {
            var questionDto = new QuestionDto();
            questionDto.setId(question.getId());
            questionDto.setQuestionTitle(question.getQuestionTitle());
            questionDto.setOption1(question.getOption1());
            questionDto.setOption2(question.getOption2());
            questionDto.setOption3(question.getOption3());
            questionDto.setOption4(question.getOption4());

            return questionDto;
        }).toList();

        return new ResponseEntity<>(questionsDTOs, HttpStatus.OK);

    }

    public ResponseEntity<?> calculateResult(int id, List<UserResponse> responses) {

        Quiz quiz = quizRepository.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        AtomicInteger score = new AtomicInteger();
        AtomicInteger index = new AtomicInteger();

        responses.forEach(userResponse -> {
            if (userResponse.getResponse().equals(questions.get(index.get()).getRightAnswer()))
                score.getAndIncrement();
            index.getAndIncrement();
        });

        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
