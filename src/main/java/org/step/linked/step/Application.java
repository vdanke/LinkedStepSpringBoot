package org.step.linked.step;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Application {

	/*
	WebContext -> @RestController || @Controller -> берет методы -> декларирует url

	WebContext - url -> подбирается обработчки url с подходящим методом запроса ->
			     обрабатывается запрос и выдается response
	 */

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
