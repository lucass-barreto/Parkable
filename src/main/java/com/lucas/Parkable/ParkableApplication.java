package com.lucas.Parkable;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Parkable API", description = "API para gerenciamento de estacionamento", version = "1.0"))
public class ParkableApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkableApplication.class, args);
	}

}
