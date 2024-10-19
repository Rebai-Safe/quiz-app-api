package com.sr.quiz.web;


import com.sr.quiz.dto.QuestionDto;
import com.sr.quiz.dto.UserResponse;
import com.sr.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<?> createString(@RequestParam String category,
                                          @RequestParam String title,
                                          @RequestParam int numQ
    ) {

        return quizService.createQuiz(category, title, numQ);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionDto>> getQuizQuestions(@PathVariable int id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<?> submitQuiz(@PathVariable int id,
                                        @RequestBody List<UserResponse> responses)
     {
         return quizService.calculateResult(id, responses);
    }

}
