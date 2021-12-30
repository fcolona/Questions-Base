package br.com.questionsbase.domain.service;

import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import br.com.questionsbase.api.assembler.QuestionAssembler;
import br.com.questionsbase.api.model.QuestionInput;
import br.com.questionsbase.api.model.QuestionResponse;
import br.com.questionsbase.api.model.dto.QuestionIdAndSlug;
import br.com.questionsbase.domain.model.Question;
import br.com.questionsbase.domain.repository.QuestionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionService {
    private QuestionRepository questionRepository;
    private QuestionAssembler questionAssembler;
    
    @Transactional
    public QuestionResponse save(QuestionInput questionInput) throws HttpStatusCodeException{
        Question question = questionAssembler.toEntity(questionInput);
        question.getAlternatives().forEach( alternative -> {
            alternative.setQuestion(question);
        });

        String slug = this.generateSlug(question.getStem(), 0);
        question.setSlug(slug);

        Question questionSaved = questionRepository.save(question);

        return questionAssembler.toResponse(questionSaved);
    }

    private String generateSlug(String stem, int salt) throws HttpStatusCodeException{
        if(salt + 10 > stem.length()){
            throw new HttpStatusCodeException(HttpStatus.INTERNAL_SERVER_ERROR){};
        }

        String words[] = stem.toLowerCase().split(" ");

        String[] words10 = Arrays.copyOfRange(words, 0 + salt, 10 + salt);

        String slug = String.join("-", words10);

        Optional<QuestionIdAndSlug> questionOptional = questionRepository.checkIfQuestionExistsBySlug(slug);
        if(questionOptional.isPresent()){
            return this.generateSlug(stem, salt + 1);
        }

        return slug;
    }
}
