package br.com.questionsbase.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class AlternativeInput {
    @NotBlank
    private String content;

    @JsonProperty
    @NotNull
    private boolean isCorrect;
}
