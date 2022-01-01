package br.com.questionsbase.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ImageInput {
    private byte[] content;
}
