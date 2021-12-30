package br.com.questionsbase.api.assembler;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.questionsbase.api.model.QuestionInput;
import br.com.questionsbase.api.model.QuestionResponse;
import br.com.questionsbase.domain.model.Question;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class QuestionAssembler {
    private ModelMapper modelMapper;
    
    public QuestionResponse toResponse(Question question){           
        QuestionResponse res = modelMapper.map(question, QuestionResponse.class);

        return res;
    }

    public Set<QuestionResponse> toCollectionResponse(Set<Question> questions){

        Set<QuestionResponse> res = questions.stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());

        return res;
    }

    public Question toEntity(QuestionInput questionInput) {
        return modelMapper.map(questionInput, Question.class);
    }
}
