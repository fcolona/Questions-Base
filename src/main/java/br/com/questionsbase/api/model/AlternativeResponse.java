package br.com.questionsbase.api.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class AlternativeResponse implements Serializable{
    private int id;
    private String content;
}
