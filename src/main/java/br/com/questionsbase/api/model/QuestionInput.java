package br.com.questionsbase.api.model;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionInput {
    private String exam;
    private int year;
    private String subject;
    private String stem;

    private Set<AlternativeInput> alternatives;
}
