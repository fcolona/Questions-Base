package br.com.questionsbase.domain.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.questionsbase.api.assembler.UserAssembler;
import br.com.questionsbase.api.exception.UserAlreadyExistsException;
import br.com.questionsbase.api.exception.ErrorDetails.Field;
import br.com.questionsbase.api.model.UserInput;
import br.com.questionsbase.api.model.UserResponse;
import br.com.questionsbase.domain.model.Role;
import br.com.questionsbase.domain.model.User;
import br.com.questionsbase.domain.model.User.Provider;
import br.com.questionsbase.domain.repository.RoleRepository;
import br.com.questionsbase.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserAssembler userAssembler;

    @Transactional
    public UserResponse save(UserInput userInput) throws UserAlreadyExistsException{;
        User user = userAssembler.toEntity(userInput);

        Optional<User> userOptional = userRepository.checkIfUserExists(user.getEmail(), Provider.LOCAL);
        userOptional.ifPresent( userFound -> {
            Set<Field> fields = new HashSet<>();
            fields.add(new Field("email", "User already exists"));
            throw new UserAlreadyExistsException(fields);
        });

        user.setProvider(Provider.LOCAL);

        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName("ROLE_USER").get();

        roles.add(userRole);
        user.setRoles(roles); 

        User userSaved = userRepository.save(user);

        return userAssembler.toResponse(userSaved);
    }
    
    @Transactional
    public void processOAuthPostLogin(String email) {

        //If the email doesn't exist, it'll be saved
        userRepository.checkIfUserExists(email, Provider.GOOGLE).orElseGet( () -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(null);
            newUser.setProvider(Provider.GOOGLE); 

            Set<Role> roles = new HashSet<>();

            Role userRole = roleRepository.findByName("ROLE_USER").get();

            roles.add(userRole);
            newUser.setRoles(roles);    
             
            return userRepository.save(newUser);   
        });
    }
}
