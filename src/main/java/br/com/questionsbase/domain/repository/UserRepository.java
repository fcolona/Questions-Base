package br.com.questionsbase.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.questionsbase.api.model.dto.UserIdAndEmail;
import br.com.questionsbase.domain.model.User;
import br.com.questionsbase.domain.model.User.Provider;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    @EntityGraph(
        type = EntityGraphType.FETCH,
        attributePaths = {"roles"}
    )
    List<User> findAll();

    public Optional<User> findByEmail(String email);

    @Query(
        value = "SELECT new br.com.questionsbase.api.model.dto.UserIdAndEmail(u.id, u.email) FROM User u WHERE u.email=:email AND u.provider=:provider"
    )
    public Optional<UserIdAndEmail> checkIfUserExists(String email, Provider provider);

    @Query(
        value = "SELECT u FROM User u WHERE u.email=:email AND u.provider=:provider"
    )
    public Optional<User> checkIfUserExistsAndRetrieveUser(String email, Provider provider);
}
