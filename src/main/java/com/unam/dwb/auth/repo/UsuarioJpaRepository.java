package com.unam.dwb.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unam.dwb.auth.domain.Usuario;
import java.util.Optional;


public interface UsuarioJpaRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByCorreo(String correo);
	Optional<Usuario> findByUsername(String username);
	
	
}
