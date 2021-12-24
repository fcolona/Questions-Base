package br.com.questionsbase.domain.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.questionsbase.domain.model.Role;
import br.com.questionsbase.domain.model.User;
import br.com.questionsbase.domain.model.User.Provider;
import br.com.questionsbase.domain.repository.RoleRepository;
import br.com.questionsbase.domain.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Transactional
    public void processOAuthPostLogin(String email) {

        //If the email doesn't exist, it'll be saved
        userRepository.findByEmail(email).orElseGet( () -> {
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
