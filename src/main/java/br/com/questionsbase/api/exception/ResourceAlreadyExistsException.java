package br.com.questionsbase.api.exception;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.questionsbase.api.exception.ErrorDetails.Field;
import lombok.Getter;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class ResourceAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private Set<Field> fields;
    
    public ResourceAlreadyExistsException(Set<Field> fields) {
		super(fields.toString());
		this.fields = fields;
	}
}
