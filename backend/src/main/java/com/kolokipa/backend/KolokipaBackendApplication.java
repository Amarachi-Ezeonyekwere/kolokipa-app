package com.kolokipa.backend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KolokipaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(KolokipaBackendApplication.class, args);
	}

}
