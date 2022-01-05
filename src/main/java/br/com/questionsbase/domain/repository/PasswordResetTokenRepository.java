package br.com.questionsbase.domain.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import br.com.questionsbase.domain.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    
    @Transactional
    @Modifying
    void deleteByUserId(int userId);

    Optional<PasswordResetToken> findByToken(String token);
}
