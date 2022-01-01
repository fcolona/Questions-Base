package br.com.questionsbase.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResponse {
    private int id;
    private byte[] content;
}
