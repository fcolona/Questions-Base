package br.com.questionsbase.api.exception;

import java.time.OffsetDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class ErrorDetails {
    private Integer status;
    private OffsetDateTime dateTime;
    private String title;
    private Set<Field> fields;

    @AllArgsConstructor
    @Getter
    public static class Field{
        private String name;
        private String message;
    }
}
