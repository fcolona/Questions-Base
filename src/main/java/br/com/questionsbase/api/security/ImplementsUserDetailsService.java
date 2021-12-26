package br.com.questionsbase.api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.questionsbase.domain.model.User.Provider;
import br.com.questionsbase.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class ImplementsUserDetailsService implements UserDetailsService{
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.checkIfUserExists(email, Provider.LOCAL).orElseThrow( () -> new UsernameNotFoundException("User Not Found"));
    }
    
}
