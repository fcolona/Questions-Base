package br.com.questionsbase.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class AlternativeInput {
    private String content;

    @JsonProperty
    private boolean isCorrect;
}
