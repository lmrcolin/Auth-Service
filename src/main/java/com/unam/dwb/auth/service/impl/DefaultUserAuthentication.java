package com.unam.dwb.auth.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unam.dwb.auth.domain.Usuario;
import com.unam.dwb.auth.repo.UsuarioJpaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DefaultUserAuthentication implements UserDetailsService {

	@Autowired
	private UsuarioJpaRepository usuarioJpaRepository;

	@Override
	public Usuario loadUserByUsername(String principal) throws UsernameNotFoundException {
		
		Optional<Usuario> usuarioOptional;
		Usuario usuario;

		usuarioOptional = usuarioJpaRepository.findByUsername(principal);

		if(usuarioOptional.isPresent()) {
			usuario = usuarioOptional.get();
			log.info("Usuario existente: {}", usuario);
			return usuario;
		}

		usuario = usuarioJpaRepository.findByCorreo(principal).orElseThrow(() -> new UsernameNotFoundException("Usuario inexistente"));
		log.info("Usuario existente: {}", usuario);
		return usuario;
	}

}
