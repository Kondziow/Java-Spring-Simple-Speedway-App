package com.wojtanowski.konrad.clubapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClubAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubAppApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(@Value("${player.app.url}") String baseUrl) {
		return new RestTemplateBuilder().rootUri(baseUrl).build();
	}

}

