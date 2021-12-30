package br.com.questionsbase.api.assembler;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.questionsbase.api.model.AlternativeInput;
import br.com.questionsbase.domain.model.Alternative;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AlternativeAssembler {
    private ModelMapper modelMapper;
    
    public Set<Alternative> toCollectionEntity(Set<AlternativeInput> alternativeInputs){

        Set<Alternative> res = alternativeInputs.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());

        return res;
    }

    public Alternative toEntity(AlternativeInput alternativeInput) {
        return modelMapper.map(alternativeInput, Alternative.class);
    }
}
