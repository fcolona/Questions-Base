package br.com.questionsbase.api.model;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import br.com.questionsbase.api.model.AlternativeResponse;

@Getter
@Setter
public class QuestionResponse {
    private int id;

    private String exam;
    private int year;
    private String subject;
    private String slug;
    private String stem;

    private Set<AlternativeResponse> alternatives;
}
