package br.com.questionsbase.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;

import br.com.questionsbase.api.model.dto.QuestionIdAndSlug;
import br.com.questionsbase.domain.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    
    @Override
    @EntityGraph(
        type = EntityGraphType.FETCH,
        attributePaths = {"alternatives"}
    )
    List<Question> findAll();

    @Query(value = "SELECT new br.com.questionsbase.api.model.dto.QuestionIdAndSlug(q.id, q.slug) FROM Question q WHERE q.slug=:slug")
    Optional<QuestionIdAndSlug> checkIfQuestionExistsBySlug(String slug);

    @EntityGraph(
        type = EntityGraphType.FETCH,
        attributePaths = {"alternatives"}
    )
    Set<Question> findByExam(String exam);

    @EntityGraph(
        type = EntityGraphType.FETCH,
        attributePaths = {"alternatives"}
    )
    Set<Question> findByExamAndYear(String exam, int year);

    @EntityGraph(
        type = EntityGraphType.FETCH,
        attributePaths = {"alternatives"}
    )
    Set<Question> findByExamAndYearAndSubject(String exam, int year, String subject);

    @EntityGraph(
        type = EntityGraphType.FETCH,
        attributePaths = {"alternatives"}
    )
    Optional<Question> findByExamAndYearAndSubjectAndSlug(String exam, int year, String subject, String slug);
}