package br.com.questionsbase.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.questionsbase.domain.model.Alternative;

@Repository
public interface AlternativeRepository extends JpaRepository<Alternative, Integer> {
    
    @Query(
        value = "SELECT alternative.is_correct FROM alternative WHERE alternative.question_id = ?1 AND alternative.alternative_id =?2",
        nativeQuery = true
    )
    Optional<Boolean> checkAnswer(String questioniId, int alternativeId);
}
