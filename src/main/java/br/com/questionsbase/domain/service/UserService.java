package br.com.questionsbase.domain.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.questionsbase.api.assembler.UserAssembler;
import br.com.questionsbase.api.exception.ErrorDetails.Field;
import br.com.questionsbase.api.exception.ResourceAlreadyExistsException;
import br.com.questionsbase.api.exception.ResourceNotFoundException;
import br.com.questionsbase.api.model.UserInput;
import br.com.questionsbase.api.model.UserResponse;
import br.com.questionsbase.api.model.dto.UserIdAndEmail;
import br.com.questionsbase.domain.PasswordResetToken;
import br.com.questionsbase.domain.model.Role;
import br.com.questionsbase.domain.model.User;
import br.com.questionsbase.domain.model.User.Provider;
import br.com.questionsbase.domain.repository.PasswordResetTokenRepository;
import br.com.questionsbase.domain.repository.RoleRepository;
import br.com.questionsbase.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserAssembler userAssembler;
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Transactional
    public UserResponse save(UserInput userInput) throws ResourceAlreadyExistsException{;
        User user = userAssembler.toEntity(userInput);

        Optional<UserIdAndEmail> userOptional = userRepository.checkIfUserExists(user.getEmail(), Provider.LOCAL);

        userOptional.ifPresent( userFound -> {
            Set<Field> fields = new HashSet<>();
            fields.add(new Field("email", "User already exists"));
            throw new ResourceAlreadyExistsException(fields);
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
        userRepository.checkIfUserExistsAndRetrieveUser(email, Provider.GOOGLE).orElseGet( () -> {
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

    public PasswordResetToken createPasswordResetTokenForUser(User user) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();

        String token = UUID.randomUUID().toString();
        LocalDate expireDate = LocalDate.now().plusDays(PasswordResetToken.EXPIRATION);
        
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpireDate(expireDate);

        passwordResetTokenRepository.deleteByUserId(user.getId());

        PasswordResetToken tokenSaved = passwordResetTokenRepository.save(passwordResetToken);

        return tokenSaved;
    }

    @Transactional
    public boolean isTokenValid(String token){
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElseThrow( () -> {
            Set<Field> fields = new HashSet<>();
            fields.add(new Field("token", "Token given do not match"));
            throw new ResourceNotFoundException(fields);
        });

        LocalDate currentDate = LocalDate.now();

        if(passwordResetToken.getExpireDate().isBefore(currentDate)){
            return false; 
        }else{
            return true;
        }
    }

    @Transactional
    public UserResponse updatePassword(String newPassword, String token){
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).get();
        User user = passwordResetToken.getUser();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);

        User userSaved = userRepository.save(user);

        return userAssembler.toResponse(userSaved);
    }
}
