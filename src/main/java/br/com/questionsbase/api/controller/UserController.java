package br.com.questionsbase.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


import br.com.questionsbase.api.exception.ResourceAlreadyExistsException;
import br.com.questionsbase.api.model.UserInput;
import br.com.questionsbase.api.model.UserResponse;
import br.com.questionsbase.domain.model.User;
import br.com.questionsbase.domain.repository.UserRepository;
import br.com.questionsbase.domain.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;
    private UserService userService;

    @GetMapping()
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid UserInput userInput) throws ResourceAlreadyExistsException{
        return userService.save(userInput);
    }
}
