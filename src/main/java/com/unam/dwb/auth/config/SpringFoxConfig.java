package com.unam.dwb.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringFoxConfig {
	
	@Bean
	OpenAPI servicioRegistroAPI() {
		return new OpenAPI()
				.info(new Info().title("Servicio Auth DWB")
						.description("Servicio Web responsable de la Autenticación/Autorización y Administración de los Usuarios")
						.version("v1.0.0")
						.contact(new Contact()
								.name("Desarrollo Web Backend")
								.email("carlos.lopez@ciencias.unam.mx"))
						.license(new License()
								.name("")
								.url("")))
				.externalDocs(new ExternalDocumentation()
						.url("")
						.description("Documentación de Ejemplo"));
	}

}
