package br.com.questionsbase.api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.http.HttpStatus;

import br.com.questionsbase.api.assembler.QuestionAssembler;
import br.com.questionsbase.api.model.QuestionInput;
import br.com.questionsbase.api.model.QuestionResponse;
import br.com.questionsbase.domain.model.Question;
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

    @GetMapping
    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
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
        Question questions = questionRepository.findByExamAndYearAndSubjectAndSlug(exam, year, subject, slug).orElseThrow( () -> new Exception("Question Not Found"));

        return questionAssembler.toResponse(questions);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponse createQuestion(@RequestBody QuestionInput questionInput) throws HttpStatusCodeException{
        return questionService.save(questionInput);
    }
}
