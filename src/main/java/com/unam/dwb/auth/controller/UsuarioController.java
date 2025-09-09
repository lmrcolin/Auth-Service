package com.unam.dwb.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unam.dwb.auth.model.request.UsuarioRequest;
import com.unam.dwb.auth.model.response.AuthAPIResponse;
import com.unam.dwb.auth.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/usuario")
@Tag(name= "Administración de Usuarios", description = "Operaciones relevantes para el manejo de los usuarios de los microservicios del sistema")
public class UsuarioController {

	private static final String REQUEST_LOG = "Request: {}";
	private static final String RESPONSE_LOG = "Response: {}";
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	@Operation(summary = "Crea un usuario", description = "Endpoint que permite la creación o actualización de un usuario")
	public ResponseEntity<AuthAPIResponse> upsertUsuario(@Valid @RequestBody UsuarioRequest request)  {
		log.info("UsuarioController.upsertUsuario() - In");
		
		if(log.isDebugEnabled())
			log.debug(REQUEST_LOG, request);
		
		AuthAPIResponse response = usuarioService.registraUsuario(request);
		
		if(log.isDebugEnabled())
			log.debug(RESPONSE_LOG);
		log.info("UsuarioController.upsertUsuario() - Out");
		
		return new ResponseEntity<>(response, HttpStatus.OK);      
	}
	
	@GetMapping
	@Operation(summary = "Consulta un usuario", description = "Endpoint que permite la creación o actualización de un usuario")
	public ResponseEntity<AuthAPIResponse> consultaUsuarios(
			@RequestParam(defaultValue = "0") Integer pagina,
			@RequestParam(defaultValue = "10") Integer tam
			)  {
		log.info("UsuarioController.consultaUsuarios() - In");
		
		StringBuilder sb = new StringBuilder();
		sb.append("Parametros: ");
		sb.append("Página: ");
		sb.append(pagina);
		sb.append("Tamaño Página: ");
		sb.append(tam);
		
		if(log.isDebugEnabled())
			log.debug(REQUEST_LOG, sb.toString());
		
		AuthAPIResponse response = usuarioService.consultaUsuarios(pagina, tam);
		
		if(log.isDebugEnabled())
			log.debug(RESPONSE_LOG);
		log.info("UsuarioController.consultaUsuarios() - Out");
		
		return new ResponseEntity<>(response, HttpStatus.OK);      
	}

}
