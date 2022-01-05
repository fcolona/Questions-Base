package br.com.questionsbase.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordAndToken {
    private String newPassword;
    private String token;
}
