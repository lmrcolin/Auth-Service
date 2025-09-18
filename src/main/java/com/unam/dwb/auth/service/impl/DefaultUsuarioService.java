package com.unam.dwb.auth.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.unam.dwb.auth.constants.Rol;
import com.unam.dwb.auth.domain.Usuario;
import com.unam.dwb.auth.model.request.UsuarioRequest;
import com.unam.dwb.auth.model.response.AuthAPIResponse;
import com.unam.dwb.auth.model.response.InfoPaginacion;
import com.unam.dwb.auth.model.response.UsuarioResponse;
import com.unam.dwb.auth.repo.UsuarioJdbcRepository;
import com.unam.dwb.auth.repo.UsuarioJpaRepository;
import com.unam.dwb.auth.service.UsuarioService;
import com.unam.dwb.auth.util.Globales;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DefaultUsuarioService implements UsuarioService {

	@Autowired
	private UsuarioJpaRepository usuarioJpaRepository;

	@Autowired
	private UsuarioJdbcRepository usuarioJdbcRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public AuthAPIResponse registraUsuario(@Valid UsuarioRequest request) {
		log.info("Se intenta registrar nuevo usuario");

		Boolean exitoPrecondiciones = verificaPrecondicionesRegistro(request);

		if(!exitoPrecondiciones.booleanValue()) {
			AuthAPIResponse usuarioExistenteResponse = new AuthAPIResponse();
			usuarioExistenteResponse.setDetalles(Arrays.asList("Registro de usuario fallido. Usuario ya existente"));
			usuarioExistenteResponse.setFechaHora(Globales.formatDate(new Date()));
			usuarioExistenteResponse.setToken(null);
			return usuarioExistenteResponse;
		}	

		Usuario usuarioNuevo = usuarioRequestToUsuario(request);
		HashSet<String> roles = new HashSet<>();
		roles.add(Rol.ROLE_USER.getNombreRol());
		usuarioNuevo.setRoles(roles);
		Usuario usuario = usuarioJpaRepository.save(usuarioNuevo);
		
		AuthAPIResponse usuarioCreadoResponse = new AuthAPIResponse();
		usuarioCreadoResponse.setDetalles(Arrays.asList("Usuario creado exitosamente"));
		usuarioCreadoResponse.setFechaHora(Globales.formatDate(new Date()));
		usuarioCreadoResponse.agregaUsuario(usuarioToUsuarioResponse(usuario));
		log.info("Usuario registrado"); 
		return usuarioCreadoResponse;

	}

	@Override
	public AuthAPIResponse consultaUsuarios(Integer pagina, Integer tam) {
		log.info("Consultando usuarios registrados");
		
		AuthAPIResponse response = new AuthAPIResponse();
	
		Pageable pageable = PageRequest.of(pagina, tam);
		Page<Usuario> paginaUsuarios = usuarioJpaRepository.findAll(pageable);
		List<Usuario> usuarios = paginaUsuarios.getContent();
		
		response.setUsuarios(usuarios.stream().map(this::usuarioToUsuarioResponse).toList());
		response.setInfoPaginacion(new InfoPaginacion(pagina, paginaUsuarios.hasNext(), paginaUsuarios.hasPrevious(), paginaUsuarios.getTotalPages(), paginaUsuarios.getSize(), paginaUsuarios.getTotalElements()));
		
		log.info("Usuarios recuperados: {}", usuarios.size());
		log.info("Termina consulta de usuarios registrados");
		
		return response;
	}	
	
	private UsuarioResponse usuarioToUsuarioResponse(Usuario usuario) {
		UsuarioResponse usuarioResponse = new UsuarioResponse();
		usuarioResponse.setApellidos(usuario.getApellidos());
		usuarioResponse.setCorreo(usuario.getCorreo());
		usuarioResponse.setEsActivo(usuario.getEsActivo());
		usuarioResponse.setNombres(usuario.getNombres());
		usuarioResponse.setNombreUsuario(usuario.getUsername());
		usuarioResponse.setRoles(usuario.getRoles());
		return usuarioResponse;
	}
	
	private Usuario usuarioRequestToUsuario(UsuarioRequest usuarioRequest) {
		Usuario usuarioNuevo = new Usuario();
		usuarioNuevo.setApellidos(usuarioRequest.getApellidos());
		usuarioNuevo.setPassword(passwordEncoder.encode(usuarioRequest.getContrasena()));
		usuarioNuevo.setCorreo(usuarioRequest.getCorreo());
		usuarioNuevo.setEsActivo(true);
		usuarioNuevo.setNombres(usuarioRequest.getNombres());
		usuarioNuevo.setUsername(usuarioRequest.getNombreUsuario());
		usuarioNuevo.setRoles(null);
		return usuarioNuevo;
	}
	
	private Boolean verificaPrecondicionesRegistro(@Valid UsuarioRequest request) {
		Optional<Usuario> byCorreo = usuarioJpaRepository.findByCorreo(request.getCorreo());
		Optional<Usuario> byUsername = usuarioJdbcRepository.findByUsername(request.getNombreUsuario());

		if(byCorreo.isPresent() || byUsername.isPresent()) {
			log.error("Usuario previamente registrado. No se puede continuar.");
			return false;
		}
		return true;
	}

}
