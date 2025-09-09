package com.unam.dwb.auth.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
	
	@NotBlank
	@Schema(description = "Nombre del usuario a registrar", example = "Carlos")
	private String nombres;
	
	@NotBlank
	@Schema(description = "Apellidos del usuario a registrar", example = "López")
	private String apellidos;
	
	@NotBlank
	@Schema(description = "Apellidos del usuario a registrar", example = "carloslzrz")
	private String nombreUsuario;
	
	@NotBlank
	@Email
	@Schema(description = "Correo del usuario", example = "carlos.lopez@ciencias.unam.mx")
	private String correo;
	
	@Size(min = 8)
	@NotBlank
	private String contrasena;

}
