package com.bolsa.banca_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BancaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancaBackendApplication.class, args);
	}

}
