package br.com.questionsbase.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.questionsbase.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
}
