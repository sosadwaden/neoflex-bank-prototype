package com.sosadwaden.dossier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DossierApplication {

	public static void main(String[] args) {
		SpringApplication.run(DossierApplication.class, args);
	}

}
