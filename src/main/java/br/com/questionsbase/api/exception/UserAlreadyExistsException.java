package br.com.questionsbase.api.exception;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.questionsbase.api.exception.ErrorDetails.Field;
import lombok.Getter;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class UserAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private Set<Field> fields;
    
    public UserAlreadyExistsException(Set<Field> fields) {
		super(fields.toString());
		this.fields = fields;
	}
}
