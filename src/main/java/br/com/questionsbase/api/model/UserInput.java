package br.com.questionsbase.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {
    
    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @NotBlank
    private String password;
}
