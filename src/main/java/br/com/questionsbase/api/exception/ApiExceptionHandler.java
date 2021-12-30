package br.com.questionsbase.api.exception;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
    
    private MessageSource messageSource;

    //Handles bean validation error
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        
        Set<ErrorDetails.Field> fields = new HashSet<>();

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(status.value());
        errorDetails.setDateTime(OffsetDateTime.now());
        errorDetails.setTitle("There are one or more invalid fields. Please fill it in the correct way and try again.");
        errorDetails.setFields(fields);
                
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            fields.add(new ErrorDetails.Field(nome, mensagem));
        });

        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<?> resourceAlreadyExistsHandler(ResourceAlreadyExistsException exception, WebRequest request){
        Set<ErrorDetails.Field> fields = exception.getFields();
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(400);
        errorDetails.setDateTime(OffsetDateTime.now());
        errorDetails.setTitle("There are one or more invalid fields. Please fill it in the correct way and try again.");
        errorDetails.setFields(fields);

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

}
