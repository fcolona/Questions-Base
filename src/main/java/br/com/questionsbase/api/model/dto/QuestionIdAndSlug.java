package br.com.questionsbase.api.model.dto;

import lombok.Value;

@Value
public class QuestionIdAndSlug {
    private int id;
    private String slug;
}
