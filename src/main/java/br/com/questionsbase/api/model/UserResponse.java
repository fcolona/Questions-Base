package br.com.questionsbase.api.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.questionsbase.domain.model.Role;
import br.com.questionsbase.domain.model.User.Provider;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class UserResponse {
    private int id;
    private String email;
    private Provider provider;
    private Set<Role> roles;
}
