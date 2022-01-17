package br.com.questionsbase.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class QuestionResponse implements Serializable{
    private int id;

    private String exam;
    private int year;
    private String subject;
    private String slug;
    private String stem;

    private Set<AlternativeResponse> alternatives;
    private List<ImageResponse> images;
}
