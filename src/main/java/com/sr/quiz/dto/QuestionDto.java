package com.sr.quiz.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.sr.quiz.entity.Question}
 */
@Getter
@Setter

public class QuestionDto implements Serializable {
    Integer id;
    String questionTitle;
    String option1;
    String option2;
    String option3;
    String option4;
}
