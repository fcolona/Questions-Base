package br.com.questionsbase.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.questionsbase.api.model.UserInput;
import br.com.questionsbase.api.model.UserResponse;
import br.com.questionsbase.domain.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UserAssembler {
    private ModelMapper modelMapper;

    public User toEntity(UserInput userInput){
        return modelMapper.map(userInput, User.class);
    }

    public UserResponse toResponse(User user){      
        UserResponse res = modelMapper.map(user, UserResponse.class);

        return res;
    }
}
