package br.com.questionsbase.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResponse implements Serializable{
    private int id;
    private byte[] content;
}
