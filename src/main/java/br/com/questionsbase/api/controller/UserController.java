package br.com.questionsbase.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.questionsbase.domain.model.User;
import br.com.questionsbase.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;

    @GetMapping()
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
