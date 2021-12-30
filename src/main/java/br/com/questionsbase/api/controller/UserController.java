package br.com.questionsbase.api.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.questionsbase.api.assembler.UserAssembler;
import br.com.questionsbase.api.exception.ErrorDetails.Field;
import br.com.questionsbase.api.exception.ResourceAlreadyExistsException;
import br.com.questionsbase.api.exception.ResourceNotFoundException;
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
    private UserAssembler userAssembler;

    @GetMapping
    public List<UserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();

        return userAssembler.toCollectionResponse(users);
    }

    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable int userId){
        User user = userRepository.findById(userId).orElseThrow( () -> {
            Set<Field> fields = new HashSet<>();
            fields.add(new Field("userId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        });

        return userAssembler.toResponse(user);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid UserInput userInput) throws ResourceAlreadyExistsException{
        return userService.save(userInput);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId){
        userRepository.deleteFromLinkTable(userId);
        userRepository.deleteById(userId);

        return ResponseEntity.noContent().build();
    }
}
