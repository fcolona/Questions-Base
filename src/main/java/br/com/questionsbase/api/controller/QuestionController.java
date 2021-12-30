package br.com.questionsbase.api.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import br.com.questionsbase.api.assembler.QuestionAssembler;
import br.com.questionsbase.api.exception.ErrorDetails.Field;
import br.com.questionsbase.api.exception.ResourceNotFoundException;
import br.com.questionsbase.api.model.QuestionInput;
import br.com.questionsbase.api.model.QuestionResponse;
import br.com.questionsbase.domain.model.Question;
import br.com.questionsbase.domain.repository.AlternativeRepository;
import br.com.questionsbase.domain.repository.QuestionRepository;
import br.com.questionsbase.domain.service.QuestionService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/question")
@AllArgsConstructor
public class QuestionController {
    private QuestionRepository questionRepository;
    private QuestionAssembler questionAssembler;
    private QuestionService questionService;
    private AlternativeRepository alternativeRepository;

    @GetMapping
    public List<QuestionResponse> getAllQuestions(){
        List<Question> questions = questionRepository.findAll();
        return questionAssembler.toListResponse(questions);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int questionId){
        questionRepository.deleteAlternatives(questionId);
        questionRepository.deleteById(questionId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable int questionId, @RequestBody QuestionInput questionInput){
        return ResponseEntity.ok(questionService.update(questionId, questionInput));
    }

    @GetMapping("/{exam}")
    public Set<QuestionResponse> getQuestionsByExam(@PathVariable String exam){
        Set<Question> questions = questionRepository.findByExam(exam);

        return questionAssembler.toCollectionResponse(questions);
    }

    @GetMapping("/{exam}/{year}")
    public Set<QuestionResponse> getQuestionsByExamAndYear(@PathVariable String exam, @PathVariable int year){
        Set<Question> questions = questionRepository.findByExamAndYear(exam, year);

        return questionAssembler.toCollectionResponse(questions);
    }

    @GetMapping("/{exam}/{year}/{subject}")
    public Set<QuestionResponse> getQuestionsByExamAndYearAndSubject(@PathVariable String exam, @PathVariable int year, @PathVariable String subject){
        Set<Question> questions = questionRepository.findByExamAndYearAndSubject(exam, year, subject);

        return questionAssembler.toCollectionResponse(questions);
    }

    @GetMapping("/{exam}/{year}/{subject}/{slug}")
    public QuestionResponse getQuestionsBySlug(@PathVariable String exam, 
    @PathVariable int year, @PathVariable String subject, @PathVariable String slug) throws Exception{
        Question questions = questionRepository.findByExamAndYearAndSubjectAndSlug(exam, year, subject, slug).orElseThrow( () -> {
            Set<Field> fields = new HashSet<>();
            fields.add(new Field("slug", "Slug given do not match"));
            throw new ResourceNotFoundException(fields);
        });

        return questionAssembler.toResponse(questions);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponse createQuestion(@RequestBody @Valid QuestionInput questionInput) throws HttpStatusCodeException{
        return questionService.save(questionInput);
    }

    @PostMapping("/answer")
    public boolean checkAnswer(@RequestParam(name = "questionId") String questionId, @RequestParam(name = "alternativeId") int alternativeId) throws Exception{
        return (boolean) alternativeRepository.checkAnswer(questionId, alternativeId).orElseThrow( () -> {
            Set<Field> fields = new HashSet<>();
            fields.add(new Field("questionId/alternativeId", "Ids given do not match"));
            throw new ResourceNotFoundException(fields);
        });
    }
}
