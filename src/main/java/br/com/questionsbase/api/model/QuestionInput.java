package br.com.questionsbase.api.model;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionInput {

    @NotBlank
    @Size(max = 255)
    private String exam;

    @NotNull
    private int year;
    
    @NotBlank
    @Size(max = 255)
    private String subject;

    @NotBlank
    private String stem;

    @Valid
    private Set<AlternativeInput> alternatives;

    private List<ImageInput> images;

}
