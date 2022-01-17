package br.com.questionsbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class QuestionsBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionsBaseApplication.class, args);
	}

}
