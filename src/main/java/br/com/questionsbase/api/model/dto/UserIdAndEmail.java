package br.com.questionsbase.api.model.dto;

import lombok.Value;

@Value
public class UserIdAndEmail {
    private int id;
    private String email;
}
