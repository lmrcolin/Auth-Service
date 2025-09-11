package com.unam.dwb.auth.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.unam.dwb.auth.domain.Usuario;

@Repository
public class UsuarioJdbcRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Optional<Usuario> findByUsername(String nombreUsuario) {
		String sql = "SELECT username, correo FROM dwb.usuario WHERE username = ?";
		
		List<Usuario> resultQuery = jdbcTemplate.query(sql, 
				(rs, row) -> new Usuario(
						rs.getString("username"),
						rs.getString("correo")),
				nombreUsuario);
		return resultQuery.stream().findFirst();
	}

}
